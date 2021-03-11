import { Hero } from './hero';

export interface HeroesJSON {
    //id: number;
    //name: string;
    "_embedded" : Hero[]; //doesnìt work if I don't put _embedded in a string
  }