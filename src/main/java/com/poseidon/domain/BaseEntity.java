package com.poseidon.domain;

public interface BaseEntity<MODEL> {

    Integer getId();

    void update(MODEL model);

}
