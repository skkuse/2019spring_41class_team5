package edu.skku.dealistic.controller;

import edu.skku.dealistic.model.Authority;
import edu.skku.dealistic.model.User;
import edu.skku.dealistic.payload.ApiResponse;
import edu.skku.dealistic.payload.LoginRequest;
import edu.skku.dealistic.payload.SignupRequest;
import edu.skku.dealistic.security.UserAuthService;
import edu.skku.dealistic.service.UserService;
import edu.skku.dealistic.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final UserService userService;

    private final UserAuthService userAuthService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sign-in")
    public Map signIn(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getId(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);
        User user = userService.getUser(request.getId());

        return Map.of(
                "token", token,
                "user", user);
    }

    @PostMapping("/sign-up")
    public ApiResponse signUp(@RequestBody SignupRequest request) {
        if (userAuthService.isUserIdExist(request.getId())) {
            return ApiResponse.builder().message("ID is already taken!").build();
        }

        User user = User.builder()
                .id(request.getId())
                .name(request.getName())
                .organization(request.getOrganization())
                .authority(Authority.USER)
                .build();

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userAuthService.signUp(user);

        return ApiResponse.SUCCESS;
    }
}
