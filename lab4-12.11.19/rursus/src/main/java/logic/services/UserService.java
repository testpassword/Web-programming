package logic.services;

import logic.models.User;
import logic.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //TODO: валидация запросов
    private final UserRepository userRepo;
    private final JavaMailSender postman;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepo, JavaMailSender postman, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.postman = postman;
        this.encoder = encoder;
    }

    public User findByEmail(String email) {
        System.out.println("Получаем пользователя " + email);
        return userRepo.getByEmail(email);
    }

    public void register(String email, String password) {
        System.out.println("Создаём пользователя " + email);
        User newbie = new User(email, encoder.encode(password));
        userRepo.save(newbie);
        try {
            System.out.println("Отправляем почту");
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Успешная регистрация");
            msg.setText("Вы зарегистрировались на сайте web4. Ваш пароль: " + password);
            postman.send(msg);
        } catch (MailSendException e) {
            System.out.println("Не удалось отправить email; время попытки истекло");
        }
    }

    public void delete(String email) {
        System.out.println("Удаляем пользователя " + email);
        User victim = userRepo.getByEmail(email);
        long result = userRepo.removeByEmailEqualsAndPasswordEquals(victim);
    }
}
