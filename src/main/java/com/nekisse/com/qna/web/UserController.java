package com.nekisse.com.qna.web;

import com.nekisse.com.qna.domain.User;
import com.nekisse.com.qna.domain.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {


    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public String updateForm(@PathVariable Long id, Model model) {
        User user = userRepository.getOne(id);
        System.out.println("user = " + user);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id , User newUser) {
        User user = userRepository.getOne(id);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users";
    }


}
