package br.com.microsservices.statelessauthapi.core.service;

import br.com.microsservices.statelessauthapi.core.dto.AuthRequest;
import br.com.microsservices.statelessauthapi.core.dto.TokenDto;
import br.com.microsservices.statelessauthapi.core.repository.UserRepository;
import br.com.microsservices.statelessauthapi.infra.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public TokenDto login(AuthRequest authRequest) {
        var user = userRepository.findByUsername(authRequest.username())
                .orElseThrow(() -> new ValidationException("User not found."));

        var accessToken = jwtService.createToken(user);
        validatePassword(authRequest.password(), user.getPassword());

        return new TokenDto(accessToken);
    }

    public TokenDto validateToken(String accessToken) {
        validateExistingToken(accessToken);
        jwtService.validateAcessToken(accessToken);
        return new TokenDto(accessToken);
    }

    private void validateExistingToken(String accessToken) {
        if (isEmpty(accessToken)) {
            throw new ValidationException("The acess token must be informed.");
        }
    }

    private void validatePassword(String rawPassword, String encondedPassword) {
        if (!passwordEncoder.matches(rawPassword, encondedPassword)) {
            throw new ValidationException("The password is incorrect!");
        }
    }
}
