package io.github.depromeet.knockknockbackend.domain.user.presentation;

import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponse;
import io.github.depromeet.knockknockbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{nickname}")
    public QueryUserByNicknameResponse queryUserByNickname(@PathVariable("nickname") String nickname) {
        return userService.queryUserByNicknameResponse(nickname);
    }

}
