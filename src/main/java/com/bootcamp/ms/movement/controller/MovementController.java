package com.bootcamp.ms.movement.controller;

import com.bootcamp.ms.movement.entity.Movement;
import com.bootcamp.ms.movement.service.BankAccountService;
import com.bootcamp.ms.movement.service.BankCreditService;
import com.bootcamp.ms.movement.service.MovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/movement")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankCreditService bankCreditService;

    private final Logger logger = LoggerFactory.getLogger(MovementController.class);

    @GetMapping(value = "/all")
    public Flux<Movement> getAll(){
        return movementService.findAll();
    }

    @GetMapping("/find/{bankAccount}")
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

        return movementService.save(movement)
                .flatMap(m -> {
                   switch (movement.getType()){
                       case "Débito":
                           logger.info("primer switch");
                           switch (m.getDescription()){
                               case "D":
                                   logger.info("segundo switch");
                                   bankAccountService.findById(m.getIdBankAccount())
                                           .flatMap(b -> {
                                               logger.info(String.valueOf(b.getAmount()));
                                               b.setAmount((b.getAmount() + movement.getAmount()));
                                               logger.info(String.valueOf(b.getAmount()));
                                               logger.info("en bank");
                                               return bankAccountService.save(b);
                                           }).subscribe();
                                   break;
                               case "R":
                                   bankAccountService.findById(m.getIdBankAccount())
                                           .flatMap(b -> {
                                               b.setAmount((b.getAmount() - movement.getAmount()));

                                               return bankAccountService.save(b);
                                           }).subscribe();
                                   break;
                           }
                           break;
                       case "Crédito":
                           logger.info("primer switch");
                           switch (m.getDescription()){
                               case "C":
                                   logger.info("segundo switch");
                                   bankCreditService.findById(m.getIdBankCredit())
                                           .flatMap(b -> {
                                               logger.info(String.valueOf(b.getAmount()));
                                               b.setAmount((b.getAmount() + movement.getAmount()));
                                               logger.info(String.valueOf(b.getAmount()));
                                               logger.info("en bank");
                                               return bankCreditService.save(b);
                                           }).subscribe();
                                   break;
                               case "P":
                                   bankCreditService.findById(m.getIdBankCredit())
                                           .flatMap(b -> {
                                               b.setAmount((b.getAmount() - movement.getAmount()));

                                               return bankCreditService.save(b);
                                           }).subscribe();
                                   break;
                           }
                           break;
                   }
                   return Mono.empty();
                });
}
}
