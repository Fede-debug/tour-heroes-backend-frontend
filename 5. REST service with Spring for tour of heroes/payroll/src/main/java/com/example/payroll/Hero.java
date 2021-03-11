package com.example.payroll;



import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//supports client using the 'name' attribute in db
//by making a virtual getter and setter for 'name'

@Entity
class Hero {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  

  Hero() {}

  Hero(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Hero))
      return false;
    Hero hero = (Hero) o;
    return Objects.equals(this.id, hero.id) && Objects.equals(this.name, hero.name);
       
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name);
  }

  

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      "}";
  }
  

  
}