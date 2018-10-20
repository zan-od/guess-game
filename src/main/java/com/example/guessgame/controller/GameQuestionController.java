package com.example.guessgame.controller;

import com.example.guessgame.model.GameQuestion;
import com.example.guessgame.service.GameQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GameQuestionController {

    private GameQuestionService gameQuestionService;

    @Autowired
    public void setGameQuestionService(GameQuestionService gameQuestionService){
        this.gameQuestionService = gameQuestionService;
    }

    @GetMapping("/admin/questions")
    public String questions(Model model){

        model.addAttribute("questions", gameQuestionService.listQuestions());

        return "questions";
    }

    @GetMapping("/admin/question/add")
    public String addQuestion(Model model){

        GameQuestion gameQuestion = new GameQuestion();
        model.addAttribute("gameQuestion", gameQuestion);

        return "question";
    }

    @GetMapping("/admin/question/edit/{questionId}")
    public String editQuestion(Model model, @PathVariable("questionId") Long questionId){

        GameQuestion gameQuestion = gameQuestionService.getQuestion(questionId);
        model.addAttribute("gameQuestion", gameQuestion);

        return "question";
    }

    @PostMapping({"/admin/question/add", "/admin/question/edit/{questionId}"})
    public String saveQuestion(@ModelAttribute("gameQuestion") GameQuestion question){

        gameQuestionService.saveQuestion(question);

        return "redirect:/admin/questions";
    }
}
