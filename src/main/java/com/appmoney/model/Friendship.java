package com.appmoney.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Friendship {

  @EmbeddedId
  private FriendshipId id;

  @ManyToOne
  @MapsId("ownerId")
  private User owner;

  @ManyToOne
  @MapsId("friendId")
  private User friend;

  public FriendshipId getId() {
    return id;
  }

  public void setId(FriendshipId id) {
    this.id = id;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public User getFriend() {
    return friend;
  }

  public void setFriend(User friend) {
    this.friend = friend;
  }

}
