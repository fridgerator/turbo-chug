package com.fridgerator.DynamicConsumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Customer {
    private String name;
    private String address;
}
