package ua.javaee.springreact.web.controller;

import ua.javaee.springreact.web.bean.Greetings;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;


@RestController
public class HelloWorldController {

    private Logger LOGGER = Logger.getLogger(HelloWorldController.class.getName());

    @GetMapping(path = "hello-world")
    @ResponseBody
    public Greetings sayHello() {
        LOGGER.debug("sayHello");

        return new Greetings("Hello World from Spring!");
    }

}
