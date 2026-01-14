package com.example.hw.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {

    @GetMapping("/main")
    public String index(){
        System.out.println("오긴해???");
        return "index";
    }
}
