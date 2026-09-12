package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalEntity that = (MedicalEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
