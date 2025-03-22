package com.flt.flt.Controllers;

import com.flt.flt.Services.CardService;
import com.flt.flt.models.Categories;
import com.flt.flt.models.Difficulties;
import com.flt.flt.models.QuizCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/card/{id}")
    public ResponseEntity<?> getCardById(@PathVariable long id) {
        Optional<QuizCard> card = cardService.findCardById(id);
        if(card.isPresent()) {
            return new ResponseEntity<>(card, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Card not found", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("card/add")
    public ResponseEntity<?> addCard(@RequestBody QuizCard quizCard) {
        cardService.saveCard(quizCard);
        return new ResponseEntity<>(Map.of("message", "Added Successfully"),HttpStatus.OK);
    }

    @PostMapping("/card/edit/{id}")
    public ResponseEntity<?> updateCard(@PathVariable long id, @RequestBody QuizCard quizCard) {
        QuizCard newQuizCard = cardService.findCardById(id).orElseThrow();
        newQuizCard.setQuestion(quizCard.getQuestion());
        newQuizCard.setAnswer(quizCard.getAnswer());
        newQuizCard.setDifficulty(quizCard.getDifficulty());
        newQuizCard.setCategory(quizCard.getCategory());
        cardService.updateCard(newQuizCard);
        return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
    }

    @PostMapping("card/delete/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable long id) {
        cardService.deleteCard(id);
        return new ResponseEntity<>(Map.of("message","Deleted Successfully"),HttpStatus.OK);
    }

    @GetMapping("/maxCardNumber")
    public ResponseEntity<?> maxCardNumber() {
        long num = cardService.findMaxCardNumber();
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping("/allCards")
    public ResponseEntity<?> allCards() {
        List<QuizCard> allCards = cardService.getAllCards();
        if(allCards != null) {
            return new ResponseEntity<>(allCards,HttpStatus.OK);
        }
        return new ResponseEntity<>("No cards are present", HttpStatus.OK);
    }

    @GetMapping("/flashcards")
    public List<QuizCard> getFlashcards(@RequestParam(value = "difficulty", required = false) Difficulties difficulty,
                                         @RequestParam(value = "category", required = false) Categories category) {
        return cardService.getFilteredCards(difficulty, category);
    }
}
