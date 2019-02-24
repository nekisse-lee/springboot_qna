package com.nekisse.com.qna.web;

import com.nekisse.com.qna.domain.User;
import com.nekisse.com.qna.domain.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {


    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            System.out.println("Login failure!");
            return "redirect:/users/loginForm";
        }

        if (!user.matchPassword(password)) {
            System.out.println("Login failure!");
            return "redirect:/users/loginForm";
        }

        System.out.println("Login Success!");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);


        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
//        Enumeration<String> attributeNames = session.getAttributeNames();
//        String sessionName = attributeNames.nextElement();
//        if (sessionName != null) {
//            System.out.println("sessionName = " + sessionName);
//            session.removeAttribute(sessionName);
//        }
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        System.out.println("Logout Success!!");
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("")
    public String home(User user) {
        System.out.println("user = " + user);
        userRepository.save(user);
        return "redirect:/users";
    }


    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }


    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalStateException("You can't update the anther user");
        }

        User user = userRepository.getOne(id);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }


    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {

        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User userSession = HttpSessionUtils.getUserFromSession(session);
        if (!userSession.matchId(id)) {
            throw  new IllegalStateException("You can not update other users!!!");
        }

        User user = userRepository.getOne(id);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }


}
