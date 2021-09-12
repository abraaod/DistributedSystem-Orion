package com.ufrn.comunicacao.healthSystem.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ufrn.comunicacao.healthSystem.Entities;
import com.ufrn.comunicacao.healthSystem.model.ContextElements;
import com.ufrn.comunicacao.healthSystem.model.ContextEntity;
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
import java.util.Arrays;

@Service
public class PublishService {

    public ResponseEntity<?> createUpdateContext(ContextEntity contextEntity){

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost;
        String json = new Gson().toJson(contextEntity);

        if(!Entities.listUsers.contains(contextEntity.getId())) {
            Entities.listUsers.add(contextEntity.getId());
            httpPost = new HttpPost("http://localhost:1026/v1/contextEntities");
        } else {
            httpPost = new HttpPost("http://localhost:1026/v1/updateContext");
            ContextElements contextElements = new ContextElements();
            contextElements.setContextElements(new ArrayList<>(Arrays.asList(contextEntity)));
            JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(contextElements)).getAsJsonObject();
            jsonObject.addProperty("updateAction", "UPDATE");
            json = jsonObject.toString();
        }


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
}
