package com.ufrn.comunicacao.healthSystem;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;

public class Entities {

    public static HashMap<String, Tuple<String, String>> listUsers = new HashMap();

    public static volatile HashMap<String, SseEmitter> pressaoEmittersList = new HashMap<>();

    public static volatile HashMap<String, SseEmitter> temperaturaEmittersList = new HashMap<>();

    public static volatile HashMap<String, SseEmitter> saturacaoEmittersList = new HashMap<>();

    public static class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position {" +
                    "lat=" + x +
                    ", long=" + y +
                    '}';
        }
    }
}

