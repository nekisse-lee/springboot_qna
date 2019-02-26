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
        try {
            Question question = questionRepository.getOne(id);
            hasPermission(session, question);
            model.addAttribute("question", question);
            return "qna/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    private boolean hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(loginUser)) {
            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다..");
        }

        return true;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents,Model model, HttpSession session) {
        try {
            Question question = questionRepository.getOne(id);
            hasPermission(session, question);
            question.update(title, contents);
            questionRepository.save(question);
            return String.format("redirect:/questions/%d", id);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,Model model, HttpSession session) {
        try {
            Question question = questionRepository.getOne(id);
            hasPermission(session, question);
            questionRepository.deleteById(id);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }
}
