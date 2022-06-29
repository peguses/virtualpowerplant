package com.virtualpowerplant.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

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

    private UUID uuid;
    private Integer postcode;
    private Integer capacity;

    public Battery(String name, Integer postcode, Integer capacity) {
        this.name = name;
        this.postcode = postcode;
        this.capacity = capacity;
        //if security is primary concern, UUID generation should first
        // create a hash code, extending that to 128 bits. Convert that byte array to a UUID format, and you're done.
        this.uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Battery battery = (Battery) o;
        return Objects.equals(name, battery.name) && Objects.equals(postcode, battery.postcode)
                && Objects.equals(capacity, battery.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, postcode, capacity);
    }
}
