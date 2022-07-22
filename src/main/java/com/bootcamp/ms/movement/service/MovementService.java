package com.bootcamp.ms.movement.service;

import com.bootcamp.ms.movement.entity.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Optional;

public interface MovementService {

    public Flux<Movement> findAll();

    public Flux<Movement> findByBankAccount(String id);

    public Flux<Movement> findLastTenByClient(String id);

    public Mono<Movement> findById(String id);

    public Mono<Movement> save(Movement movement);

    public Mono<Void> delete(Movement movement);

    Optional<Movement> findByIdOpt(String id);

}
