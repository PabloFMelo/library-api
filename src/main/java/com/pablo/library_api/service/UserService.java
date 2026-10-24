package com.pablo.library_api.service;

import com.pablo.library_api.dto.RegisterRequest;
import com.pablo.library_api.dto.RegisterResponse;
import com.pablo.library_api.exception.RegraDeNegocioException;
import com.pablo.library_api.model.AppUser;
import com.pablo.library_api.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService (AppUserRepository appUserRepository, PasswordEncoder passwordEncoder){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
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


}
