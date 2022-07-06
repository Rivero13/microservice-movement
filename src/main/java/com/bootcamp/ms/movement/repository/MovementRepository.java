package com.bootcamp.ms.movement.repository;

import com.bootcamp.ms.movement.entity.Movement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovementRepository extends ReactiveMongoRepository<Movement, String> {

}
