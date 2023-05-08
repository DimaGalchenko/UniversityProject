package view.component.util;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class DrawUtil {

    public static void drawArrow(Graphics g1, double x1, double y1, double x2, double y2 ) {
        Graphics2D ga = (Graphics2D) g1.create();
        ga.drawLine((int)x1, (int)y1, (int)x2, (int)y2);

        double l = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));//  line length
        double d = l / 5; // arrowhead distance from end of line. you can use your own value.

        double newX = ((x2 + (((x1 - x2) / (l) * d)))); // new x of arrowhead position on the line with d distance from end of the line.
        double newY = ((y2 + (((y1 - y2) / (l) * d)))); // new y of arrowhead position on the line with d distance from end of the line.

        double dx = x2 - x1, dy = y2 - y1;
        double angle = (Math.atan2(dy, dx)); // get angle (Radians) between ours line and x vectors line. (counter clockwise)
        angle = (-1) * Math.toDegrees(angle);// convert to degree and reverse it to round clock for better understand what we need to do.
        if (angle < 0) {
            angle = 360 + angle; // convert negative degrees to positive degree
        }
        angle = (-1) * angle; // convert to counter clockwise mode
        angle = Math.toRadians(angle);//  convert Degree to Radians
        AffineTransform at = new AffineTransform();
        at.translate(newX, newY);// transport cursor to draw arrowhead position.
        at.rotate(angle);
        ga.transform(at);

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(5, 0);
        arrowHead.addPoint(-5, 5);
        arrowHead.addPoint(-2, -0);
        arrowHead.addPoint(-5, -5);
        ga.fill(arrowHead);
        ga.drawPolygon(arrowHead);
    }
}
