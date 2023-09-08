package com.banco.sucursal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping(path = "/helloworld")
    public String saludar() {
        return "Hello world";
    }
}
