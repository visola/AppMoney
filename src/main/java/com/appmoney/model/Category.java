package com.appmoney.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@JsonSerialize(using=CategorySerializer.class)
public class Category {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;
  private String name;

  @ManyToOne
  private Category parent;

  @ManyToOne
  private User owner;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category getParent() {
    return parent;
  }

  public void setParent(Category parent) {
    this.parent = parent;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

}
