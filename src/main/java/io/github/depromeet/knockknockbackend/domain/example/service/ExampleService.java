package io.github.depromeet.knockknockbackend.domain.example.service;

import io.github.depromeet.knockknockbackend.domain.example.domain.Example;
import io.github.depromeet.knockknockbackend.domain.example.domain.repository.ExampleRepository;
import io.github.depromeet.knockknockbackend.domain.example.exception.ExampleNotFoundException;
import io.github.depromeet.knockknockbackend.domain.example.presenter.dto.request.ExampleRequest;
import io.github.depromeet.knockknockbackend.domain.example.presenter.dto.response.ExampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExampleService {

    private final ExampleRepository exampleRepository;

    public String getExample(ExampleRequest request) {
        exampleRepository.save(
                Example.builder()
                        .exampleValue(request.getExampleValue())
                        .build()
        );

        return request.getExampleValue();
    }

    public void exceptionExample() {
        throw ExampleNotFoundException.EXCEPTION;
    }

}
