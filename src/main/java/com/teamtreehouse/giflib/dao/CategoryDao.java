package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.model.Gif;

import java.util.List;

/* This is essentially the contract that must be followed by anything
* that implements it */
public interface CategoryDao {
    List<Category> findAll();
    Category findById(Long id);
    void save(Category category);
    void delete(Category category);
}
