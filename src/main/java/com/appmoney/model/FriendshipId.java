package com.appmoney.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FriendshipId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer ownerId;
  private Integer friendId;

  public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }

  public Integer getFriendId() {
    return friendId;
  }

  public void setFriendId(Integer friendId) {
    this.friendId = friendId;
  }

}
