package com.cyf.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author chenyafei
 * @version 1.0
 * @date 05/02/2018 23:18
 */

@Controller
@RequestMapping("/page")
public class PageTestController {
    @GetMapping("/hello")
    public String hello(String name, Model model){
        System.out.println(name);
        model.addAttribute("name", name);
        return "app";
    }
}
