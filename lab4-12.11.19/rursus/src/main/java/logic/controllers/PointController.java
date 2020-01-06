package logic.controllers;

import logic.models.*;
import logic.requests.PointDTO;
import logic.security.JWTUtil;
import logic.services.UserService;
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
@RestController @RequestMapping(path = "/api/point")
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
        modifiedUser.getPoints().add(new Point(data.getX(), data.getY(), data.getR()));
        userService.updateUser(modifiedUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<Point>> loadPoints(HttpServletRequest req) {
        return new ResponseEntity<>(userService.findByEmail(jwtUtil.getUsername(jwtUtil.resolveToken(req))).getPoints(), HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<String> clearPoints(HttpServletRequest req) {
        User modifiedUser = userService.findByEmail(jwtUtil.getUsername(jwtUtil.resolveToken(req)));
        modifiedUser.getPoints().clear();
        userService.updateUser(modifiedUser);
        return new ResponseEntity<>("Все ваши точки удалены", HttpStatus.OK);
    }
}