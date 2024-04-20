package com.vama.vamabackend.controllers;

import com.vama.vamabackend.persistence.entity.orders.OrderDeliveryTimeEntity;
import com.vama.vamabackend.persistence.repository.OrderDeliveryTimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryTimeController {

    OrderDeliveryTimeRepository deliveryTimeRepository;

    @GetMapping(value = "/times/all")
    private List<OrderDeliveryTimeEntity> findAllTimes(){
        return deliveryTimeRepository.findAll();
    }

    @PatchMapping(value = "/times/change/day/delivery")
    ResponseEntity<OrderDeliveryTimeEntity> changeWeekDayDelivery(@RequestParam String dayOfWeek){
        if (Arrays.asList(DayOfWeek.values()).stream().map(m -> m.name()).collect(Collectors.toList()).contains(dayOfWeek)){
            OrderDeliveryTimeEntity entity = deliveryTimeRepository.findByDayOfWeek(DayOfWeek.valueOf(dayOfWeek));
            entity.setDelivery(!entity.isDelivery());
            deliveryTimeRepository.save(entity);
            return new ResponseEntity(entity, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(value = "/times/change/day/time/delivery")
    ResponseEntity<OrderDeliveryTimeEntity> changeDeliveryTimeForWeekDay(@RequestParam String dayOfWeek,
                                                                         @RequestBody DeliveryTime requestBody){
        if (Arrays.asList(DayOfWeek.values()).stream().map(m -> m.name()).collect(Collectors.toList()).contains(dayOfWeek)){
            OrderDeliveryTimeEntity entity = deliveryTimeRepository.findByDayOfWeek(DayOfWeek.valueOf(dayOfWeek));
            LocalTime[][] times = entity.getTimes();
            Integer removeIndex = null;
            LocalTime[][] newTimes = null;
            if (times != null){
                for (int x = 0; x < times.length; x++){
                    if (times[x][0].equals(requestBody.getFrom()) && times[x][1].equals(requestBody.getTo())){
                        removeIndex = x;
                    }
                }
                if (removeIndex != null){
                    newTimes = new LocalTime[times.length - 1][];
                    int newIndex = 0;
                    for (int x = 0; x < times.length; x++){
                        if (x != removeIndex){
                            newTimes[newIndex] = times[x];
                            newIndex++;
                        }
                    }
                }else {
                    newTimes = new LocalTime[(1 + times.length)][];
                    for (int x = 0; x < times.length; x++){
                        newTimes[x] = times[x];
                    }
                    newTimes[(times.length)] = new LocalTime[]{requestBody.getFrom(), requestBody.getTo()};
                }
            }else {
                newTimes = new LocalTime[1][];
                newTimes[0] = new LocalTime[]{requestBody.getFrom(), requestBody.getTo()};
            }
            entity.setTimes(newTimes.length > 0 ? newTimes : null);
            deliveryTimeRepository.save(entity);
            return new ResponseEntity(entity, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/times/allow")
    private List<OrderDeliveryTimeEntity> findAllowDaysOfWeek(){
        return deliveryTimeRepository.findAllByDeliveryIsTrue();
    }
}
