package com.pablo.library_api.service;

import com.pablo.library_api.dto.LoginRequest;
import com.pablo.library_api.dto.LoginResponse;
import com.pablo.library_api.dto.RegisterRequest;
import com.pablo.library_api.dto.RegisterResponse;
import com.pablo.library_api.exception.CredenciaisInvalidasException;
import com.pablo.library_api.exception.RegraDeNegocioException;
import com.pablo.library_api.model.AppUser;
import com.pablo.library_api.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService (AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }
    public RegisterResponse register(RegisterRequest request) {
        String username = request.username().trim();

        if (appUserRepository.existsByUsername(username)) {
            throw new RegraDeNegocioException("Nome de usuário já existe");
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(request.password()));

        AppUser saved = appUserRepository.save(user);
        return new RegisterResponse(saved.getId(), saved.getUsername());
    }
    public LoginResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByUsername(request.username().trim())
                .orElseThrow(() -> new CredenciaisInvalidasException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CredenciaisInvalidasException("Credenciais inválidas");
        }

        String token = jwtService.gerarToken(user.getUsername());
        return new LoginResponse(token);
    }
}
