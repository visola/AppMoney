package com.appmoney.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.CategoryDao;
import com.appmoney.model.Category;
import com.appmoney.model.User;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  @Autowired
  CategoryDao categoryDao;

  @RequestMapping(method=RequestMethod.GET)
  public List<Category> getCategories(@RequestParam(defaultValue="false") Boolean hidden, @AuthenticationPrincipal User user) {
    return categoryDao.getCategories(user.getId(), hidden);
  }

  @RequestMapping(method=RequestMethod.POST)
  public Category createCategory(@RequestBody Category category, @AuthenticationPrincipal User user) {
    category.setCreatedBy(user.getId());
    return categoryDao.create(category);
  }

  @RequestMapping(method=RequestMethod.PUT, value="/{categoryId}")
  public Category updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category, @AuthenticationPrincipal User user) {
    categoryDao.update(category, user.getId());
    return category;
  }

  @RequestMapping(method=RequestMethod.PUT)
  @Transactional
  public List<Category> updateCategories(@RequestBody List<Category> categories, @AuthenticationPrincipal User user) {
    categories.forEach(c -> categoryDao.update(c, user.getId()));
    return categories;
  }

}
