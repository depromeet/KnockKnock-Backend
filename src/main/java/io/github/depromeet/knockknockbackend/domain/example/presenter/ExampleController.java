package io.github.depromeet.knockknockbackend.domain.example.presenter;

import io.github.depromeet.knockknockbackend.domain.example.presenter.dto.request.ExampleRequest;
import io.github.depromeet.knockknockbackend.domain.example.presenter.dto.response.ExampleResponse;
import io.github.depromeet.knockknockbackend.domain.example.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/")
    public ExampleResponse getExampleValue(@RequestBody ExampleRequest request) {
        return new ExampleResponse(exampleService.getExample(request));
    }

}
