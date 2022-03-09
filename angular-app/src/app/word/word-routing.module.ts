import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FourWordsComponent} from "./components/four-words/four-words.component";
import {WordComponent} from "./word.component";

const routes: Routes = [
  {
    path: '',
    component: WordComponent,
    children: [
      {
        path: '',
        component: FourWordsComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WordRoutingModule { }
