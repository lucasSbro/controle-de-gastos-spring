package com.controle.auth.auth;

import com.controle.Exceptions.LicencaExpiradaException;
import com.controle.auth.config.JwtService;
import com.controle.auth.user.Role;
import com.controle.auth.user.Usuario;
import com.controle.auth.user.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Usuario.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .dataExpiracaoLicenca(LocalDateTime.now().plusDays(7))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var usuario = userRepository.findByEmail(request.getEmail()).orElseThrow();

        if (usuario.getDataExpiracaoLicenca() != null && !usuario.getDataExpiracaoLicenca().isAfter(LocalDateTime.now())) {
            throw new LicencaExpiradaException("Sua licença está expirada. Por favor, efetue o pagamento para continuar utilizando o serviço.");
        }
        var jwtToken = jwtService.generateToken(usuario);
        return AuthenticationResponse.builder()
                .email(usuario.getEmail())
                .firstName(usuario.getFirstName())
                .lastName(usuario.getLastName())
                .token(jwtToken)
                .build();
    }

}
