package com.nekisse.com.qna.web;

import com.nekisse.com.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    private final QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }


}
