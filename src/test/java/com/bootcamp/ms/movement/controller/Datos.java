package com.bootcamp.ms.movement.controller;

import com.bootcamp.ms.movement.entity.Movement;
import reactor.core.publisher.Flux;
import java.util.Date;

public class Datos {


    public final static Flux<Movement> MOVEMENT = Flux.just(
            new Movement("62dae2f624f70813e1068adf", "Crédito", "C",new Date(),500,null,"62dae2f624f70813e1068adf","","",""),
            new Movement("62dae2f624f74813e1068adf", "Débito", "R",new Date(),100,"62dae2f624f79113e1068adf",null,"","","")
    );
}
