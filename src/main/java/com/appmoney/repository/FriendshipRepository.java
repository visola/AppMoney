package com.appmoney.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.appmoney.model.Friendship;
import com.appmoney.model.FriendshipId;

public interface FriendshipRepository extends PagingAndSortingRepository<Friendship, FriendshipId> {

  List<Friendship> findByFriendId(Integer id);

}
