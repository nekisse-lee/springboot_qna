package com.nekisse.com.qna.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class WelcomeController {


    @GetMapping("/helloworld")
    public String welcome(Model model) {
        List<MyModel> repo = Arrays.asList(
            new MyModel("nekisse"),
            new MyModel("perci"),
            new MyModel("romitasha"));
        model.addAttribute("repo", repo);

        return "welcome";
    }
}
