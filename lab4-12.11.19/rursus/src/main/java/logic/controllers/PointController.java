package logic.controllers;

import logic.models.Point;
import logic.models.User;
import logic.requests.PointDTO;
import logic.security.JWTUtil;
import logic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Обрабатывает запросы к url-у /point.
 * @author Артемий Кульбако
 * @version 1.2
 */
@RestController @RequestMapping(path = "point")
public class PointController {

    //TODO валидация
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Autowired public PointController(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /* В приведённых ниже методах для идентификации пользователя используется jwt. Он имеет вид 'Bearer hash'. Для корректной
    работы с ним, необходимо после получения его из заголовка, отрезать часть 'Bearer '. */

    @PutMapping
    private ResponseEntity<String> addPoint(@Valid @RequestBody PointDTO req, @RequestHeader("Authorization") String token) {
        User modifiedUser = userService.findByEmail(jwtUtil.getUsername(token.substring(7)));
        /* Так-как проверка попадании точки в одз и создания временной метки находится в конструкторе, а @RequestBody
        работает через аксессоры, то необходимо сконструировать новую точку из данных запроса. */
        Point incompletePoint = req.getPoint();
        Point p = new Point(incompletePoint.getX(), incompletePoint.getY(), incompletePoint.getR());
        modifiedUser.getPoints().add(p);
        userService.updateUser(modifiedUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<Point>> loadPoints(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(userService.findByEmail(jwtUtil.getUsername(token.substring(7))).getPoints(), HttpStatus.CHECKPOINT);
    }

    @DeleteMapping
    private ResponseEntity<String> clearPoints(@RequestHeader("Authorization") String token) {
        User modifiedUser = userService.findByEmail(jwtUtil.getUsername(token.substring(7)));
        modifiedUser.getPoints().clear();
        userService.updateUser(modifiedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}