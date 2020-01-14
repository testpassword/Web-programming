package logic.config;

import org.slf4j.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Класс, представляющий служебные бины на глобальном уровне.
 * @author Артемий Кульбако
 * @version 1.0
 */
@Component
public class ApplicationBeansProvider {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("application");
    }
}