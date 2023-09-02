package br.com.microsservices.statelessauthapi.core.controller;

import br.com.microsservices.statelessauthapi.core.dto.AuthRequest;
import br.com.microsservices.statelessauthapi.core.dto.TokenDto;
import br.com.microsservices.statelessauthapi.core.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public TokenDto login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("token/validate")
    public TokenDto tokenValidate(@RequestHeader String accessToken) {
        return authService.validateToken(accessToken);
    }
}
