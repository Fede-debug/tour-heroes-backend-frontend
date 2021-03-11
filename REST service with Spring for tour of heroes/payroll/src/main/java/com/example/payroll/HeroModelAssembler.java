package com.example.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

//converting a non-model object (Hero) into a model-based object EntityModel<Hero>
//so you don't have to repeat code in the controller

/*
This simple interface has one method: toModel(). 
It is based on converting a non-model object (Hero) into a model-based object (EntityModel<Hero>).

*/ 

@Component
class HeroModelAssembler implements RepresentationModelAssembler<Hero, EntityModel<Hero>> {

  @Override
  public EntityModel<Hero> toModel(Hero hero) {

    return EntityModel.of(hero, //
        linkTo(methodOn(HeroController.class).one(hero.getId())).withSelfRel(),
        linkTo(methodOn(HeroController.class).all()).withRel("hero"));
  }
}