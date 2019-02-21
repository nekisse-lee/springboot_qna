package com.nekisse.com.qna.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WelcomeController {


    @GetMapping("/helloworld")
    public String a(String name, int age,  Model model) {
        System.out.println(name);
        model.addAttribute("name", name);
        model.addAttribute("age", age);

        return "welcome";
    }
}
