package co.kr.jurumarble.user.controller;

import co.kr.jurumarble.user.dto.AddUserInfo;
import co.kr.jurumarble.user.dto.LoginToken;
import co.kr.jurumarble.user.dto.request.AddCategoryRequest;
import co.kr.jurumarble.user.dto.request.AddInfoRequest;
import co.kr.jurumarble.user.dto.request.KakaoLoginRequest;
import co.kr.jurumarble.user.dto.response.TokenResponse;
import co.kr.jurumarble.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup/kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginToken loginToken = userService.signupByThirdParty(kakaoLoginRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(loginToken));
    }

    @PatchMapping("/additional-info")
    public ResponseEntity<HttpStatus> addUserInfo(@RequestAttribute Long userId, @RequestBody AddInfoRequest addInfoRequest) {
        userService.addUserInfo(userId, addInfoRequest.toAddUserInfo());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/additional-category")
    public ResponseEntity<HttpStatus> addUserCategory(@RequestAttribute Long userId, @RequestBody AddCategoryRequest addCategoryRequest) {
        userService.addUserCategory(userId, addCategoryRequest.toAddUserCategory());
        return ResponseEntity.ok().build();
    }
}
