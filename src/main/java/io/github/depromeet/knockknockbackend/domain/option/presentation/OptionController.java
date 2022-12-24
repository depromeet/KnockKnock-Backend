package io.github.depromeet.knockknockbackend.domain.option.presentation;


import io.github.depromeet.knockknockbackend.domain.option.presentation.dto.response.QueryOptionsResponse;
import io.github.depromeet.knockknockbackend.domain.option.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알림 설정 관련 컨트롤러", description = "")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@RequestMapping("/api/v1/options")
@RestController
public class OptionController {

    private final OptionService optionService;

    @GetMapping
    public QueryOptionsResponse queryOptions() {
        return optionService.queryOptions();
    }

    @Operation(summary = "새로운 푸시알림 도착 설정 Api입니다. - 마이페이지")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newOptionOn() {
        optionService.changeNewOption(true);
    }

    @Operation(summary = "새로운 푸시알림 도착 설정해제 Api입니다. - 마이페이지")
    @DeleteMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newOptionOff() {
        optionService.changeNewOption(false);
    }

    @Operation(summary = "리액션 알림 설정 Api입니다 - 마이페이지")
    @PostMapping("/reaction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactionOptionOn() {
        optionService.changeReactionOption(true);
    }

    @Operation(summary = "리액션 알림 설정해제 가져오는 Api입니다 - 마이페이지")
    @DeleteMapping("/reaction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactionOptionOff() {
        optionService.changeReactionOption(false);
    }

    @Operation(summary = "야간 푸시알림 설정 Api입니다 - 마이페이지")
    @PostMapping("/night")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void nightOptionOn() {
        optionService.changeNightOption(true);
    }

    @Operation(summary = "야간 푸시알림 설정해제 Api입니다 - 마이페이지")
    @DeleteMapping("/night")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void nightOptionOff() {
        optionService.changeNightOption(false);
    }
}
