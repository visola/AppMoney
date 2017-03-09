package com.appmoney.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.appmoney.model.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>{

  List<Category> findByOwnerIdIn(Collection<Integer> ownerIds);

}
