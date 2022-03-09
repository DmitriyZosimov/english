import {Component, OnInit} from '@angular/core';
import {WordService} from "../../../services/word.service";
import {WordModel} from "../../../model/WordModel";

@Component({
  selector: 'app-four-words',
  templateUrl: './four-words.component.html',
  styleUrls: ['./four-words.component.scss']
})
export class FourWordsComponent implements OnInit {

  words?: WordModel[] = [];
  index: number = 0;
  russian: string = '';
  description: string = '';
  invalid: boolean = false;
  selectedIndex: number = -1;

  constructor(private wordService: WordService) {}

  ngOnInit(): void {
    this.getWords();
  }

  private getWords(): void {
    this.index = Math.floor(Math.random() * 3);
    console.log("index ----> " + this.index);
    this.wordService.getFourRandomWords()
      .subscribe(resp => {
        if (resp.body != null) {
          this.words = resp.body;
          this.russian = this.words[this.index].russian;
          this.description = this.words[this.index].description;
        }
      });
  }

  public onWord(selectedIndex: number): void {
    console.log("selected index " + selectedIndex);
    if (selectedIndex != this.index) {
      this.invalid = true;
      this.selectedIndex = selectedIndex;
    } else {
      this.invalid = false;
      this.selectedIndex = -1;
      this.getWords();
    }
  }

}
