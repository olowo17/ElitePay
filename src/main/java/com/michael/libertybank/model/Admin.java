package com.michael.libertybank.model;
import jakarta.persistence.Entity;

import lombok.*;


@Entity
@Getter
@Setter
public class Admin extends Employee{


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
