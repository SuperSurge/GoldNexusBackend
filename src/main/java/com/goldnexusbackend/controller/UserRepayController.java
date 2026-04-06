package com.goldnexusbackend.controller;

import com.goldnexusbackend.entity.Repay;
import com.goldnexusbackend.entity.Res;
import com.goldnexusbackend.service.UserRepayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goldnexus/user")
@RequiredArgsConstructor
public class UserRepayController {
    private final UserRepayService userRepayService;

    //未测试
    @PostMapping("/repay")
    public Res repay(@RequestBody Repay repay){
        return userRepayService.repay(repay.getApplicationId(), repay.getPeriodNumber(), repay.getAmount());
    }
}
