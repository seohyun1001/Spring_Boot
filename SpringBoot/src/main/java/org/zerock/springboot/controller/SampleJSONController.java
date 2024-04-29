package org.zerock.springboot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SampleJSONController {
    @Tag(name = "test", description = "test")

    @GetMapping("/helloArr")
    public String[] helloArr(){

        log.info("helloArr");
        return new String[]{"hello","world"};

    }

    @Tag(name = "lunch menu test", description = "lunch menu test")

    @GetMapping("/lunchMenu")
    public String[] lunchMenu(){

        log.info("lunchMenu--------------");
        return new String[]{"배고파!","뭐 먹지!"};

    }

}
