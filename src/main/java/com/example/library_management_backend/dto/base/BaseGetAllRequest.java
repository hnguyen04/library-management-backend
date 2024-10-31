package com.example.library_management_backend.dto.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseGetAllRequest {
    protected Integer skipCount = 0;
    protected Integer maxResultCount = 10;
}
