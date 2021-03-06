package com.myenglish.web.angular;

import com.myenglish.kafka.logger.LoggerProducer;
import com.myenglish.model.FourWordsDto;
import com.myenglish.model.Word;
import com.myenglish.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class TranslateFromRussianController {

    private WordService wordService;
    private LoggerProducer logger;

    public TranslateFromRussianController(WordService wordService, LoggerProducer logger) {
        this.wordService = wordService;
        this.logger = logger;
    }

    @GetMapping(path = "/translateFromRussian", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FourWordsDto> getFourRandomWords() {
        logger.debug("using getFourRandomWords...", TranslateFromRussianController.class);
        FourWordsDto dto = wordService.getFourRandomWords();
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
