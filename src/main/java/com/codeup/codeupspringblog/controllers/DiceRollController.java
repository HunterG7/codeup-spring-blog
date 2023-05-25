package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Random;

@Controller
public class DiceRollController {

    @GetMapping("/roll-dice")
    public String rollDice() {
        return "rollDice";
    }

    @GetMapping("/roll-dice/{guess}")
    public String rollDiceGuess(@PathVariable int guess, Model model){
        Random random = new Random();
        int correctCount = 0;
        ArrayList<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
           int roll = random.nextInt(6) + 1;
           rolls.add(roll);
           if (guess == roll){
               correctCount++;
           }
        }

        model.addAttribute("count", correctCount);
        model.addAttribute("guess", guess);
        model.addAttribute("rolls", rolls);
        return "rollDice";
    }
}
