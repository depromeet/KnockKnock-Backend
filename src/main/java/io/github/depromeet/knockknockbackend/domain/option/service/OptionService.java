package io.github.depromeet.knockknockbackend.domain.option.service;


import io.github.depromeet.knockknockbackend.domain.credential.service.UserOptionService;
import io.github.depromeet.knockknockbackend.domain.option.domain.Option;
import io.github.depromeet.knockknockbackend.domain.option.domain.repository.OptionRepository;
import io.github.depromeet.knockknockbackend.domain.option.exception.OptionNotFoundException;
import io.github.depromeet.knockknockbackend.domain.option.presentation.dto.response.QueryOptionsResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OptionService implements UserOptionService {

    private final OptionRepository optionRepository;

    public QueryOptionsResponse queryOptions() {
        Option option = queryOption();

        return new QueryOptionsResponse(
                option.isNewOption(), option.isReactionOption(), option.isNightOption());
    }

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

    public void initializeOption(User user) {
        optionRepository.save(Option.builder().user(user).build());
    }

    private Option queryOption() {
        return optionRepository
                .findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> OptionNotFoundException.EXCEPTION);
    }
}
