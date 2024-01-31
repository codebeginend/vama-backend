package com.vama.vamabackend.services;

import com.vama.vamabackend.models.NomenclatureMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RabbitMQJsonConsumer {

    ProductsService productsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(List<NomenclatureMessage> message){
        productsService.updateProducts(message);
        LOGGER.info(String.format("Received JSON message size -> %s", message.size()));
    }
}