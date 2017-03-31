package com.appmoney.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.model.Category;
import com.appmoney.model.Friendship;
import com.appmoney.model.User;
import com.appmoney.repository.CategoryRepository;
import com.appmoney.repository.FriendshipRepository;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  FriendshipRepository friendshipRepository;

  @RequestMapping(method=RequestMethod.GET)
  public List<Category> getCategories(@AuthenticationPrincipal User user) {
    List<Friendship> friendships = friendshipRepository.findByFriendId(user.getId());

    Set<Integer> userIds = new HashSet<>();
    userIds.add(user.getId());

    friendships.forEach(f -> userIds.add(f.getOwner().getId()));

    return categoryRepository.findByOwnerIdIn(userIds);
  }

  @RequestMapping(method=RequestMethod.POST)
  public Category createCategory(@RequestBody Category category, @AuthenticationPrincipal User user) {
    category.setOwner(user);
    return categoryRepository.save(category);
  }

  @RequestMapping(method=RequestMethod.PUT, value="/{categoryId}")
  public Category updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category, @AuthenticationPrincipal User user) {
    Category loaded = categoryRepository.findOne(categoryId);

    if (!canEdit(loaded, user)) {
      return null;
    }

    loaded.setName(category.getName());

    if (category.getParent() == null) {
      loaded.setParent(null);
    } else {
      Category loadedParent = categoryRepository.findOne(category.getParent().getId());
      loaded.setParent(loadedParent);
    }

    return categoryRepository.save(loaded);
  }

  @RequestMapping(method=RequestMethod.PUT)
  @Transactional
  public List<Category> updateCategories(@RequestBody List<Category> categories, @AuthenticationPrincipal User user) {
    categories.forEach(c -> this.updateCategory(c.getId(), c, user));
    return categories;
  }

  private Set<Integer> friendsAndI(User user) {
    List<Friendship> friendships = friendshipRepository.findByFriendId(user.getId());

    Set<Integer> userIds = new HashSet<>();
    userIds.add(user.getId());

    friendships.forEach(f -> userIds.add(f.getOwner().getId()));
    return userIds;
  }

  private boolean canEdit(Category category, User user) {
    return friendsAndI(user).contains(category.getOwner().getId());
  }

}
