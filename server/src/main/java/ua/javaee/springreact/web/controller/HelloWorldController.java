package ua.javaee.springreact.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.javaee.springreact.web.bean.Greetings;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {

    private Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @GetMapping(path = "hello-world")
    @ResponseBody
    public Greetings sayHello() {
        logger.debug("sayHello");
        return new Greetings("Hello World from Spring!");
    }

}
