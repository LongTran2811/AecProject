package com.example.aecbackend.payload;

import java.util.List;

public class SoftDeleteRequest {
    private List<Integer> ids;
    private String deletedBy;

    public List<Integer> getIds() {
        return ids;
    }
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
    public String getDeletedBy() {
        return deletedBy;
    }
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
} 