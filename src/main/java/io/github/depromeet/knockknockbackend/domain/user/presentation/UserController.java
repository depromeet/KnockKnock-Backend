package io.github.depromeet.knockknockbackend.domain.user.presentation;

import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.request.ChangeNicknameRequest;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.request.ChangeProfileRequest;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponse;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.UserProfileResponse;
import io.github.depromeet.knockknockbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/nickname/{nickname}")
    public QueryUserByNicknameResponse queryUserByNickname(@PathVariable("nickname") String nickname) {
        return userService.queryUserByNicknameResponse(nickname);
    }

    @PutMapping("/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeNickname(@RequestBody @Valid ChangeNicknameRequest request) {
        userService.changeNickname(request.getNickname());
    }

    @PutMapping("/profile")
    public UserProfileResponse changeProfile(@RequestBody @Valid ChangeProfileRequest changeProfileRequest) {
       return userService.changeProfile(changeProfileRequest);
    }

}
