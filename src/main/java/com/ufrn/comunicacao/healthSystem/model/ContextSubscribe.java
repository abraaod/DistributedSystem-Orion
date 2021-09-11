package com.ufrn.comunicacao.healthSystem.model;

import java.util.List;

public class ContextSubscribe {

    List<Data> entities;

    List<String> attributes;

    String reference;

    String duration;

    List<Type> notifyConditions;

    String throttling;

    private class Type{
        String type;
    }

    public List<Data> getEntities() {
        return entities;
    }

    public void setEntities(List<Data> entities) {
        this.entities = entities;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Type> getNotifyConditions() {
        return notifyConditions;
    }

    public void setNotifyConditions(List<Type> notifyConditions) {
        this.notifyConditions = notifyConditions;
    }

    public String getThrottling() {
        return throttling;
    }

    public void setThrottling(String throttling) {
        this.throttling = throttling;
    }
}
