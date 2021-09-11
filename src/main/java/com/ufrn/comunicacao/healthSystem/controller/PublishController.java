package com.ufrn.comunicacao.healthSystem.controller;

import com.google.gson.Gson;
import com.ufrn.comunicacao.healthSystem.model.ContextEntity;
import com.ufrn.comunicacao.healthSystem.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private PublishService service;

    @PostMapping
    public ResponseEntity<?> publish(@RequestBody ContextEntity contextEntity){
        return service.createUpdateContext(contextEntity);
    }
}
