package logic.controllers;

import logic.models.Point;
import logic.models.User;
import logic.security.JWTUtil;
import logic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Обрабатывает запросы к url-у /point.
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

    @PutMapping
    private ResponseEntity<String> addPoint(@RequestBody Map<String, String> req) {
        //Получить пользователя и добавить точку
        String token = req.get("token");
        Point p = new Point(Double.parseDouble(req.get("x")), Double.parseDouble(req.get("y")), Double.parseDouble(req.get("r")));
        String email = jwtUtil.getUsername(token);
        User modifiedUser = userService.findByEmail(email);
        modifiedUser.getPoints().add(p);
        userService.updateUser(modifiedUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    private ResponseEntity<String> loadPoints(@RequestBody Map<String, String> req) {
        //Получить пользователя и вернуть точки
        return new ResponseEntity<>(HttpStatus.CHECKPOINT);
    }

    @DeleteMapping
    private ResponseEntity<String> clearPoints(@RequestBody Map<String, String> req) {
        //Получить пользователя и удалить все точки
        return new ResponseEntity<>(HttpStatus.CHECKPOINT);
    }
}