package io.github.depromeet.knockknockbackend.domain.option.presentation;

import io.github.depromeet.knockknockbackend.domain.option.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/options")
@RestController
public class OptionController {

    private final OptionService optionService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newOptionOn() {
        optionService.changeNewOption(true);
    }

    @DeleteMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newOptionOff() {
        optionService.changeNewOption(false);
    }

}
