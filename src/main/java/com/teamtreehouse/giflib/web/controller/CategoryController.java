package com.teamtreehouse.giflib.web.controller;

import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.service.CategoryService;
import com.teamtreehouse.giflib.web.Color;
import com.teamtreehouse.giflib.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Index of all categories
    @RequestMapping("/categories")
    public String listCategories(Model model) {
        // TODO: Get all categories
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        // TODO: Get the category given by categoryId
        Category category = categoryService.findById(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("gifs", category.getGifs());
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        // Check if there is an existing category attribute. See flash message
        if (!model.containsAttribute("category")) {
            // TODO: Add model attributes needed for new form
            model.addAttribute("category", new Category());
        }
        // Import the color enum and add all options to the model
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", "/categories");
        model.addAttribute("heading", "New Category");
        model.addAttribute("submit", "Add");
        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        // Check if there is an existing category attribute. See flash message
        if (!model.containsAttribute("category")) {
            // TODO: Add model attributes needed for new form
            model.addAttribute("category", categoryService.findById(categoryId));
        }
        // Import the color enum and add all options to the model
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", String.format("/categories/%s", categoryId));
        model.addAttribute("heading", "Edit Category");
        model.addAttribute("submit", "Update");
        return "category/form";
    }

    // Update an existing category
    // TODO: How does Spring capture the Category object from the form submission?
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory(@Valid Category category,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        // TODO: Update category if valid data was received
        if (result.hasErrors()) {
            // Add category if invalid data was received
            // This category object will survive 1 redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
            return String.format("redirect:/categories/%s/edit", category.getId());
        }
        categoryService.save(category);

        redirectAttributes.addFlashAttribute(
                "flash", new FlashMessage("Category successfully updated!", FlashMessage.Status.SUCCESS));

        // TODO: Redirect browser to /categories
        // This will invoke the method that matches the mapping
        // which in this case is listCategories
        return "redirect:/categories";
    }

    // Add a category
    // Can add a Category param since we expect that all form data will contain
    // what is needed to construct a new category object.
    // @Valid requires the category to pass the constraints we placed
    // on its values in Category.class
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        // TODO: Add category if valid data was received
        if (result.hasErrors()) {
            // Add category if invalid data was received
            // This category object will survive 1 redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
            return "redirect:/categories/add";
        }
        categoryService.save(category);

        redirectAttributes.addFlashAttribute(
                "flash", new FlashMessage("Category successfully added!", FlashMessage.Status.SUCCESS));

        // TODO: Redirect browser to /categories
        // This will invoke the method that matches the mapping
        // which in this case is listCategories
        return "redirect:/categories";
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId,
                                 RedirectAttributes redirectAttributes) {
        // TODO: Delete category if it contains no GIFs
        Category category = categoryService.findById(categoryId);
        if (category.getGifs().size() > 0) {
            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage("Only empty categories can be deleted!", FlashMessage.Status.FAILURE));
            return String.format("redirect:/categories/%s/edit", categoryId);
        }
        categoryService.delete(category);
        redirectAttributes.addFlashAttribute("Category deleted!", FlashMessage.Status.SUCCESS);

        // TODO: Redirect browser to /categories
        return "redirect:/categories";
    }
}
