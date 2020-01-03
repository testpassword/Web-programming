package logic.controllers;

import logic.models.User;
import logic.requests.UserDTO;
import logic.security.JWTUtil;
import logic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

/**
 * Обрабатывает запросы к url-у /user.
 * @author Артемий Кульбако
 * @version 1.2
 */
@RestController @RequestMapping(path = "user")
public class UserController {

    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired public UserController(AuthenticationManager authManager, JWTUtil jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    private ResponseEntity<String> login(@Valid @RequestBody UserDTO req) {
        String email = req.getEmail();
        String password = req.getPassword();
        User user = userService.findByEmail(email);
        if (user != null) {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = jwtUtil.generateToken(email, new ArrayList<String>(){{add("USER");}});
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>("Указанного сочетания почты и пароля не существует", HttpStatus.NO_CONTENT);
    }

    @PutMapping
    private ResponseEntity<String> register(@Valid @RequestBody UserDTO req) {
        String email = req.getEmail();
        String password = req.getPassword();
        User user = userService.findByEmail(email);
        if (user != null) return new ResponseEntity<>("Пользователь уже существует", HttpStatus.BAD_REQUEST);
        else {
            userService.register(email, password);
            return new ResponseEntity<>("Успешная регистрация", HttpStatus.CREATED);
        }
    }

    @DeleteMapping
    private ResponseEntity<String> remove(@Valid @RequestBody UserDTO req) {
        String email = req.getEmail();
        String password = req.getPassword();
        User user = userService.findByEmail(email);
        if (user != null) {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            userService.remove(user);
            return new ResponseEntity<>("Вы удалили свой аккаунт", HttpStatus.OK);
        } else return new ResponseEntity<>("Указанного сочетания почты и пароля не существует", HttpStatus.NO_CONTENT);
    }
}