import geometry.line.EquationOfLine;
import processing.core.PApplet;

import java.util.Arrays;

public class Application extends PApplet {

    public void settings() {
        size(600, 600);

    }

    public void draw() {
        background(1200f);

        float[][] twoDots = new float[2][2];
        float[] dot1 = new float[]{1f, 1f};
        float[] dot2 = new float[]{100f, 100f};
        twoDots[0] = dot1;
        twoDots[1]  = dot2;
        try {
            float[] lineCoef = EquationOfLine.by(twoDots);
            float x1 = 0;
            float y1 = (-1 * lineCoef[0] * x1 - lineCoef[2])/lineCoef[1];
            float x2 = 800;
            float y2 = (-1 * lineCoef[0] * x2 - lineCoef[2])/lineCoef[1];
            line(x1, y1, x2, y2);
            float[] paralLineCoef = EquationOfLine.parallelBy(twoDots, new float[] {100, 0});
            System.out.println(Arrays.toString(paralLineCoef));
            float x3 = 20;
            float y3 = (-1 * paralLineCoef[0] * x3 - paralLineCoef[2])/paralLineCoef[1];
            float x4 = 400;
            float y4 = (-1 * paralLineCoef[0] * x4 - paralLineCoef[2])/paralLineCoef[1];
            line(x3, y3, x4, y4);
            float[] orthoLineCoef = EquationOfLine.orthogonalBy(twoDots, new float[] {100, 500});
            System.out.println(Arrays.toString(paralLineCoef));
            float x5 = 20;
            float y5 = (-1 * orthoLineCoef[0] * x5 - orthoLineCoef[2])/orthoLineCoef[1];
            float x6 = 400;
            float y6 = (-1 * orthoLineCoef[0] * x6 - orthoLineCoef[2])/orthoLineCoef[1];
            line(x5, y5, x6, y6);
        } catch (Exception ignore) {
            throw new RuntimeException(ignore);
        }
    }

    public static void main(String... args) {
        String[] strings = new String[]{"Application"};
        PApplet.main(strings);

        float[][] twoDots = new float[2][2];
        float[] dot1 = new float[]{1f, 1f};
        float[] dot2 = new float[]{110f, 110f};
        twoDots[0] = dot1;
        twoDots[1]  = dot2;
        try {
            float[] lineCoef = EquationOfLine.by(twoDots);
            System.out.println(Arrays.toString(lineCoef));

            float x1 = 0;
            float y1 = (-1 * lineCoef[0] * x1 - lineCoef[2])/lineCoef[1];
            float x2 = 800;
            float y2 = (-1 * lineCoef[0] * x2 - lineCoef[2])/lineCoef[1];
            //line(x1, y1, x2, y2);
            float[] paralLineCoef = EquationOfLine.parallelBy(twoDots, new float[] {200, 40});
            System.out.println(Arrays.toString(paralLineCoef));
            paralLineCoef[2] = 40;
            float x3 = 20;
            float y3 = (-1 * paralLineCoef[0] * x3 - paralLineCoef[2])/paralLineCoef[1];
            float x4 = 400;
            float y4 = (-1 * paralLineCoef[0] * x4 - paralLineCoef[2])/paralLineCoef[1];
            //line(x3, y3, x4, y4);
        } catch (Exception ignore) {
            throw new RuntimeException(ignore);
        }
    }
}
