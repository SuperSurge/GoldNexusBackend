package com.goldnexusbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetail {
    private String username;
    private String name;
    private String gender;
    private int age;
    private int real_name_authentication;
    private int phone;
    private String address;
    private int marriage;
    private int job;
    private int education;
    private int earnings;
}
