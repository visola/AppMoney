package com.appmoney.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  public List<Category> getCategories(@AuthenticationPrincipal User user) {
    return categoryDao.getCategories(user.getId());
  }

}
