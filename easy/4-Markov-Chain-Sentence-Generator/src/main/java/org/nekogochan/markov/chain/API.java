package org.nekogochan.markov.chain;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

@SpringBootApplication
@Controller
@RequestMapping("/shit-generator")
public class API {

    @Bean
    @SessionScope
    ShitGenerator shitGenerator() {
        return new ShitGenerator();
    }

    @Autowired
    private ShitGenerator shitGenerator;

    @PostMapping("/shitpost/set")
    @ResponseBody
    public String setShitpostText(@RequestParam("text") String text) {
        LoggerFactory.getLogger("").info(text);
        shitGenerator.setMarkovChain(text);
        return "OK";
    }

    @GetMapping("/shitpost/get/{sentences}")
    @ResponseBody
    public String getShitpost(@PathVariable("sentences") int sentences) {
        LoggerFactory.getLogger("").info(String.valueOf(sentences));
        return shitGenerator.getShitpost(sentences);
    }

    public static void main(String[] args) {
        SpringApplication.run(API.class);
    }
}
