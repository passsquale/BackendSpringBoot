package ru.roslyackov.springboot.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.roslyackov.springboot.auth.entity.User;
import ru.roslyackov.springboot.auth.exception.JwtCommonException;
import ru.roslyackov.springboot.auth.service.UserDetailsImpl;
import ru.roslyackov.springboot.auth.utils.CookieUtils;
import ru.roslyackov.springboot.auth.utils.JwtUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";

    private JwtUtils jwtUtils;
    private CookieUtils cookieUtils;

    private List<String> permitURL = Arrays.asList(
            "register",
            "login",
            "activate-account",
            "resend-activate-email",
            "send-reset-password-email",
            "test-no-auth",
            "index"
    );
    @Autowired
    public void setCookieUtils(CookieUtils cookieUtils) {
        this.cookieUtils = cookieUtils;
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    // этот метод вызывается автоматически при каждом входящем запросе
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean isRequestToPublicAPI = permitURL.stream().anyMatch(s -> request.getRequestURI().toLowerCase().contains(s));


        if (!isRequestToPublicAPI) {

            String jwt = null;

            if (request.getRequestURI().contains("update-password")) {
                jwt = getJwtFromHeader(request);
            } else {
                jwt = cookieUtils.getCookieAccessToken(request);
            }

            if (jwt != null) {
                if (jwtUtils.validate(jwt)) {
                    User user = jwtUtils.getUser(jwt);
                    UserDetailsImpl userDetails = new UserDetailsImpl(user);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {

                    throw new JwtCommonException("jwt validate exception");
                }

            } else {
                throw new AuthenticationCredentialsNotFoundException("token not found");
            }
        }
        filterChain.doFilter(request, response); // продолжить выполнение запроса (запрос отправится дальше в контроллер)
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
            return headerAuth.substring(7); // вырезаем префикс, чтобы получить чистое значение jwt
        }

        return null; // jwt не найден
    }
}

