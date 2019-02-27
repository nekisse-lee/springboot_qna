package com.nekisse.com.qna.web;

import com.nekisse.com.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final AnswerRepository answerRepository;

    @Autowired
    public ApiAnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    private final QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        return answerRepository.save(answer);
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인해야 합니다.");
        }
        Answer answer = answerRepository.getOne(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isSameWriter(loginUser)) {
            return Result.fail("자신의 글만 삭제 할 수 있습니다.");
        }

        answerRepository.deleteById(id);
        return Result.ok();

    }

}
