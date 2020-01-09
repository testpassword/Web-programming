package logic.controllers;

import logic.models.*;
import logic.requests.PointDTO;
import logic.security.JWTUtil;
import logic.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Обрабатывает запросы к url-у /api/point.
 * @author Артемий Кульбако
 * @version 1.2
 */
@Slf4j @RestController @RequestMapping(path = "/api/point")
public class PointController {

    //TODO валидация
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Autowired public PointController(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /* В приведённых ниже методах для идентификации пользователя используется jwt. Он имеет вид 'Bearer hash'. Для корректной
    работы с ним, необходимо после получения его из заголовка, отрезать часть 'Bearer '. Можно делать это вручную,
    получив RequestHeader("Authorization") String token в сигнатуре метода, и обрезав часть в теле метода, либо передать
    чистый запрос экземпляру класса JWTUtil, обрабатывающему jwt. Второй вариант будет более правильным, так как в теории,
    мы можем не знать какую часть токена надо обрезать. */

    @PutMapping
    private ResponseEntity<String> addPoint(@Valid @RequestBody PointDTO data, HttpServletRequest req) {
        User modifiedUser = userService.findByEmail(jwtUtil.getUsername(jwtUtil.resolveToken(req)));
        Point p = new Point(data.getX(), data.getY(), data.getR());
        modifiedUser.getPoints().add(p);
        userService.updateUser(modifiedUser);
        log.info("Пользователь " + modifiedUser.getEmail() + " добавил точку " + p.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<Point>> loadPoints(HttpServletRequest req) {
        User user = userService.findByEmail(jwtUtil.getUsername(jwtUtil.resolveToken(req)));
        log.info("Пользователь " + user.getEmail() + " загрузил точки");
        return new ResponseEntity<>(user.getPoints(), HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<String> clearPoints(HttpServletRequest req) {
        User modifiedUser = userService.findByEmail(jwtUtil.getUsername(jwtUtil.resolveToken(req)));
        modifiedUser.getPoints().clear();
        userService.updateUser(modifiedUser);
        log.info("Пользователь " + modifiedUser.getEmail() + " очистил коллекцию точек");
        return new ResponseEntity<>("Все ваши точки удалены", HttpStatus.OK);
    }
}