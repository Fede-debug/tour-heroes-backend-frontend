import { Component, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { HeroService } from '../hero.service';

import { HeroesJSON } from '../HeroesJSON';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  heroesJSON : HeroesJSON[];
  heroes: Hero[] = [];

  constructor(private heroService: HeroService) { }

  ngOnInit() {
    this.getHeroes();
  }

  getHeroes(): void {
    this.heroService.getHeroes()
      .subscribe(result => {console.log(result["_embedded"]); 
                  this.heroesJSON = result
                  this.heroes = this.heroesJSON["_embedded"].heroList.slice(1, 5);
                  console.log(this.heroes);
    });
  }  

  /*
  getHeroes(): void {
    this.heroService.getHeroes()
      .subscribe(heroes => this.heroes = heroes.slice(1, 5));
  }
  */

}


