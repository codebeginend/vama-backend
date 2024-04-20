package com.vama.vamabackend.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryTime{
    private LocalTime from;
    private LocalTime to;
}