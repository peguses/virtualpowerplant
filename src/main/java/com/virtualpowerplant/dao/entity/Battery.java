package com.virtualpowerplant.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "batteries")
@NoArgsConstructor
public class Battery {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    private String name;
    private Integer postcode;
    private Integer capacity;

    public Battery(String name, Integer postcode, Integer capacity) {
        this.name = name;
        this.postcode = postcode;
        this.capacity = capacity;
    }
}
