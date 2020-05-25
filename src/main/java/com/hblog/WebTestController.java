package com.hblog;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebTestController {

    @GetMapping("/")
    public String webTest(Model model) {
        Map<String,String> testVO = new HashMap<String,String>();
        testVO.put("id","dlgudwo11");
        testVO.put("name","이형재");
        model.addAttribute("testModel", testVO);
        model.addAttribute("message","Spring Boot Main");
        return "index";
    }
}
