import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";

const routes: Routes = [
    {
    path: '',
    redirectTo: '/translateFromRussian',
    pathMatch: 'full'
  },
  {
    path: 'translateFromRussian',
    loadChildren: () => import('./word/word.module').then(m => m.WordModule)
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
