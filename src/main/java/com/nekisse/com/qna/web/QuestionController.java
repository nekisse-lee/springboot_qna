package com.nekisse.com.qna.web;

import com.nekisse.com.qna.domain.Question;
import com.nekisse.com.qna.domain.QuestionRepository;
import com.nekisse.com.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "qna/form";
    }


    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "users/loginForm";

        User userSession = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(userSession, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.getOne(id));
        return "qna/show";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(id);
        if (!question.isSameWriter(loginUser)) {
            return "users/loginForm";
        }

        model.addAttribute("question", question);
        return "qna/updateForm";

    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(id);
        if (!question.isSameWriter(loginUser)) {
            return "users/loginForm";
        }

        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(id);
        if (!question.isSameWriter(loginUser)) {
            return "users/loginForm";
        }

        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
