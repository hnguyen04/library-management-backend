package com.example.library_management_backend.dto.base.request;

import lombok.*;

@Getter
@Setter
public abstract class BaseGetAllRequest {
    protected Integer skipCount = 0;
    protected Integer maxResultCount = 10;
}
