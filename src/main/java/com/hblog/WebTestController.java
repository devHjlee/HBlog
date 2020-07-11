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

    @GetMapping("/pxlTest")
    public String webTest(Model model) {
        return "/dev/pxlApiTest";
    }
    @PostMapping("/api/test")
    @ResponseBody 
    public List<String> apiTest(Model model) {
        List<String> result = new ArrayList<String>();
        result.add("dlgudwo");
        return result;
    }
}
