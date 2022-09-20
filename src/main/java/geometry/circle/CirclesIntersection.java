package geometry.circle;

import processing.core.PVector;

import java.util.Arrays;

import static geometry.constants.CoordinateIndex.X_INDEX;
import static geometry.constants.CoordinateIndex.Y_INDEX;
import static processing.core.PVector.dist;

public abstract class CirclesIntersection {
    private final static int RADIUS_INDEX = 0;

    public static float[][] findIntersectionOfTwoCircles(
            float[][] firstCircleParameters,
            float[][] secondCircleParameters
    ) {
        validateCircleParameters(firstCircleParameters);
        validateCircleParameters(secondCircleParameters);

        validateIfCircleIntersects(firstCircleParameters, secondCircleParameters);

        return findCirclesIntersects(firstCircleParameters, secondCircleParameters);
    }

    public static float[][] findIntersectionOfCircleAndLine(float[][] circleParameters, float[] lineParameters) {
        validateCircleParameters(circleParameters);
        validateLineParameters(lineParameters);

        return findCircleLineIntersects(circleParameters, lineParameters);
    }

    private static void validateCircleParameters(float[][] circleParameters) {
        if (circleParameters.length != 2) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (circleParameters[0].length != 2 && circleParameters[1].length != 1) {
            throw new IllegalArgumentException("Invalid parameters, should [[xCenter, yCenter], [radius]]");
        }
    }

    private static void validateLineParameters(float[] lineParameters) {
        if(lineParameters.length != 3) {
            throw new IllegalArgumentException("Invalid line parameters should be: [A, B, C]");
        }
    }

    private static void validateIfCircleIntersects(float[][] firstCircleParameters, float[][] secondCircleParameters) {
        float distanceBetweenTwoCenters = distBetweenCircleCenters(firstCircleParameters[0], secondCircleParameters[0]);

        float r1 = firstCircleParameters[1][RADIUS_INDEX];
        float r2 = secondCircleParameters[1][RADIUS_INDEX];

        if (distanceBetweenTwoCenters > r1 + r2) {
            throw new IllegalArgumentException("The circles are too far apart and do not intersect");
        }

        if (distanceBetweenTwoCenters < Math.abs(r1 - r2)) {
            throw new IllegalArgumentException("One circle is inside the other and do not intersect");
        }

        if (distanceBetweenTwoCenters == 0 && r1 == r2) {
            throw new IllegalArgumentException("The circles are merged and there are an infinite number of points of intersection");
        }
    }

    private static float[][] findCirclesIntersects(float[][] firstCircleParameters, float[][] secondCircleParameters) {
        float x1 = firstCircleParameters[0][X_INDEX];
        float y1 = firstCircleParameters[0][Y_INDEX];

        float x2 = secondCircleParameters[0][X_INDEX];
        float y2 = secondCircleParameters[0][Y_INDEX];

        float distanceBetweenTwoCenters = distBetweenCircleCenters(firstCircleParameters[0], secondCircleParameters[0]);

        float r1 = firstCircleParameters[1][RADIUS_INDEX];
        float r2 = secondCircleParameters[1][RADIUS_INDEX];

        float a = (float) (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(distanceBetweenTwoCenters, 2))
                / (2 * distanceBetweenTwoCenters);

        float h = (float) Math.sqrt(Math.pow(r1, 2) - Math.pow(a, 2));

        float x5 = x1 + (a / distanceBetweenTwoCenters) * (x2 - x1);
        float y5 = y1 + (a / distanceBetweenTwoCenters) * (y2 - y1);

        float ix1 = x5 - h * (y2 - y1) / distanceBetweenTwoCenters;
        float iy1 = y5 + h * (x2 - x1) / distanceBetweenTwoCenters;

        float ix2 = x5 + h * (y2 - y1) / distanceBetweenTwoCenters;
        float iy2 = y5 - h * (x2 - x1) / distanceBetweenTwoCenters;
        return new float[][]{{ix1, iy1}, {ix2, iy2}};
    }

    private static float[][] findCircleLineIntersects(float[][] circleParameters, float[] lineParameters) {
        float coordinateOutOfScreen = 5000;
        float[] point1;
        float[] point2;
        if (lineParameters[0] == 0) {
            point1 = new float[]{0, (-lineParameters[0] * 0 - lineParameters[2]) / lineParameters[1]};
            point2 = new float[]{coordinateOutOfScreen, (-lineParameters[0] * coordinateOutOfScreen - lineParameters[2]) / lineParameters[1]};
        } else {
            point1 = new float[]{-lineParameters[2] / lineParameters[0], 0};
            point2 = new float[]{((-lineParameters[2] - lineParameters[1] * coordinateOutOfScreen) / lineParameters[0]), coordinateOutOfScreen};
        }

        float xCircleCenter = circleParameters[0][X_INDEX];
        float yCircleCenter = circleParameters[0][Y_INDEX];
        float radius = circleParameters[1][RADIUS_INDEX];
        float x1 = point1[0], y1 = point1[1];
        float x2 = point2[0], y2 = point2[1];

        float dx = x2 - x1;
        float dy = y2 - y1;
        float a = dx * dx + dy * dy;
        float b = 2 * dx * (x1 - xCircleCenter) + 2 * dy * (y1 - yCircleCenter);
        double c = Math.pow((x1 - xCircleCenter), 2) + Math.pow((y1 - yCircleCenter), 2) - radius * radius;

        double d = b * b - 4 * a * c;
        if (d >= 0) {
            double root = Math.sqrt(d);
            double t1 = 2 * c / (-b + root);
            double t2 = 2 * c / (-b - root);
            double interX1 = x1 + dx * t1;
            double interY1 = y1 + dy * t1;
            double interX2 = x1 + dx * t2;
            double interY2 = y1 + dy * t2;
            return new float[][]{{(float) interX1, (float) interY1}, {(float) interX2, (float) interY2}};
        }
        return new float[0][0];
    }

    private static float distBetweenCircleCenters(float[] firstCircleCoord, float[] secondCircleCoord) {
        return dist(
                new PVector(firstCircleCoord[X_INDEX], firstCircleCoord[Y_INDEX]),
                new PVector(secondCircleCoord[X_INDEX], secondCircleCoord[Y_INDEX])
        );
    }
}
