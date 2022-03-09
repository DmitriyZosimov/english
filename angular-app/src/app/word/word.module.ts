import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WordRoutingModule } from './word-routing.module';
import { FourWordsComponent } from './components/four-words/four-words.component';
import { WordComponent } from './word.component';
import {WordService} from "../services/word.service";


@NgModule({
  declarations: [
    FourWordsComponent,
    WordComponent
  ],
  imports: [
    CommonModule,
    WordRoutingModule
  ],
  providers: [WordService]
})
export class WordModule { }
