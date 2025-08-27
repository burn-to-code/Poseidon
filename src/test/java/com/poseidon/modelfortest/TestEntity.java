package com.poseidon.modelfortest;

import com.poseidon.domain.BaseEntity;
import lombok.Getter;

public class TestEntity implements BaseEntity<TestEntity> {

    private Integer id;
    @Getter
    private String name;

    public TestEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TestEntity() {
    }

    @Override
    public Integer getId() { return id; }

    @Override
    public void update(TestEntity model) {
        this.name = model.name;
    }

}
