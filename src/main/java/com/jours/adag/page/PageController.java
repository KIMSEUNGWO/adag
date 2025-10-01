package com.jours.adag.page;

import com.jours.adag.config.ApiDocsConfigurer;
import com.jours.adag.entity.ApiDocInfo;
import com.jours.adag.generator.ApiDocGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ApiDocGenerator generator;
    private final ApiDocsConfigurer configurer;

    @GetMapping("#{apiDocsConfigurer.baseUrl}")
    public String index(Model model) {
        List<ApiDocInfo> docs = generator.getDocs();
        docs.sort(configurer.getGroupConfigurer().sort());
        model.addAttribute("docs", docs);
        model.addAttribute("group", configurer.getGroupConfigurer().getType());
        return "docs";
    }
}
