import processing.core.PVector;

import static parameters.Parameters.HEIGHT;
import static parameters.Parameters.WIDTH;

public class Spring {
    private final PVector position;
    private final float length;

    public Spring(float x, float y) {
        position = new PVector(x, y);
        length = PVector.sub(position, new PVector(WIDTH / 8f, HEIGHT / 8f)).mag();
    }

    PVector getForce(PVector point) {
        PVector force = PVector.sub(position, point);
        return force.setMag(force.mag() - length);
    }

    public PVector getPosition() {
        return position;
    }
}
