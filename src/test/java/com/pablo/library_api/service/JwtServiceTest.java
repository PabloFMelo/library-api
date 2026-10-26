package com.pablo.library_api.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtServiceTest {
    private static final String SECRET = "um-segredo-de-teste-com-mais-de-32-caracteres-ok";

    @Test
    void deveGerarTokenEExtrairOMesmoUsername() {
        JwtService jwtService = new JwtService(SECRET, 3600000L);

        String token = jwtService.gerarToken("pablo");

        assertEquals("username", jwtService.extrairUsername(token));
    }

    @Test
    void deveRejeitarTokenExpirado() {
        JwtService jwtService = new JwtService(SECRET, -1000L);
        String token = jwtService.gerarToken("pablo");

        assertThrows(ExpiredJwtException.class, () -> jwtService.extrairUsername(token));
    }

    @Test
    void deveRejeitarTokenAssinadoComOutraChave() {
        JwtService emissor = new JwtService("outra-chave-totalmente-diferente-com-32-chars-ok", 3600000L);
        JwtService validador = new JwtService(SECRET, 3600000L);
        String token = emissor.gerarToken("pablo");

        assertThrows(JwtException.class, () -> validador.extrairUsername(token));
    }
}
