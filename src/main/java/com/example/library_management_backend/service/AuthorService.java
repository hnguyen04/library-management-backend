package com.example.library_management_backend.service;

import com.example.library_management_backend.dto.author.request.AuthorCreationRequest;
import com.example.library_management_backend.dto.author.request.AuthorGetAllRequest;
import com.example.library_management_backend.dto.author.request.AuthorUpdateRequest;
import com.example.library_management_backend.dto.author.response.AuthorResponse;
import com.example.library_management_backend.entity.Author;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.AuthorMapper;
import com.example.library_management_backend.repository.AuthorRepository;
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
public class AuthorService {
    private AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponse createAuthor(AuthorCreationRequest request) {
        Author author = authorMapper.toAuthor(request);
        try {
            author = authorRepository.save(author);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.AUTHOR_EXISTED);
        }
        return authorMapper.toAuthorResponse(author);
    }

    public List<AuthorResponse> getAllAuthors(AuthorGetAllRequest request) {
        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String name = (request.getName() == null || request.getName().isEmpty()) ? null : request.getName();

        return authorRepository.findAllByFilters(name)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(authorMapper::toAuthorResponse)
                .collect(Collectors.toList());
    }

    public AuthorResponse updateAuthor(String id, AuthorUpdateRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_EXISTED));
        authorMapper.updateAuthor(author, request);
        author = authorRepository.save(author);
        return authorMapper.toAuthorResponse(author);
    }

    public void deleteAuthor(String id) {
        if (!authorRepository.existsById(id)) {
            throw new AppException(ErrorCode.AUTHOR_NOT_EXISTED);
        }
        authorRepository.deleteById(id);
    }

    public AuthorResponse getAuthor(String id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_EXISTED));
        return authorMapper.toAuthorResponse(author);
    }
}