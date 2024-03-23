package com.fridgerator.DynamicConsumer.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class GameOfThrone {
    private String charater;
    private String city;
    private String dragon;
    private String house;
    private String quote;
    private int id;

    public byte[] toBytes() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsBytes(this);
    }
}
