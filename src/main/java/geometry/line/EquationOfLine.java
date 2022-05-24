package geometry.line;

import java.util.Arrays;

public class EquationOfLine {
    public static final int POINT = 2;

    public static float[] by(float[][] twoPoints) throws EquationOfLineException {
        validatePointsCount(2, twoPoints);

        float[] firstPoint = validateAndGetPoint(twoPoints[0]);
        float[] secondPoint = validateAndGetPoint(twoPoints[1]);

        float a = secondPoint[1] - firstPoint[1];
        float b = firstPoint[0] - secondPoint[0];
        float c = - a * firstPoint[0] - b * firstPoint[1];
        return new float[] {a, b, c};
    }

    public static float[] parallelBy(float[][] twoPoints, float[] pointOfParallelLine) throws EquationOfLineException {
        return EquationOfLine.by(LinePosition.PARALLEL, twoPoints, pointOfParallelLine);
    }

    public static float[] orthogonalBy(float[][] twoPoints, float[] pointOfOrthogonalLine) throws EquationOfLineException {
        return EquationOfLine.by(LinePosition.ORTHOGONAL, twoPoints, pointOfOrthogonalLine);
    }

    private static float[] by(LinePosition linePosition, float[][] twoPoints, float[] pointOfAnotherLine) throws EquationOfLineException {
        validatePointsCount(2, twoPoints);
        validateAndGetPoint(pointOfAnotherLine);

        float[] firstLineCoefficients = EquationOfLine.by(twoPoints);

        float secondYCoordOfParallelLine = 1;
        float a1y2 = firstLineCoefficients[0] * secondYCoordOfParallelLine;
        float a1y1 = firstLineCoefficients[0] * pointOfAnotherLine[1];
        float b1x1 = firstLineCoefficients[1] * pointOfAnotherLine[0];

        if(linePosition == LinePosition.PARALLEL) {
            a1y2 = a1y2 * (-1);
        }
        if(linePosition == LinePosition.ORTHOGONAL) {
            a1y1 = a1y1 * (-1);
        }

        float secondXCoordOfParallelLine = (a1y1 + a1y2 + b1x1) / firstLineCoefficients[1];
        return EquationOfLine.by(new float[][] {
                pointOfAnotherLine,
                {secondXCoordOfParallelLine, secondYCoordOfParallelLine}
        });
    }

    private static float[] validateAndGetPoint(float[] point) throws EquationOfLineException {
        validatePoint(point);
        return point;
    }

    private static void validatePointsCount(int countOfPoints, float[][] points) throws EquationOfLineException {
        if(points.length != countOfPoints) {
            throw new EquationOfLineException(
                    String.format("%s points must be passed, but were passed: %s", countOfPoints, points.length)
            );
        }
    }

    private static void validatePoint(float[] point) throws EquationOfLineException {
        if(point.length != POINT) {
            throw new EquationOfLineException(
                    String.format(
                            "The point must consist of two coordinates [x, y], but now consist of: %s",
                            Arrays.toString(point)
                    )
            );
        }
    }
}
