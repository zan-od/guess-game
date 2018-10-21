package com.example.guessgame.service;

import com.example.guessgame.model.GameQuestion;
import com.example.guessgame.repository.GameQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameQuestionServiceImpl implements GameQuestionService{

    private GameQuestionRepository gameQuestionRepository;

    @Autowired
    public void setGameQuestionRepository(GameQuestionRepository gameQuestionRepository){
        this.gameQuestionRepository = gameQuestionRepository;
    }

    @Override
    public void saveQuestion(GameQuestion gameQuestion){
        gameQuestionRepository.save(gameQuestion);
    }

    @Override
    public List<GameQuestion> listQuestions(){
        //TODO get list from stream in right way or use Iterable
        List<GameQuestion> list = new ArrayList<>();
        gameQuestionRepository.findAll().forEach(question -> {
            list.add(question);
        });

        return list;
    }

    @Override
    public GameQuestion getQuestion(Long id) {
        //TODO use Optional
        return gameQuestionRepository.findById(id).get();
    }

    @Override
    public GameQuestion getRandomQuestion() {
        List<GameQuestion> list = listQuestions();

        Random random = new Random();
        int index = random.nextInt(list.size());

        return list.get(index);
    }

    @Override
    public boolean isCorrectAnswer(GameQuestion question, String answer) {
        if (answer == null){
            return false;
        }

        return getTrimLowerCase(answer).equals(getTrimLowerCase(question.getAnswer()));
    }

    private String getTrimLowerCase(String value){
        return value.trim().toLowerCase();
    }
}
