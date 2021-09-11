package com.ufrn.comunicacao.healthSystem.model;

import java.util.List;

public class ContextEntity {

    String type;

    boolean isPattern = false;

    String id;

    List<Data> attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPattern() {
        return isPattern;
    }

    public void setPattern(boolean pattern) {
        isPattern = pattern;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Data> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Data> attributes) {
        this.attributes = attributes;
    }
}