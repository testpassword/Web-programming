package logic.controllers;

import logic.models.User;
import logic.security.JWTUtil;
import logic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * Обрабатывает запросы к url-у /user.
 */
@RestController @RequestMapping(path = "user")
public class UserController {

    //TODO: валидация запросов
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired public UserController(AuthenticationManager authManager, JWTUtil jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    private ResponseEntity<String> login(@RequestBody Map<String, String> req) {
            String email = req.get("email");
            String password = req.get("password");
            User user = userService.findByEmail(email);
            if (user != null) {
                authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                String token = jwtUtil.generateToken(email, new ArrayList<String>(){{add("USER");}});
                return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>("Указанного сочетания почты и пароля не существует", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    private ResponseEntity<String> register(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");
        try {
            new InternetAddress(email).validate();
            User user = userService.findByEmail(email);
            if (user != null) return new ResponseEntity<>("Пользователь уже существует", HttpStatus.BAD_REQUEST);
            else {
                userService.register(email, password);
                return new ResponseEntity<>("Успешная регистрация", HttpStatus.CREATED);
            }
        } catch (AddressException e) {
            return new ResponseEntity<>("Укажите почту в формате имя@доменное.имя", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    private ResponseEntity<String> remove(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");
        authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userService.findByEmail(email);
        if (user != null) {
            userService.remove(user);
            return new ResponseEntity<>("Вы удалили свой аккаунт", HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>("Указанного сочетания почты и пароля не существует", HttpStatus.UNAUTHORIZED);
    }
}