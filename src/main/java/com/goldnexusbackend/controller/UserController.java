package com.goldnexusbackend.controller;

import com.goldnexusbackend.entity.Res;
import com.goldnexusbackend.entity.UserDetail;
import com.goldnexusbackend.entity.VO;
import com.goldnexusbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goldnexus/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public Res register(@RequestBody VO vo) {
        return userService.register(vo);
    }

    @PostMapping("/login")
    public Res login(@RequestBody VO vo) {
        return userService.login(vo);
    }

    @PostMapping("/updateInfo")
    public Res updateInfo(@RequestBody UserDetail userDetail){
        return userService.UpdateUserDetail(userDetail);
    }
}
