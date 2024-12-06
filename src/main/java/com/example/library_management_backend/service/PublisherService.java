package com.example.library_management_backend.service;

import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.publisher.request.PublisherCreationRequest;
import com.example.library_management_backend.dto.publisher.request.PublisherGetAllRequest;
import com.example.library_management_backend.dto.publisher.request.PublisherUpdateRequest;
import com.example.library_management_backend.dto.publisher.response.PublisherResponse;
import com.example.library_management_backend.entity.Publisher;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.PublisherMapper;
import com.example.library_management_backend.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class PublisherService {
    private PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public PublisherResponse createPublisher(PublisherCreationRequest request) {
        Publisher publisher = publisherMapper.toPublisher(request);
        try {
            publisher = publisherRepository.save(publisher);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.PUBLISHER_EXISTED);
        }
        return publisherMapper.toPublisherResponse(publisher);
    }

    public BaseGetAllResponse<PublisherResponse> getAllPublishers(PublisherGetAllRequest request) {
        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String name = (request.getName() == null || request.getName().isEmpty()) ? null : request.getName();

        List<PublisherResponse> publisherResponseList = publisherRepository.findAllByFilters(name)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(publisherMapper::toPublisherResponse)
                .collect(Collectors.toList());

        return BaseGetAllResponse.<PublisherResponse>builder()
                .data(publisherResponseList)
                .totalRecords(publisherRepository.countByFilters(name))
                .build();
    }

    public PublisherResponse updatePublisher(PublisherUpdateRequest request) {
        Publisher publisher = publisherRepository.findById(String.valueOf(request.getId()))
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_EXISTED));
        publisherMapper.updatePublisher(publisher, request);
        publisher = publisherRepository.save(publisher);
        return publisherMapper.toPublisherResponse(publisher);
    }

    public void deletePublisher(int id) {
        if (!publisherRepository.existsById(String.valueOf(id))) {
            throw new AppException(ErrorCode.PUBLISHER_NOT_EXISTED);
        }
        publisherRepository.deleteById(String.valueOf(id));
    }

    public PublisherResponse getPublisher(int id) {
        Publisher publisher = publisherRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_EXISTED));
        return publisherMapper.toPublisherResponse(publisher);
    }
}