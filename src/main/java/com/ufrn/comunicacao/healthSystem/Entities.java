package com.ufrn.comunicacao.healthSystem;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.HashSet;

public class Entities {

    public static HashSet<String> listUsers = new HashSet<>();

    public static volatile HashMap<String, SseEmitter> pressaoEmittersList = new HashMap<>();

    public static volatile HashMap<String, SseEmitter> temperaturaEmittersList = new HashMap<>();

    public static volatile HashMap<String, SseEmitter> saturacaoEmittersList = new HashMap<>();
}
