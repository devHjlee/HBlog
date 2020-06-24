package com.hblog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebTestController {

/*    @GetMapping("/")
    public String webTest(Model model) {
        Map<String,String> testVO = new HashMap<String,String>();
        testVO.put("id","dlgudwo11");
        testVO.put("name","이형재");
        model.addAttribute("testModel", testVO);
        model.addAttribute("message","Spring Boot Main");
        return "index";
    }*/
    @PostMapping("/api/test")
    @ResponseBody 
    public List<String> apiTest(Model model) {
        List<String> result = new ArrayList<String>();
        result.add("dlgudwo");
        return result;
    }
}
