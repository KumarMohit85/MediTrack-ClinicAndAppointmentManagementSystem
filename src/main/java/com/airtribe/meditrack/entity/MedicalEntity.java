package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public abstract class MedicalEntity {
    private String id;
    private LocalDateTime createdAt;

    public MedicalEntity(String id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

}
