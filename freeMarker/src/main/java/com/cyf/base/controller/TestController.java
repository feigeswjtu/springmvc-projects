package com.cyf.base.controller;

import com.cyf.base.model.Person;
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

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/hello")
    public Object hello(String name){
        Person person = new Person();
        person.setName(name);
        return person;
    }
}
