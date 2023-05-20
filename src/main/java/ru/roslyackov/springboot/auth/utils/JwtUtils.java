package ru.roslyackov.springboot.auth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.roslyackov.springboot.auth.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;



@Component
@Log
public class JwtUtils {

    public static final String CLAIM_USER_KEY = "user";

    @Value("${jwt.secret}")
    private String jwtSecret;


    @Value("${jwt.access_token-expiration}")
    private int accessTokenExpiration;

    @Value("${jwt.reset-pass-expiration}")
    private int resetPassTokenExpiration;

    // генерация JWT для доступа к данным
    public String createAccessToken(User user) { // в user будут заполнены те поля, которые нужны аутентификации пользователя и работы в системе
        return createToken(user, accessTokenExpiration);
    }


    // генерация JWT для сброса пароля
    public String createEmailResetToken(User user) { // в user будут заполнены только те поля, которые нужны для сброса пароля
        return createToken(user, resetPassTokenExpiration);
    }


    // создает JWT с нужным сроком действия
    private String createToken(User user, int duration){
        Date currentDate = new Date(); // для отсчета времени от текущего момента - для задания expiration

        // пароль зануляем до формирования jwt
        user.setPassword(null);


        Map claims = new HashMap<String, Object>();
        claims.put(CLAIM_USER_KEY, user);
        claims.put(Claims.SUBJECT, user.getId());
        return Jwts.builder()


                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + duration)) // срок действия access_token

                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // проверить целостность данных (не истек ли срок jwt и пр.)
    public boolean validate(String jwt) {
        try {
            Jwts.
                    parser().
                    setSigningKey(jwtSecret).
                    parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException e) {
            log.log(Level.SEVERE, "Invalid JWT token: ", jwt);
        } catch (ExpiredJwtException e) {
            log.log(Level.SEVERE, "JWT token is expired: ", jwt);
        } catch (UnsupportedJwtException e) {
            log.log(Level.SEVERE, "JWT token is unsupported: ", jwt);
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "JWT claims string is empty: ", jwt);
        }

        return false;

    }

    // получение поля subject из JWT
    public User getUser(String jwt) {

        Map map = (Map)Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().get(CLAIM_USER_KEY); // CLAIM_USER_KEY здесь - это поле из токена

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(map, User.class);

        return user;
    }

}
