package com.example.aecbackend.payload;

import java.util.List;

public class SoftDeleteRequest {
    private List<String> ids;
    private String deletedBy;

    public List<String> getIds() {
        return ids;
    }
    public void setIds(List<String> ids) {
        this.ids = ids;
    }
    public String getDeletedBy() {
        return deletedBy;
    }
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
} 