package com.bootcamp.ms.movement.controller;

import com.bootcamp.ms.commons.entity.BankAccount;
import com.bootcamp.ms.movement.entity.Movement;
import com.bootcamp.ms.movement.service.BankAccountService;
import com.bootcamp.ms.movement.service.BankCreditService;
import com.bootcamp.ms.movement.service.MovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BankCreditService bankCreditService;

    private final Logger logger = LoggerFactory.getLogger(MovementController.class);

    @GetMapping("/all")
    public Flux<Movement> getAll(){
        return movementService.findAll();
    }

    @GetMapping("/find/{bankAccount}")
    public Flux<Movement> getByIdBankAccount(@PathVariable String bankAccount){
        return movementService.findByBankAccount(bankAccount);
    }

    @GetMapping("/findBy/{id}")
    public Mono<Movement> findBy(@PathVariable String id){
        return movementService.findById(id);
    }

    @PostMapping(value = "/create")
    public Mono<Movement> create(@RequestBody Movement movement){

        movement.setDate(new Date());

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
                                case "T":
                                    bankAccountService.findById(m.getIdBankAccount())
                                            .flatMap(b -> {
                                                Double currentAmount = b.getAmount();
                                                Integer currentMovement = b.getMaxMovement();

                                                if(currentAmount > movement.getAmount()) {
                                                    currentAmount -= movement.getAmount();
                                                    b.setAmount(currentAmount);

                                                    if (currentMovement > 0) {
                                                        currentMovement -= 1;
                                                        b.setMaxMovement(currentMovement);
                                                    }

                                                    bankAccountService.findById(m.getIdBankAccountDestination())
                                                            .flatMap(b1 -> {
                                                                Double currentAmountDestination = b1.getAmount() + movement.getAmount();
                                                                b1.setAmount(currentAmountDestination);
                                                                return bankAccountService.save(b1);
                                                            }).subscribe();
                                                }

                                                return bankAccountService.save(b);
                                            }).subscribe();
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
                                                return bankCreditService.save2(b);
                                            }).subscribe();
                                    break;
                                case "P":
                                    bankCreditService.findById(m.getIdBankCredit())
                                            .flatMap(b -> {
                                                b.setAmount((b.getAmount() - movement.getAmount()));

                                                return bankCreditService.save2(b);
                                            }).subscribe();
                                    break;
                            }
                            break;

                    }
                    return Mono.just(movement);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return movementService.findById(id).flatMap(m -> movementService.delete(m));
    }

}
