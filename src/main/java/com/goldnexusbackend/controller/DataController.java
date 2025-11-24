package com.goldnexusbackend.controller;

import com.goldnexusbackend.entity.Res;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goldnexus/user")
public class DataController {

    @RequestMapping("/get_data")
    public Res getData() {
        return new Res(200,"123",null);
    }
}
