package com.ufrn.comunicacao.healthSystem.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ufrn.comunicacao.healthSystem.Entities;
import com.ufrn.comunicacao.healthSystem.model.ContextSubscribe;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SubscribeService {

//    private final CopyOnWriteArrayList<SseEmitter> pressaoEmitters = new CopyOnWriteArrayList<>();
//    private final CopyOnWriteArrayList<SseEmitter> temperaturaEmitters = new CopyOnWriteArrayList<>();
//    private final CopyOnWriteArrayList<SseEmitter> saturacaoEmitters = new CopyOnWriteArrayList<>();

    public ResponseEntity<?> createSubscriptionInOrion(ContextSubscribe contextSubscriber){
        HttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://localhost:1026/v1/subscribeContext");
        String json = new Gson().toJson(contextSubscriber);

        StringEntity entity = null;

        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(entity != null){
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(response.getStatusLine().getStatusCode() == 200)
                return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void sendSseEvent(Object object, int control) {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(object)).getAsJsonObject();
        String id = jsonObject.getAsJsonArray("contextResponses").get(0).getAsJsonObject().getAsJsonObject("contextElement").get("id").getAsString();;
        Entities.Tuple<String, String> position = Entities.listUsers.get(id);
        jsonObject.addProperty("position", position.toString());

        object = new Gson().fromJson(jsonObject, Object.class);

        switch (control){
            case 0:
                if(Entities.pressaoEmittersList.containsKey(id)){
                    SseEmitter sseEmitter = Entities.pressaoEmittersList.get(id);
                    try {
                        sseEmitter.send(object);
                    } catch (Exception e) {
                        sseEmitter.completeWithError(e);
                        deadEmitters.add(sseEmitter);
                    }
                }

                Entities.pressaoEmittersList.values().removeAll(deadEmitters);
                break;

            case 1:
                if(Entities.temperaturaEmittersList.containsKey(id)){
                    SseEmitter sseEmitter = Entities.temperaturaEmittersList.get(id);
                    try {
                        sseEmitter.send(object);
                    } catch (Exception e) {
                        sseEmitter.completeWithError(e);
                        deadEmitters.add(sseEmitter);
                    }
                }

                Entities.temperaturaEmittersList.values().removeAll(deadEmitters);
                break;

            case 2:
                if(Entities.saturacaoEmittersList.containsKey(id)){
                    SseEmitter sseEmitter = Entities.saturacaoEmittersList.get(id);
                    try {
                        sseEmitter.send(object);
                    } catch (Exception e) {
                        sseEmitter.completeWithError(e);
                        deadEmitters.add(sseEmitter);
                    }
                }

                Entities.saturacaoEmittersList.values().removeAll(deadEmitters);
                break;

            default:
                break;
        }
    }
}
