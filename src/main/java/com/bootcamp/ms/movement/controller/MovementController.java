package com.bootcamp.ms.movement.controller;

import com.bootcamp.ms.movement.entity.Movement;
import com.bootcamp.ms.movement.service.BankAccountService;
import com.bootcamp.ms.movement.service.MovementService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
public class MovementController {

    @Autowired
    private MovementService movementService;

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping(value = "/all")
    public Flux<Movement> getAll(){
        return movementService.findAll();
    }

    @GetMapping(value = "/findAllByBankAccount/{bankAccount}")
    public Flux<Movement> getByIdBankAccount(@PathVariable String bankAccount){
        return movementService.findByBankAccount(bankAccount);
    }

    @GetMapping(value = "/findBy/{id}")
    public Mono<Movement> findBy(@PathVariable String id){
        return movementService.findById(id);
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movement> create(@RequestBody Movement movement){

        Double currentAmount = bankAccountService.checkBalance(movement.getIdBankAccount());

        return movementService.save(movement)
                .flatMap(m -> {
                   switch (movement.getType()){
                       case "Débito":
                           switch (m.getDescription()){
                               case "D":
                           }
                           break;
                       case "Crédito":


                   }
                });
}
