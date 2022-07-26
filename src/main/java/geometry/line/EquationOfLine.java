package geometry.line;

import java.util.Arrays;

public abstract class EquationOfLine {
    public static final int POINT_COORDINATE_COUNT = 2;
    public static final int TWO_POINT_COUNT = 2;
    public static final int X_INDEX = 0;
    public static final int Y_INDEX = 1;
    public static final int A_COEFFICIENT_OF_LINE_EQUATION = 0;
    public static final int B_COEFFICIENT_OF_LINE_EQUATION = 1;
    public static final int C_COEFFICIENT_OF_LINE_EQUATION = 2;

    public static float[] equationOfLineBy(float[][] twoPoints) {
        return by(twoPoints);
    }

    public static float[] equationOfParallelBy(float[][] twoPoints, float[] pointOfParallelLine) {
        return EquationOfLine.by(LinePosition.PARALLEL, twoPoints, pointOfParallelLine);
    }

    public static float[] equationOfOrthogonalBy(float[][] twoPoints, float[] pointOfOrthogonalLine) {
        return EquationOfLine.by(LinePosition.ORTHOGONAL, twoPoints, pointOfOrthogonalLine);
    }

    private static float[] by(LinePosition linePosition, float[][] twoPoints, float[] pointOfAnotherLine) {
        validatePointsCount(TWO_POINT_COUNT, twoPoints);
        validateAndGetPoint(pointOfAnotherLine);

        float[] firstLineCoefficients = EquationOfLine.by(twoPoints);

        float secondYCoordOfParallelLine = 1;
        float a1y2 = firstLineCoefficients[A_COEFFICIENT_OF_LINE_EQUATION] * secondYCoordOfParallelLine;
        float a1y1 = firstLineCoefficients[A_COEFFICIENT_OF_LINE_EQUATION] * pointOfAnotherLine[Y_INDEX];
        float b1x1 = firstLineCoefficients[B_COEFFICIENT_OF_LINE_EQUATION] * pointOfAnotherLine[X_INDEX];

        if (linePosition == LinePosition.PARALLEL) {
            a1y2 = a1y2 * (-1);
        }
        if (linePosition == LinePosition.ORTHOGONAL) {
            a1y1 = a1y1 * (-1);
        }

        float secondXCoordOfParallelLine = (a1y1 + a1y2 + b1x1) / firstLineCoefficients[B_COEFFICIENT_OF_LINE_EQUATION];
        return EquationOfLine.by(new float[][]{
                pointOfAnotherLine,
                {secondXCoordOfParallelLine, secondYCoordOfParallelLine}
        });
    }

    private static float[] by(float[][] twoPoints) {
        validatePointsCount(TWO_POINT_COUNT, twoPoints);

        float[] firstPoint = validateAndGetPoint(twoPoints[0]);
        float[] secondPoint = validateAndGetPoint(twoPoints[1]);

        float a = secondPoint[Y_INDEX] - firstPoint[Y_INDEX];
        float b = firstPoint[X_INDEX] - secondPoint[X_INDEX];
        float c = -a * firstPoint[X_INDEX] - b * firstPoint[Y_INDEX];
        return new float[]{a, b, c};
    }

    private static float[] validateAndGetPoint(float[] point) {
        validatePoint(point);
        return point;
    }

    private static void validatePointsCount(int countOfPoints, float[][] points) {
        if (points.length != countOfPoints) {
            throw new IllegalArgumentException(
                    String.format("%s points must be passed, but were passed: %s", countOfPoints, points.length)
            );
        }
    }

    private static void validatePoint(float[] point) {
        if (point.length != POINT_COORDINATE_COUNT) {
            throw new IllegalArgumentException(
                    String.format(
                            "The point must consist of two coordinates [x, y], but now consist of: %s",
                            Arrays.toString(point)
                    )
            );
        }
    }

}
