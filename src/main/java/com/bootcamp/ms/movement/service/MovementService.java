package com.bootcamp.ms.movement.service;

import com.bootcamp.ms.movement.entity.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {

    public Flux<Movement> findAll();

    public Flux<Movement> findByBankAccount(String bankAccount);

    public Mono<Movement> findById(String id);

    public Mono<Movement> save(Movement movement);

    public Mono<Void> deleteById(Movement movement);

}
