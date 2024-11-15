package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.author.request.AuthorCreationRequest;
import com.example.library_management_backend.dto.author.request.AuthorUpdateRequest;
import com.example.library_management_backend.dto.author.response.AuthorResponse;
import com.example.library_management_backend.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorCreationRequest request);

    AuthorResponse toAuthorResponse(Author author);

    void updateAuthor(@MappingTarget Author author, AuthorUpdateRequest request);
}
