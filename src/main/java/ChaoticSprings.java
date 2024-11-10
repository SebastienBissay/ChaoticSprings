import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class ChaoticSprings extends PApplet {
    public static void main(String[] args) {
        PApplet.main(ChaoticSprings.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        noLoop();
    }

    @Override
    public void draw() {
        Spring[] springs = new Spring[NUMBER_OF_SPRINGS];
        for (int i = 0; i < NUMBER_OF_SPRINGS; i++) {
            PVector p = new PVector(width / 8f, height / 8f)
                    .add((PVector.fromAngle(i * TWO_PI / NUMBER_OF_SPRINGS)
                            .mult(min(width, height) / 12f)));
            springs[i] = new Spring(p.x, p.y);
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (sq(x - width / 2f) + sq(y - height / 2f) > sq(min(width, height) / 2f - OUTER_MARGIN)) {
                    continue;
                }
                PVector point = new PVector(x / 4f, y / 4f);
                PVector speed = new PVector(0, 0), acceleration;
                for (int k = 0; k < MAX_ITERATIONS; k++) {
                    acceleration = PVector.mult(speed, -DAMPENING);
                    for (Spring spring : springs) {
                        acceleration.add(spring.getForce(point).div(DELTA_T));
                    }
                    speed.add(acceleration.div(DELTA_T));
                    point.add(PVector.div(speed, DELTA_T));
                    if (speed.magSq() < sq(SPEED_LOW_THRESHOLD)) {
                        break;
                    }
                }
                float[] distances = new float[NUMBER_OF_SPRINGS];
                for (int i = 0; i < NUMBER_OF_SPRINGS; i++) {
                    distances[i] = PVector.sub(point, springs[i].getPosition()).magSq();
                }
                int indexOfClosestSpring = 0;
                for (int i = 1; i < NUMBER_OF_SPRINGS; i++) {
                    if (distances[i] < distances[indexOfClosestSpring]) {
                        indexOfClosestSpring = i;
                    }
                }
                Color color = PALETTE[indexOfClosestSpring];
                stroke(color.red(), color.green(), color.blue());
                point(x, y);
            }
        }
        saveSketch(this);
    }
}
