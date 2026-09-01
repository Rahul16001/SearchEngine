package com.project.search_engine.controller;


import com.project.search_engine.services.document_processing_service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;

@Controller
public class IndexingController {

    @Autowired
    IndexService indexService;

    @RequestMapping("/index")
    public ResponseEntity<Map<String, Set<String>>> testMethod()
    {
        return new ResponseEntity<>(indexService.createIndex(), HttpStatus.ACCEPTED);
    }
}
