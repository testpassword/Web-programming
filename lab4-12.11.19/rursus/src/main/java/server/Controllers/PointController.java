package server.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "point")
public class PointController {

    @PutMapping
    private @ResponseBody String addPoint() {
        return "добавлено";
    }

    @PostMapping
    private @ResponseBody String loadPoints() {
        return "загружено";
    }

    @DeleteMapping
    private @ResponseBody String deletePoints() {
        return "удалено";
    }
}
