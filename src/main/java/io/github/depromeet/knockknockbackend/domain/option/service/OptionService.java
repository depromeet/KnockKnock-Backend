package io.github.depromeet.knockknockbackend.domain.option.service;

import io.github.depromeet.knockknockbackend.domain.option.domain.Option;
import io.github.depromeet.knockknockbackend.domain.option.domain.repository.OptionRepository;
import io.github.depromeet.knockknockbackend.domain.option.exception.OptionNotFoundException;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public void changeNewOption(boolean value) {
        Option option = optionRepository.findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> OptionNotFoundException.EXCEPTION);

        option.setNewOption(value);

        optionRepository.save(option);
    }

}
