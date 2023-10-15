package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.io.IOException;
import java.time.LocalDate;
import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {
    
    @Autowired
    private SearchAutoCompleteService searchAutoCompleteService;

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping(value = "/ping")
    public String getPing() {
        String response_val = "pong";
        System.out.println("Ping has been occurred");
        // Business Logic 추가
        return response_val;
    }

    @GetMapping(value = "/searchConsonant")
    public String searchConsonant(@RequestParam(value = "searchText") String searchText) {
        logger.info("!!! searchConsonant called with parameter : {} !!!", searchText);
        return searchAutoCompleteService.getConsonants(searchText);
    }

    @GetMapping(value = "/searchAutoCompletemWithCons")
    @ResponseBody
    public ArrayList<String> searchAutoComplete(@RequestParam(value = "searchText") String searchText) {
        logger.info("!!! searchAutoCompletemWithCons called with parameter : {} !!!", searchText);
        return searchAutoCompleteService.getAutoComplete(searchText);
    }
}