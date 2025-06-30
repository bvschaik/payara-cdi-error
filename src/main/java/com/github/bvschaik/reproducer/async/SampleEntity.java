package com.github.bvschaik.reproducer.async;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
@EntityListeners(SampleEntityListener.class)
public class SampleEntity {
    @Id
    @Column(name = "id")
    String id;

    @Column(name = "sample_name")
    String name;

    public SampleEntity() {
    }

    public SampleEntity(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
