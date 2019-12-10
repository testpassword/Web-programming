package server.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "user")
public class UserController {

    @RequestMapping(method = RequestMethod.POST)
    private @ResponseBody String login() {
        /*
        TODO: обращаемся к базе данных и ищем пользователя
         если нашли вернуть true
         если не нашли вернуть http-код 401
         */
        return "лог";
    }

    @RequestMapping(method = RequestMethod.PUT)
    private @ResponseBody String register() {
        /*
        TODO: проверить логин на уникальность в бд
            если +:
                создать пользователя new User(params)
                если при создании выкинул exception , то обработать его и вернуть отрицательный ответ клиенту
                если пользователь создался вернуть положительный ответ
            если -:
                вернуть отрицательный ответ
         */
        return "рег";
    }
}
