package ru.otus.tableineritance.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLeaf")
public class C extends A {
    private String fieldC;

    public C(long id, String fieldA, String fieldC) {
        super(id, fieldA);
        this.fieldC = fieldC;
    }

    @Override
    public String toString() {
        return "C{" + "id=" + id + ", fieldA='" + fieldA + '\'' + ", fieldC='" + fieldC + '\'' + '}';
    }
}
