import geometry.circle.CirclesIntersection;
import geometry.line.EquationOfLine;
import processing.core.PApplet;

public class Application extends PApplet {

    public void settings() {
        size(600, 600);

    }

    public void draw() {
//        background(1200f);

//        for(int i = 0; i < 600; i++) {
//
//            float x1 = (float) Math.sin(i/100);
//            float x2 = (float) Math.cos(i/100);
//
//            float[] circleCoord1 = new float[]{x1, 200};
//            float r1 = 200;
//            float[] circleCoord2 = new float[]{x2, 215};
//            float r2 = 200;
//
//            float[][] iter = CirclesIntersection.findIntersectionOfTwoCircles(
//                    circleCoord1,
//                    r1,
//                    circleCoord2,
//                    r2
//            );
//
//            float[] i1 = iter[0];
//            float[] i2 = iter[1];
//
//            color(255);
//            noFill();
//
//            circle(circleCoord1[0], circleCoord1[1], r1 * 2);
//            circle(circleCoord2[0], circleCoord2[1], r2 * 2);
//            circle(i1[0], i1[1], 5);
//            circle(i2[0], i2[1], 5);
//        }


//        for(int i = 50; i < 600; i=i+10) {
//            float y1 = (float) (Math.sin(i / 100.0) * 40);
//            float y2 = (float) (Math.cos(i / 100.0) * 40);
//
//            float r1 = 30;
//            float[][] circleCoord1 = new float[][]{{i, y1 + 300}, {r1}};
//
//            float r2 = 30;
//            float[][] circleCoord2 = new float[][]{{i, y2 + 300}, {r2}};
//
//
//            float[][] iter = CirclesIntersection.findIntersectionOfTwoCircles(
//                    circleCoord1,
//                    circleCoord2
//            );
//
//            float[] i1 = iter[0];
//            float[] i2 = iter[1];
//
//            color(255);
//            noFill();
//
//            circle(circleCoord1[0][0], circleCoord1[0][1], r1 * 2);
//            circle(circleCoord2[0][0], circleCoord2[0][1], r2 * 2);
//            circle(i1[0], i1[1], 5);
//            circle(i2[0], i2[1], 5);
//            return;
//        }
//                    color(255);
//            noFill();
        noFill();
        float[][] circle = new float[][]{{300, 300}, {30}};
        float[] line = EquationOfLine.equationOfLineBy(new float[][]{{0,274},{600,274}});
        float[][] iter = CirclesIntersection.findIntersectionOfCircleAndLine(
                circle,
                line
            );

        circle(circle[0][0], circle[0][1], circle[1][0] * 2);
        line(0,274, 600, 274);

        float[] i1 = iter[0];
        float[] i2 = iter[1];

        fill(255);
        circle(i1[0], i1[1], 5);
        circle(i2[0], i2[1], 5);
    }

    public static void main(String... args) {
        String[] strings = new String[]{"Application"};
        PApplet.main(strings);
    }
}
