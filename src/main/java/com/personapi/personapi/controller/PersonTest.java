package com.personapi.personapi.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class PersonTest {

    public String personTest(){
        return "API TEST";
    }

}
