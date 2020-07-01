package org.konanok.demo.dubbo.consumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.konanok.demo.dubbo.api.IDubboService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class DubboController {

    @DubboReference(check = false)
    private IDubboService dubboService;


    @GetMapping("/hello")
    public String hello() {
        return dubboService.hello();
    }

}
