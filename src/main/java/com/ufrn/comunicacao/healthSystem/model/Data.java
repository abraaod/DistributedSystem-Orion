package com.ufrn.comunicacao.healthSystem.model;

import java.util.List;

public class Data {

    String name;

    String type;

    String value;

    List<Data> metadatas;

    public Data() {
        this.metadatas = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Data> getMetadatas() {
        return metadatas;
    }

    public void setMetadatas(List<Data> metadatas) {
        this.metadatas = metadatas;
    }
}

