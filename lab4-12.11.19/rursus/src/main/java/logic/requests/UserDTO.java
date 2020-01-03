package logic.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Объект запроса аутентификации и регистрации согласно шаблону проектирования Data Transfer Object для User-контроллера.
 * @see logic.controllers.UserController
 * @author Кульбако Артемий
 * @version 1.0
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 4L;
    @Email @NotNull private String email;
    @NotNull private String password;
}