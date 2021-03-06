package com.teamtreehouse.giflib.service;

import com.teamtreehouse.giflib.dao.CategoryDao;
import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Spring will pick this up as a Bean
@Transactional
public class CategoryServiceImpl implements CategoryService {

    // Autowire something by it's interface name. Spring will find the only implementation
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryDao.save(category);
    }

    @Override
    public void delete(Category category) {
        // TODO: Check for non-empty categories here. Then throw a non-empty category exception. See Teacher's notes
        // TODO: If above complete, remove that logic from the controller
        categoryDao.delete(category);
    }
}
