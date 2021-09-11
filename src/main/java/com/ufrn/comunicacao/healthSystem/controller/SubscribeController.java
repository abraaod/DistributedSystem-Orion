package com.ufrn.comunicacao.healthSystem.controller;

import com.google.gson.Gson;
import com.ufrn.comunicacao.healthSystem.Entities;
import com.ufrn.comunicacao.healthSystem.model.ContextSubscribe;
import com.ufrn.comunicacao.healthSystem.service.SubscribeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService service;

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody ContextSubscribe contextSubscribe){
        return service.createSubscriptionInOrion(contextSubscribe);
    }

    @PostMapping("/pressao")
    public void subscribePressao(@RequestHeader @RequestBody @RequestAttribute Object orionNotification){
        service.sendSseEvent(orionNotification, 0);
    }

    @ApiOperation(value = "Inscrever-se para receber notificações de pressão de um usuário no dashboard",
            response = SseEmitter.class,
            produces = "application/stream+json")
    @GetMapping(value = "/pressao/dashboard/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribePressaoDashboard(HttpServletResponse response, @PathVariable String id){

        response.setHeader("Cache-Control", "no-store");

        SseEmitter emitter = new SseEmitter(0L);

        Entities.pressaoEmittersList.put(id, emitter);

        emitter.onCompletion(() -> Entities.pressaoEmittersList.remove(id));
        emitter.onTimeout(() -> Entities.pressaoEmittersList.remove(id));
        emitter.onError((throwable) -> Entities.pressaoEmittersList.remove(id));

        return emitter;
    }

    @PostMapping("/temperatura")
    public void subscribeTemperatura(@RequestHeader @RequestBody @RequestAttribute Object orionNotification){
        service.sendSseEvent(orionNotification, 1);
    }

    @ApiOperation(value = "Inscrever-se para receber notificações de temperatura de um usuário no dashboard",
            response = SseEmitter.class,
            produces = "application/stream+json")
    @GetMapping(value = "/temperatura/dashboard/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeTemperaturaDashboard(HttpServletResponse response, @PathVariable String id){

        response.setHeader("Cache-Control", "no-store");

        SseEmitter emitter = new SseEmitter(0L);

        Entities.temperaturaEmittersList.put(id, emitter);

        emitter.onCompletion(() -> Entities.temperaturaEmittersList.remove(id));
        emitter.onTimeout(() -> Entities.temperaturaEmittersList.remove(id));
        emitter.onError((throwable) -> Entities.temperaturaEmittersList.remove(id));

        return emitter;
    }

    @PostMapping("/saturacao")
    public void subscribeSaturacao(@RequestHeader @RequestBody @RequestAttribute Object orionNotification){
        service.sendSseEvent(orionNotification, 2);
    }

    @ApiOperation(value = "Inscrever-se para receber notificações de saturação de um usuário no dashboard",
            response = SseEmitter.class,
            produces = "application/stream+json")
    @GetMapping(value = "/saturacao/dashboard/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeSaturacaoDashboard(HttpServletResponse response, @PathVariable String id){

        response.setHeader("Cache-Control", "no-store");

        SseEmitter emitter = new SseEmitter(0L);

        Entities.saturacaoEmittersList.put(id, emitter);

        emitter.onCompletion(() -> Entities.saturacaoEmittersList.remove(id));
        emitter.onTimeout(() -> Entities.saturacaoEmittersList.remove(id));
        emitter.onError((throwable) -> Entities.saturacaoEmittersList.remove(id));

        return emitter;
    }

}
