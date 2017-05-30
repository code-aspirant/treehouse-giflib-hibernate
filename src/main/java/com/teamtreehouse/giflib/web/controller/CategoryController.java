package com.teamtreehouse.giflib.web.controller;

import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.service.CategoryService;
import com.teamtreehouse.giflib.web.Color;
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
        model.addAttribute("categories",categories);
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        // TODO: Get the category given by categoryId
        Category category = null;

        model.addAttribute("category", category);
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
        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        // TODO: Add model attributes needed for edit form

        return "category/form";
    }

    // Update an existing category
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory() {
        // TODO: Update category if valid data was received

        // TODO: Redirect browser to /categories
        return null;
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
            redirectAttributes.addFlashAttribute("category", category);
            return "redirect:/categories/add";
        }
        categoryService.save(category);

        // TODO: Redirect browser to /categories
        // This will invoke the method that matches the mapping
        // which in this case is listCategories
        return "redirect:/categories";
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId) {
        // TODO: Delete category if it contains no GIFs

        // TODO: Redirect browser to /categories
        return null;
    }
}
