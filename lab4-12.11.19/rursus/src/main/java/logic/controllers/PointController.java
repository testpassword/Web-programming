package logic.controllers;

import entities.Point;
import entities.User;
import logic.PasswordManager;
import logic.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path = "point")
public class PointController {

    @Autowired
    private UserRepository userRepo;

    @PutMapping
    private ResponseEntity addPoint(@RequestBody Map<String, String> data) {
        //Получить пользователя и добавить точку
        return new ResponseEntity(HttpStatus.CHECKPOINT);
    }

    @PostMapping
    private ResponseEntity loadPoints(@RequestBody Map<String, String> data) {
        //Получить пользователя и вернуть точки
        return new ResponseEntity(HttpStatus.CHECKPOINT);
    }

    @DeleteMapping
    private ResponseEntity deletePoints(@RequestBody Map<String, String> data) {
        //Получить пользователя и удалить все точки
        return new ResponseEntity(HttpStatus.CHECKPOINT);
    }
}