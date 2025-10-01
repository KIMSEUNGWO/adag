package com.jours.adag.page;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("#{apiDocsConfigurer.baseUrl}")
    public String index(Model model) {
        System.out.println("성공");
        return "index";
    }
}
