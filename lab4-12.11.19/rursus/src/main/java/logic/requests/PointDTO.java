package logic.requests;

import lombok.Data;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Объект обращения к серверу для {@code PointController}.
 * @see logic.controllers.PointController
 * @author Кульбако Артемий
 * @version 1.2
 */
@Data
public class PointDTO implements Serializable {
    private static final long serialVersionUID = 4L;
    @NotNull @Min(value = -4) @Max(value = 4) private double x;
    @NotNull @Min(value = -5) @Max(value = 3) private double y;
    @NotNull @Min(value = 1) @Max(value = 4) private double r;
}