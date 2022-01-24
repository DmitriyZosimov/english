package com.myenglish.web.angular;

import com.myenglish.model.Word;
import com.myenglish.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TranslateFromRussianController {

    private WordService wordService;

    public TranslateFromRussianController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping(path = "/translateFromRussian", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Word>> getFourRandomWords() {
        List<Word> words = wordService.getFourRandomWords();
        return words != null ? new ResponseEntity<>(words, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
