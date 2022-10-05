package br.edu.utfpr.pb.pw26s.server.security.social;

import br.edu.utfpr.pb.pw26s.server.model.AuthProvider;
import br.edu.utfpr.pb.pw26s.server.model.User;
import br.edu.utfpr.pb.pw26s.server.repository.UserRepository;
import br.edu.utfpr.pb.pw26s.server.security.AuthUserService;
import br.edu.utfpr.pb.pw26s.server.security.AuthenticationResponse;
import br.edu.utfpr.pb.pw26s.server.security.SecurityConstants;
import br.edu.utfpr.pb.pw26s.server.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final GoogleTokenVerifier googleTokenVerifier;
    private final AuthUserService authUserService;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(GoogleTokenVerifier googleTokenVerifier, AuthUserService authUserService,
                          UserService userService,
                          UserRepository userRepository) {
        this.googleTokenVerifier = googleTokenVerifier;
        this.authUserService = authUserService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    ResponseEntity<AuthenticationResponse> auth(HttpServletRequest request, HttpServletResponse response) {
        String idToken = request.getHeader("Auth-Id-Token");
        if (idToken != null) {
            final Payload payload;
            try {
                payload = googleTokenVerifier.verify(idToken.replace(SecurityConstants.TOKEN_PREFIX, ""));
                if (payload != null) {
                    String username = payload.getEmail();
                    User user = userRepository.findByUsername(username);
                    if (user == null) {
                        user = new User();
                        user.setUsername(payload.getEmail());
                        user.setDisplayName( (String) payload.get("name"));
                        user.setPassword("P4ssword");
                        user.setProvider(AuthProvider.google);
                        userService.save(user);
                    }

                    String token = JWT.create()
                            .withSubject(username)
                            .withExpiresAt(new Date(System.currentTimeMillis() +
                                    SecurityConstants.EXPIRATION_TIME))
                            .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

                    return  ResponseEntity.ok(new AuthenticationResponse(token));

                }
            } catch (Exception e) {
                // This is not a valid token, the application will send HTTP 401 as a response
            }
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
