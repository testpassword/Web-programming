package logic.controllers;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import logic.UserRepository;
import org.springframework.http.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import logic.PasswordManager;
import javax.mail.internet.*;
import java.util.Map;

@Controller
@RequestMapping(path = "user")
public class UserController {

    //TODO: валидация запросов
    @Autowired private UserRepository userRepo;
    @Autowired private JavaMailSender postman;

    @PostMapping
    private ResponseEntity<String> login(@RequestBody Map<String, String> data) {
        User verifiableUser = userRepo.findByEmailAndPassword(PasswordManager.getHash(data.get("email"), "MD5"),
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
                User newbie = new User(email, password);
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