package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.publisher.request.PublisherCreationRequest;
import com.example.library_management_backend.dto.publisher.request.PublisherUpdateRequest;
import com.example.library_management_backend.dto.publisher.response.PublisherResponse;
import com.example.library_management_backend.entity.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    Publisher toPublisher(PublisherCreationRequest request);

    PublisherResponse toPublisherResponse(Publisher publisher);

    void updatePublisher(@MappingTarget Publisher publisher, PublisherUpdateRequest request);
}
