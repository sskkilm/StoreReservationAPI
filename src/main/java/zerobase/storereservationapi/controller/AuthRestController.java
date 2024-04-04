package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.storereservationapi.dto.Login;
import zerobase.storereservationapi.dto.SignUp;
import zerobase.storereservationapi.dto.UserDto;
import zerobase.storereservationapi.jwt.TokenProvider;
import zerobase.storereservationapi.service.CustomUserDetailsService;

@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;

    /**
     * 일반 회원 가입 (고객)
     */
    @PostMapping("/signup/general")
    public ResponseEntity<UserDto> generalSignUp(
            @RequestBody @Valid SignUp request
    ) {
        return ResponseEntity.ok(customUserDetailsService.generalSignUp(request));
    }

    /**
     * 파트너 회원 가입 (관리자)
     */
    @PostMapping("/signup/partner")
    public ResponseEntity<UserDto> partnerSignUp(
            @RequestBody @Valid SignUp request
    ) {
        return ResponseEntity.ok(customUserDetailsService.partnerSignUp(request));
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid Login request
    ) {
        UserDto userDto = customUserDetailsService.authenticate(request);
        String token = tokenProvider.generateToken(userDto.getUsername(), userDto.getRole());

        return ResponseEntity.ok(token);
    }
}
