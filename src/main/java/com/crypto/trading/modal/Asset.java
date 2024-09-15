package com.crypto.trading.modal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double quantity;
    private double buyPrice;

    @ManyToOne
    private Coin coin;

    @ManyToOne
    private User user;


}
