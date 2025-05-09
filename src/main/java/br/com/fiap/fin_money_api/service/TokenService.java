package br.com.fiap.fin_money_api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.fin_money_api.model.Token;
import br.com.fiap.fin_money_api.model.User;

@Service
public class TokenService {

    private Instant expiresAt = LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.ofHours(-3));
    private Algorithm algorithm = Algorithm.HMAC256("secret");

    public Token createToken(User user){
        var jwt = JWT.create()
            .withSubject(user.getId().toString())
            .withClaim("email", user.getEmail())
            .withExpiresAt(expiresAt)
            .sign(algorithm);

        return new Token(jwt, user.getEmail());
    }

    public User getUserFromToken(String token){
        var verifiedToken = JWT.require(algorithm).build().verify(token);

        return User.builder()
                .id(Long.valueOf( verifiedToken.getSubject() ))
                .email(verifiedToken.getClaim("email").toString())
                .build();
    }
    
}
