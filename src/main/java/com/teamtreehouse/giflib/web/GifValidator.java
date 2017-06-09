package com.teamtreehouse.giflib.web;

import com.teamtreehouse.giflib.model.Gif;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GifValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Gif.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Gif gif = (Gif) target;
        System.out.println("Validate Gif id = " + gif.getId());
        //System.out.println("Validate Gif id = " + gif.getId());
        if (gif.getId() == null && gif.getFile() == null) {
            errors.rejectValue("file", "file.required","You must upload a gif!");
        }

        ValidationUtils.rejectIfEmpty(errors, "category", "category.empty", "Please choose a category.");
    }
}
