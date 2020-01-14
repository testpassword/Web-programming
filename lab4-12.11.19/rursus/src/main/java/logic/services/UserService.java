package logic.services;

import logic.models.User;
import logic.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервисный слой, управляющий сущностью {@code User}.
 * @see User
 * @author Артемий Кульбако
 * @version 1.2
 */
@Service @Slf4j
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

    @Transactional
    public User findByEmail(String email) {
        log.info("Получаем пользователя " + email);
        return userRepo.getByEmail(email);
    }

    @Transactional
    public void register(String email, String password) {
        log.info("Создаём пользователя " + email);
        User newbie = new User(email, encoder.encode(password));
        userRepo.save(newbie);
        try {
            log.info("Отправляем почту");
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Успешная регистрация");
            msg.setText("Вы зарегистрировались на сайте web4. Ваш пароль: " + password);
            postman.send(msg);
        } catch (MailSendException e) {
            log.error("Не удалось отправить email; время попытки истекло");
        }
    }

    @Transactional
    public void remove(User victim) {
        log.info("Удаляем пользователя " + victim.getEmail());
        userRepo.deleteByEmail(victim.getEmail());
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(victim.getEmail());
            msg.setSubject("Аккаунт удалён");
            msg.setText("Вы удалили аккаунт " + victim.getEmail() + " на сайте web4.");
            postman.send(msg);
        } catch (MailSendException e) {
            log.error("Не удалось отправить email; время попытки истекло");
        }
    }

    public void updateUser(User updatable) {
        log.info("Применяем изменения пользователя " + updatable.getEmail());
        userRepo.save(updatable);
    }
}