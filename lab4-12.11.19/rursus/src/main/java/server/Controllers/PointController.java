package server.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "point")
public class PointController {

    @RequestMapping(method = RequestMethod.PUT)
    private @ResponseBody String addPoint() {
        return "добавлено";
    }

    @RequestMapping(method = RequestMethod.POST)
    private @ResponseBody String loadPoints() {
        return "загружено";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    private @ResponseBody String deletePoints() {
        return "удалено";
    }
}
