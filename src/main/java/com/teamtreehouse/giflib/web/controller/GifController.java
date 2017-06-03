package com.teamtreehouse.giflib.web.controller;

import com.teamtreehouse.giflib.model.Gif;
import com.teamtreehouse.giflib.service.CategoryService;
import com.teamtreehouse.giflib.service.GifService;
import com.teamtreehouse.giflib.web.FlashMessage;
import com.teamtreehouse.giflib.web.GifValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.teamtreehouse.giflib.web.FlashMessage.*;

@Controller
public class GifController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GifService gifService;

    @Autowired
    private GifValidator gifValidator;

    // Home page - index of all GIFs
    @RequestMapping("/")
    public String listGifs(Model model) {
        // Get all gifs
        List<Gif> gifs = gifService.findAll();

        model.addAttribute("gifs", gifs);
        return "gif/index";
    }

    // Single GIF page
    @RequestMapping("/gifs/{gifId}")
    public String gifDetails(@PathVariable Long gifId, Model model) {
        // Get gif whose id is gifId
        Gif gif = gifService.findById(gifId);

        model.addAttribute("gif", gif);
        return "gif/details";
    }

    // GIF image data
    // @ResponseBody tells Thymeleaf that the return value here is
    // the exact date to send in the response and not to render a template
    @RequestMapping("/gifs/{gifId}.gif")
    @ResponseBody
    public byte[] gifImage(@PathVariable Long gifId) {
        // Return image data as byte array of the GIF whose id is gifId
        return gifService.findById(gifId).getBytes();
    }

    // Favorites - index of all GIFs marked favorite
    @RequestMapping("/favorites")
    public String favorites(Model model) {
        // Get list of all GIFs marked as favorite
        List<Gif> faves = gifService.findAll();

        model.addAttribute("gifs",
                faves.stream()
                        .filter(Gif::isFavorite)
                        .collect(Collectors.toList()));
        model.addAttribute("username","The Dude"); // Static username
        return "gif/favorites";
    }

    // Upload a new GIF
    // @RequestParam can be set with a custom name
    // if that name does not match the name of the
    // parameter in the method
    @RequestMapping(value = "/gifs", method = RequestMethod.POST)
    public String addGif(Gif gif,
                         @RequestParam MultipartFile file,
                         RedirectAttributes redirectAttributes,
                         BindingResult result) {
        // Upload new GIF if data is valid
        gifValidator.validate(gif, result);
        gifService.save(gif, file);

        // Add a flash message for success
        redirectAttributes.addFlashAttribute(
                "flash",
                new FlashMessage("GIF Successfully Uploaded", Status.SUCCESS));
        // Redirect browser to new GIF's detail view
        // The Hibernate save session takes care of assigning
        // an id to the new Gif object that has been persisted.
        return String.format("redirect:/gifs/%s", gif.getId());
    }

    // Form for uploading a new GIF
    @RequestMapping("/upload")
    public String formNewGif(Model model) {
        // Add model attributes needed for new GIF upload form
        if (!model.containsAttribute("gif")) {
            model.addAttribute("gif", new Gif());
        }
        model.addAttribute("action", "/gifs");
        model.addAttribute("submitText", "Upload");
        model.addAttribute("categories", categoryService.findAll());
        return "gif/form";
    }

    // Form for editing an existing GIF
    @RequestMapping(value = "/gifs/{gifId}/edit")
    public String formEditGif(@PathVariable Long gifId, Model model) {
        // Add model attributes needed for edit form
        if (!model.containsAttribute("gif")) {
            model.addAttribute("gif", gifService.findById(gifId));
        }

        model.addAttribute("submitText", "Update");
        model.addAttribute("action", String.format("/gifs/%s", gifId));
        model.addAttribute("categories", categoryService.findAll());

        return "gif/form";
    }

    // Update an existing GIF
    @RequestMapping(value = "/gifs/{gifId}", method = RequestMethod.POST)
    public String updateGif(Gif gif,
                            @RequestParam MultipartFile file,
                            RedirectAttributes redirectAttributes) {
        // TODO: Update GIF if data is valid
        gifService.save(gif, file);
        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Gif successfully updated!", Status.SUCCESS));

        // Redirect browser to updated GIF's detail view
        return String.format("redirect:/gifs/%s", gif.getId());
    }

    // Delete an existing GIF
    @RequestMapping(value = "/gifs/{gifId}/delete", method = RequestMethod.POST)
    public String deleteGif(@PathVariable Long gifId, RedirectAttributes redirectAttributes) {
        // Delete the GIF whose id is gifId
        Gif gif = gifService.findById(gifId);
        gifService.delete(gif);
        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Gif successfully deleted!", Status.SUCCESS));
        // Redirect to app root
        return "redirect:/";
    }

    // Mark/unmark an existing GIF as a favorite
    @RequestMapping(value = "/gifs/{gifId}/favorite", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long gifId, HttpServletRequest request) {
        // With GIF whose id is gifId, toggle the favorite field
        Gif gif = gifService.findById(gifId);
        gifService.toggleFavorite(gif);
        // Redirect to GIF's detail view
        // return String.format("redirect:/gifs/%s", gifId);
        // TODO: How to make this secure?
        return String.format("redirect:%s", request.getHeader("referer"));
    }

    // Search results
    @RequestMapping("/search")
    public String searchResults(@RequestParam String q, Model model) {
        // Get list of GIFs whose description contains value specified by q
        List<Gif> gifs = gifService.findAll();
        model.addAttribute("gifs", gifs.stream()
                .filter(gif -> gif.getDescription().toLowerCase().contains(q.toLowerCase()))
                .collect(Collectors.toList()));
        return "gif/index";
    }
}