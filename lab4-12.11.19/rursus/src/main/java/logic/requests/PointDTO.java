package logic.requests;

import logic.models.Point;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Объект запроса согласно шаблону проектирования Data Transfer Object для Point-контроллера.
 * @see logic.controllers.PointController
 * @author Кульбако Артемий
 * @version 1.1
 */
@Data
public class PointDTO implements Serializable {
    private static final long serialVersionUID = 4L;
    @NotNull private Point point;
}
