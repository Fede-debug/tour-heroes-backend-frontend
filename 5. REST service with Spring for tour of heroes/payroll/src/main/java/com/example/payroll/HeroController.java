package com.example.payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
class HeroController {

    private final HeroRepository repository;
    private final HeroModelAssembler assembler;

    HeroController(HeroRepository repository,  HeroModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    /*
     * @GetMapping("/heroes") List<Hero> all() { return repository.findAll();
     * } // end::get-aggregate-root[]
     */


    /*
    CollectionModel<> is another Spring HATEOAS container; 
    it’s aimed at encapsulating collections of resources—instead of a single resource entity, 
    like EntityModel<> from earlier. CollectionModel<>, too, lets you include links.

    Since we’re talking REST, it should encapsulate collections of hero resources.

    That’s why you fetch all the heroes, 
    but then transform them into a list of EntityModel<Hero> objects. (Thanks Java 8 Streams!)
    */ 

    
    @GetMapping("/heroes")
    CollectionModel<EntityModel<Hero>> all() {

        List<EntityModel<Hero>> heroes = repository.findAll().stream()
                .map(assembler::toModel
                    /*hero -> EntityModel.of(hero,
                        linkTo(methodOn(HeroController.class).one(hero.getId())).withSelfRel(),
                        linkTo(methodOn(HeroController.class).all()).withRel("heroes"))*/)
                .collect(Collectors.toList());

        return CollectionModel.of(heroes, linkTo(methodOn(HeroController.class).all()).withSelfRel());
    }

    /*
    @PostMapping("/heroes")
    Hero newHero(@RequestBody Hero newHero) {
        return repository.save(newHero);
    }

    The new Hero object is saved as before. 
    But the resulting object is wrapped using the HeroModelAssembler.

    Spring MVC’s ResponseEntity is used to create an HTTP 201 Created status message. 
    This type of response typically includes a Location response header, and we use the URI derived from the model’s self-related link.

    Additionally, return the model-based version of the saved object.
    */

    
    @PostMapping("/heroes")
    ResponseEntity<?> newHero(@RequestBody Hero newHero) {

        EntityModel<Hero> entityModel = assembler.toModel(repository.save(newHero));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item
    /*
     * @GetMapping("/heroes/{id}") Hero one(@PathVariable Long id) {
     * 
     * return repository.findById(id) .orElseThrow(() -> new
     * HeroNotFoundException(id)); }
     */

    // mapping with HATEOAS links
    /*
     * The return type of the method has changed from Hero to
     * EntityModel<Hero>. EntityModel<T> is a generic container from Spring
     * HATEOAS that includes not only the data but a collection of links.
     * 
     * linkTo(methodOn(HeroController.class).one(id)).withSelfRel() asks that
     * Spring HATEOAS build a link to the HeroController 's one() method, and
     * flag it as a self link.
     * 
     * linkTo(methodOn(HeroController.class).all()).withRel("heroes") asks
     * Spring HATEOAS to build a link to the aggregate root, all(), and call it
     * "heroes".
     */


    
    @GetMapping("/heroes/{id}")
    EntityModel<Hero> one(@PathVariable Long id) {

        Hero hero = repository.findById(id) //
                .orElseThrow(() -> new HeroNotFoundException(id));
        /*    
        return EntityModel.of(hero, //
                linkTo(methodOn(HeroController.class).one(id)).withSelfRel(),
                linkTo(methodOn(HeroController.class).all()).withRel("heroes"));
                */

        return assembler.toModel(hero);        
    }

    
    @PutMapping("/heroes/{id}")
    Hero replaceHero(@RequestBody Hero newHero, @PathVariable Long id) {

        return repository.findById(id).map(hero -> {
            hero.setName(newHero.getName());
            //hero.setRole(newHero.getRole());
            return repository.save(hero);
        }).orElseGet(() -> {
            newHero.setId(id);
            return repository.save(newHero);
        });
    }
    /*
    @DeleteMapping("/heroes/{id}")
    void deleteHero(@PathVariable Long id) {
        repository.deleteById(id);
    }
    */

    
    @DeleteMapping("/heroes/{id}")
    ResponseEntity<?> deleteHero(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
}
}