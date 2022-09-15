package geometry.circle;

import processing.core.PVector;

import static geometry.constants.CoordinateIndex.X_INDEX;
import static geometry.constants.CoordinateIndex.Y_INDEX;
import static processing.core.PVector.dist;

public abstract class CirclesIntersection {
    private final static int RADIUS_INDEX = 0;

    public static float[][] findIntersectionOfTwoCircles(
            float[][] firstCircleParameters,
            float[][] secondCircleParameters
    ) {
        validateCircleParameters(firstCircleParameters, secondCircleParameters);

        validateIfCircleIntersects(firstCircleParameters, secondCircleParameters);

        return findCirclesIntersects(firstCircleParameters, secondCircleParameters);
    }

    private static void validateCircleParameters(float[][] firstCircleParameters, float[][] secondCircleParameters) {
        if (firstCircleParameters.length != 2 && secondCircleParameters.length != 2) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (firstCircleParameters[0].length != 2 && firstCircleParameters[1].length != 1) {
            throw new IllegalArgumentException("Invalid first parameter, should [[xCenter, yCenter], [radius]]");
        }

        if (secondCircleParameters[0].length != 2 && secondCircleParameters[1].length != 1) {
            throw new IllegalArgumentException("Invalid second parameter, should [[xCenter, yCenter], [radius]]");
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

    private static float distBetweenCircleCenters(float[] firstCircleCoord, float[] secondCircleCoord) {
        return dist(
                new PVector(firstCircleCoord[X_INDEX], firstCircleCoord[Y_INDEX]),
                new PVector(secondCircleCoord[X_INDEX], secondCircleCoord[Y_INDEX])
        );
    }
}
