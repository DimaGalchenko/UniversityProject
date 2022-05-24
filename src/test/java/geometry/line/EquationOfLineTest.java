package geometry.line;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquationOfLineTest {

    @Test
    void shouldGiveEquationOfLineByTwoPoints() throws EquationOfLineException {
        float[][] twoPoints = new float[][] {
                {1, 1},
                {3, 4}
        };
        float[] equationOfLine = EquationOfLine.by(twoPoints);
        float[] expectedEquationOfLine = new float[] {3, -2, -1};
        assertArrayEquals(expectedEquationOfLine, equationOfLine);
    }

    @Test
    void shouldGiveEquationOfLineByTwoNegativePoints() throws EquationOfLineException {
        float[][] twoPoints = new float[][] {
                {-1, -1},
                {-3, -4}
        };
        float[] equationOfLine = EquationOfLine.by(twoPoints);
        float[] expectedEquationOfLine = new float[] {-3, 2, -1};
        assertArrayEquals(expectedEquationOfLine, equationOfLine);
    }

    @Test
    void shouldGiveEquationOfLineByTwoFractionalPoints() throws EquationOfLineException {
        float[][] twoPoints = new float[][] {
                {-1.5f, -1.1f},
                {-3.23f, -4.91f}
        };
        float[] equationOfLine = EquationOfLine.by(twoPoints);
        float[] expectedEquationOfLine = new float[] {-3.81f, 1.73f, -3.812f};
        assertArrayEquals(expectedEquationOfLine, equationOfLine);
    }

    @Test
    void shouldThrowExceptionIfPassedMoreThanTwoPoints() {
        float[][] threePoints = new float[][] {
                {-1.5f, -1.1f},
                {-3.23f, -4.91f},
                {-0.23f, -5.91f}
        };
        assertThrows(EquationOfLineException.class, () -> EquationOfLine.by(threePoints));
    }

    @Test
    void shouldThrowExceptionIfPassedLessThanTwoPoints() {
        float[][] threePoints = new float[][] {
                {-1.5f, -1.1f},
        };
        assertThrows(EquationOfLineException.class, () -> EquationOfLine.by(threePoints));
    }

    @Test
    void shouldThrowExceptionIfFirstPointNotValid() {
        float[][] threePoints = new float[][] {
                { -1.1f},
                {1, 4}
        };
        assertThrows(EquationOfLineException.class, () -> EquationOfLine.by(threePoints));
    }

    @Test
    void shouldThrowExceptionIfSecondPointNotValid() {
        float[][] threePoints = new float[][] {
                { -1.1f, 2},
                {1}
        };
        assertThrows(EquationOfLineException.class, () -> EquationOfLine.by(threePoints));
    }
}