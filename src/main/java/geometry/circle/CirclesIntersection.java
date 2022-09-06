package geometry.circle;

import processing.core.PVector;

import static geometry.constants.CoordinateIndex.X_INDEX;
import static geometry.constants.CoordinateIndex.Y_INDEX;
import static processing.core.PVector.dist;

public abstract class CirclesIntersection {

    public static float[][] findIntersectionOfTwoCircles(
            float[] firstCircleCoord,
            float r1,
            float[] secondCircleCoord,
            float r2
    ) {
        float x1 = firstCircleCoord[X_INDEX];
        float y1 = firstCircleCoord[Y_INDEX];

        float x2 = secondCircleCoord[X_INDEX];
        float y2 = secondCircleCoord[Y_INDEX];

        float distanceBetweenTwoCenters =
                dist(
                        new PVector(x1, y1),
                        new PVector(x2, y2)
                );

        if(distanceBetweenTwoCenters > r1 + r2) {
            throw new IllegalArgumentException("The circles are too far apart and do not intersect");
        }

        if(distanceBetweenTwoCenters < Math.abs(r1 - r2)) {
            throw new IllegalArgumentException("One circle is inside the other and do not intersect");
        }

        if(distanceBetweenTwoCenters == 0 && r1 == r2) {
            throw new IllegalArgumentException("The circles are merged and there are an infinite number of points of intersection");
        }

        float a = (float) (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(distanceBetweenTwoCenters, 2))
                / (2 * distanceBetweenTwoCenters);

        float h = (float) Math.sqrt(Math.pow(r1, 2) - Math.pow(a, 2));

        float x5 = x1 + (a/distanceBetweenTwoCenters) * (x2 - x1);
        float y5 = y1 + (a/distanceBetweenTwoCenters) * (y2 - y1);

        float ix1 = x5 - h * (y2-y1) / distanceBetweenTwoCenters;
        float iy1 = y5 + h * (x2-x1) / distanceBetweenTwoCenters;

        float ix2 = x5 + h * (y2-y1) / distanceBetweenTwoCenters;
        float iy2 = y5 - h * (x2-x1) / distanceBetweenTwoCenters;
        return new float[][]{{ix1, iy1}, {ix2, iy2}};
    }
}
