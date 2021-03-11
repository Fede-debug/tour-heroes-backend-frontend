import { Component, OnInit } from '@angular/core';
import { Hero } from '../hero';

import { HeroesJSON } from '../HeroesJSON';

//import { HEROES } from '../mock-heroes';

import { HeroService } from '../hero.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})
export class HeroesComponent implements OnInit {

  //heroes = HEROES;
  //selectedHero : Hero;

  heroes: Hero[];
  heroesJSON : HeroesJSON[];

  /*
  hero : Hero = {
    id :1 , 
    name: 'Windstorm'
  };
  */

  constructor(private heroService: HeroService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.getHeroes();
  }

  /*
  getHeroes(): void {
    this.heroes = this.heroService.getHeroes();
  }
  */

  getHeroes(): void {
    //console.log(this.heroService.getHeroes()); 
    this.heroService.getHeroes()
        .subscribe(result => {console.log(result["_embedded"]); 
                              this.heroesJSON = result
                              this.heroes = this.heroesJSON["_embedded"].heroList;
                              console.log(this.heroes);
                            });

        
    //this.heroes = this.heroesJSON["_embedded"].heroList;
    //console.log(this.heroes);

    //this.heroes = this.heroes._embedded    
    //console.log(this.heroes);   
  }

  /*
  onSelect(hero: Hero): void {
    this.selectedHero = hero;
    this.messageService.add(`HeroesComponent: Selected hero id=${hero.id}`);
  }
  */

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.heroService.addHero({ name } as Hero)
      .subscribe(hero => {
        this.heroes.push(hero);
      });
  }

  delete(hero: Hero): void {
    this.heroes = this.heroes.filter(h => h !== hero);
    this.heroService.deleteHero(hero).subscribe();
  }
}
