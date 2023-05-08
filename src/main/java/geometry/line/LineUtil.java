package geometry.line;

import java.awt.geom.Point2D;

public abstract class LineUtil {

    public static Point2D getLineSegmentCenter(Point2D point1, Point2D point2) {
        return new Point2D.Double(
                (point1.getX() + point2.getX()) / 2.0,
                (point1.getY() + point2.getY()) / 2.0
        );
    }
}
