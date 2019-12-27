package logic.controllers;

import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import logic.UserRepository;
import org.springframework.http.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import logic.PasswordManager;
import javax.mail.internet.*;
import java.util.Map;

@Controller
@RequestMapping(path = "user")
public class UserController {

    //TODO: валидация запросов
    private final UserRepository userRepo;
    private final JavaMailSender postman;
    /*private final AuthenticationManager authManager;*/

    @Autowired public UserController(UserRepository userRepo, JavaMailSender postman/*, AuthenticationManager authManager*/) {
        this.userRepo = userRepo;
        this.postman = postman;
        /*this.authManager = authManager;*/
    }

    @PostMapping
    private ResponseEntity<String> login(@RequestBody Map<String, String> data) {
        User verifiableUser = userRepo.getByEmailAndPassword(PasswordManager.getHash(data.get("email"), "MD5"),
                PasswordManager.getHash(data.get("password"), "SHA1"));
        if (verifiableUser != null) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>("Указанного сочетания почты и пароля не существует", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    private ResponseEntity<String> register(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");
        try {
            new InternetAddress(email).validate();
            if (!userRepo.existsByEmail(PasswordManager.getHash(email, "MD5"))) {
                User newbie = new User(PasswordManager.getHash(email, "MD5"), PasswordManager.getHash(data.get("password"), "SHA1"));
                userRepo.save(newbie);
                try {
                    SimpleMailMessage msg = new SimpleMailMessage();
                    msg.setTo(email);
                    msg.setSubject("Успешная регистрация");
                    msg.setText("Вы зарегистрировались на сайте web4. Ваш пароль: " + password);
                    postman.send(msg);
                } catch (MailSendException e) {
                    System.out.println("Не удалось отправить email; время попытки истекло");
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>("Почта уже занята", HttpStatus.BAD_REQUEST);
        } catch (AddressException e) {
            return new ResponseEntity<>("Укажите почту в формате имя@доменное.имя", HttpStatus.BAD_REQUEST);
        }
    }
}