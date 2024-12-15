package com.example.library_management_backend.controller;

import com.example.library_management_backend.dto.base.response.ApiResponse;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.fine.request.FineCreationRequest;
import com.example.library_management_backend.dto.fine.request.FineGetAllRequest;
import com.example.library_management_backend.dto.fine.request.FineUpdateRequest;
import com.example.library_management_backend.dto.fine.response.FineResponse;
import com.example.library_management_backend.service.FineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FineController {
    FineService fineService;

    @GetMapping("/GetAll")
    public ApiResponse<BaseGetAllResponse<FineResponse>> getAllFines(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "skipCount", defaultValue = "0") int skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") int maxResultCount) {

        FineGetAllRequest request = FineGetAllRequest.builder()
                .userId(userId)
                .skipCount(skipCount)
                .maxResultCount(maxResultCount)
                .build();

        return ApiResponse.<BaseGetAllResponse<FineResponse>>builder()
                .result(fineService.getAllFines(request))
                .build();
    }

    @PostMapping("/Create")
    public ApiResponse<FineResponse> createFine(@RequestBody FineCreationRequest request) {
        return ApiResponse.<FineResponse>builder()
                .result(fineService.createFine(request))
                .build();
    }

    @GetMapping("/GetById")
    public ApiResponse<FineResponse> getFineById(@RequestParam String id) {
        return ApiResponse.<FineResponse>builder()
                .result(fineService.getFineById(id))
                .build();
    }

    @PutMapping("/Update")
    public ApiResponse<FineResponse> updateFine(@RequestBody FineUpdateRequest request) {
        return ApiResponse.<FineResponse>builder()
                .result(fineService.updateFine(request))
                .build();
    }

    @DeleteMapping("/Delete")
    public ApiResponse<String> deleteFine(@RequestParam String id) {
        fineService.deleteFine(id);
        return ApiResponse.<String>builder()
                .result("Fine deleted successfully")
                .build();
    }
}