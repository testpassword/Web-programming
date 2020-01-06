package logic.services;

import logic.models.User;
import logic.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация интерфейса UserDetailsService для манипуляциями {@code User} с точки зрения Spring Security.
 * @see User
 * @author Артемий Кульбако
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByEmail(s);
        if (user == null) throw new UsernameNotFoundException("Пользователя с таким сочетанием почты и пароля не существует");
        else return new UserPrincipal(user);
    }
}