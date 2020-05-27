package com.nextcloudapps.blogapi.payload;

import java.io.Serializable;
import java.time.Instant;

public abstract class DateAuditPayload implements Serializable {

    private Instant createdAt;

    private Instant updatedAt;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
