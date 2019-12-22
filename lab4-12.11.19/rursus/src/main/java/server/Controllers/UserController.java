package server.Controllers;
import entities.User;
import entities.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.PasswordManager;

import javax.mail.internet.AddressException;
import java.util.Map;

@Controller
@RequestMapping(path = "user")
public class UserController {

    private UserRepository userRepo;

    @PostMapping
    private ResponseEntity login(@RequestBody Map<String, String> data) {
        User verifiableUser = userRepo.findByEmailAndPassword(data.get("email"), PasswordManager.getHash(data.get("password"), "SHA1"));
        if (verifiableUser != null) return new ResponseEntity(HttpStatus.ACCEPTED);
        else return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    private ResponseEntity register(@RequestBody Map<String, String> data) {
        if (!userRepo.existsByEmail(data.get("email"))) {
            try {
                User newbie = new User(data.get("email"), data.get("password"));
                userRepo.save(newbie);
                //TODO: отправка email
                return new ResponseEntity(HttpStatus.OK);
            } catch (AddressException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
