package com.nekisse.com.qna.web;

import com.nekisse.com.qna.domain.User;
import com.nekisse.com.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    private final UserRepository userRepository;

    @Autowired
    public ApiUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public User show(@PathVariable Long id) {
        return userRepository.getOne(id);
    }




}
