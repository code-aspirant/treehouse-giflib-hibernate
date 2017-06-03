package com.teamtreehouse.giflib.web;

import com.teamtreehouse.giflib.model.Category;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category category = (Category) target;
        if (category.getName().isEmpty()) {
            errors.rejectValue("category.name", "Please enter a title for the Category");
        }

    }
}
