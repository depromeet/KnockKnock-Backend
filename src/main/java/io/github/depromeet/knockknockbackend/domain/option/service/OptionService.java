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
        Option option = queryOption();

        option.setNewOption(value);

        optionRepository.save(option);
    }

    public void changeReactionOption(boolean value) {
        Option option = queryOption();

        option.setReactionOption(value);

        optionRepository.save(option);
    }

    public void changeNightOption(boolean value) {
        Option option = queryOption();

        option.setNightOption(value);

        optionRepository.save(option);
    }

    private Option queryOption() {
        return optionRepository
                .findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> OptionNotFoundException.EXCEPTION);
    }
}
