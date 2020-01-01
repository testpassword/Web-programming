package logic.controllers;

import logic.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "point")
public class PointController {

    @Autowired
    private UserRepository userRepo;

    @PutMapping
    private ResponseEntity<String> addPoint(@RequestBody Map<String, String> data) {
        //Получить пользователя и добавить точку
        return new ResponseEntity<>(HttpStatus.CHECKPOINT);
    }

    @PostMapping
    private ResponseEntity<String> loadPoints(@RequestBody Map<String, String> data) {
        //Получить пользователя и вернуть точки
        return new ResponseEntity<>(HttpStatus.CHECKPOINT);
    }

    @DeleteMapping
    private ResponseEntity<String> deletePoints(@RequestBody Map<String, String> data) {
        //Получить пользователя и удалить все точки
        return new ResponseEntity<>(HttpStatus.CHECKPOINT);
    }
}