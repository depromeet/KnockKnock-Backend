package io.github.depromeet.knockknockbackend.domain.user.presentation;


import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.request.ChangeNicknameRequest;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.request.ChangeProfileRequest;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponse;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.UserProfileResponse;
import io.github.depromeet.knockknockbackend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 컨트롤러", description = "")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "access-token")
@RestController
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 닉네임으로 검색하는 Api입니다. - 친구목록")
    @GetMapping("/nickname/{nickname}")
    public QueryUserByNicknameResponse queryUserByNickname(
            @PathVariable("nickname") String nickname) {
        return userService.queryUserByNicknameResponse(nickname);
    }

    @Operation(summary = "닉네임 변경 Api입니다. - 마이페이지")
    @PutMapping("/nickname")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeNickname(@RequestBody @Valid ChangeNicknameRequest request) {
        userService.changeNickname(request.getNickname());
    }

    @PutMapping("/profile")
    public UserProfileResponse changeProfile(
            @RequestBody @Valid ChangeProfileRequest changeProfileRequest) {
        return userService.changeProfile(changeProfileRequest);
    }

    @GetMapping("/profile")
    public UserProfileResponse getProfile() {
        return userService.getProfile();
    }
}
