package com.example.library_management_backend.controller;

import com.example.library_management_backend.dto.base.response.ApiResponse;
import com.example.library_management_backend.dto.author.request.AuthorCreationRequest;
import com.example.library_management_backend.dto.author.request.AuthorGetAllRequest;
import com.example.library_management_backend.dto.author.request.AuthorUpdateRequest;
import com.example.library_management_backend.dto.author.response.AuthorResponse;
import com.example.library_management_backend.service.AuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorController {
    AuthorService authorService;

    @PostMapping("/Create")
    ApiResponse<AuthorResponse> createAuthor(@RequestBody AuthorCreationRequest request) {
        return ApiResponse.<AuthorResponse>builder().
                result(authorService.createAuthor(request)).
                build();
    }

    @GetMapping("/GetAll")
    ApiResponse<List<AuthorResponse>> getAllAuthors(
            @RequestParam(value = "skipCount", defaultValue = "0") int skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") int maxResultCount,
            @RequestParam(value = "name", required = false) String name) {

        AuthorGetAllRequest request = new AuthorGetAllRequest();
        request.setSkipCount(skipCount);
        request.setMaxResultCount(maxResultCount);
        request.setName(name);
        return ApiResponse.<List<AuthorResponse>>builder()
                .result(authorService.getAllAuthors(request))
                .build();
    }

    @GetMapping("/GetById")
    ApiResponse<AuthorResponse> getAuthorById(@RequestParam int id) {
        return ApiResponse.<AuthorResponse>builder().
                result(authorService.getAuthor(id)).
                build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteAuthor(@RequestParam int id) {
        authorService.deleteAuthor(id);
        return ApiResponse.<String>builder().
                result("Author deleted successfully").
                build();
    }

    @PutMapping("/Update")
    ApiResponse<AuthorResponse> updateAuthor(@RequestParam int id, @RequestBody AuthorUpdateRequest request) {
        return ApiResponse.<AuthorResponse>builder().
                result(authorService.updateAuthor(id, request)).
                build();
    }
}