package com.bootcamp.ms.movement.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "movement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {

    @Id
    private String id;
    private String type;
    private String description;
    private Date date;
    private double amount;
    private String idBankAccount;
    private String idBankCredit;

}
