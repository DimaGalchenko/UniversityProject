package view.component.impl;

import view.component.Component2D;
import view.listeners.MovingAdapter;
import view.listeners.ScaleHandler;

import java.awt.*;
import java.awt.geom.Point2D;
public class TriangularPost extends Component2D {
    private ComponentNode componentNode;
    public TriangularPost(int xPosition, int yPosition) {
        super(xPosition, yPosition, 1.0);
        setBounds(xPosition, yPosition, 100, 100);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.scale = 1.0;
        MovingAdapter<TriangularPost> movingAdapter = new MovingAdapter<>(this);
        addMouseMotionListener(movingAdapter);
        addMouseListener(movingAdapter);
        addMouseWheelListener(new ScaleHandler<>(this));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        double centerOfComponentX = 50;
        double centerOfComponentY = 30;
        double radius = 6 * scale;
        double lengthOfLine = 50 * scale;
        double lengthOfLineDash = 10 * scale;
        double rotateAngle = 0;

        // List which consist three sides of a triangle
        Point2D[] points = new Point2D[3];

        Point2D prevPoint = new Point2D.Double(centerOfComponentX, centerOfComponentY);
        // angle between sides of the triangle
        int alfa = 60;

        // draws the triangle
        for (int i = 0; i < 3; i++) {
            double alfa1 = Math.toRadians(rotateAngle + alfa + alfa * i * 2);
            Point2D endPoint = pointByAngleAndLengthFromDot(prevPoint, lengthOfLine, alfa1);
            points[i] = endPoint;
            drawLine(graphics, prevPoint, endPoint);
            prevPoint = endPoint;
        }

        if (componentNode == null) {
            componentNode = new ComponentNode( boundingWidth / 2,  (boundingHeight / 100) * 30, scale, radius);
            add(componentNode);
        }
        componentNode.repaint();
        componentNode.updateDependencies();


        // This angle specifies that the lines will be drawn parallel to the rotation angle
        double linesStartAlfa = rotateAngle;
        linesStartAlfa = Math.toRadians(linesStartAlfa);

        // This is the point from which the lines will be drawn (left angle at the base)
        Point2D startDashesPoint = points[1];
        Point2D startLinesPrevPoint = startDashesPoint;

        double spaceBetween;
        for (int i = 0; i < 6; i++) {
            // first line draws on distanse 0 from start point
            if (i == 0) {
                spaceBetween = 0;
                // Subsequent lines draws with space between
            } else {
                spaceBetween = lengthOfLine / 5;
            }
            // Calculate start point of line
            double startLinesX = startLinesPrevPoint.getX() + spaceBetween * Math.cos(linesStartAlfa);
            double startLinesY = startLinesPrevPoint.getY() + spaceBetween  * Math.sin(linesStartAlfa);
            // Calculate end point of line
            double endLineX = startLinesX + lengthOfLineDash * Math.cos(linesStartAlfa + 120);
            double endLineY = startLinesY + lengthOfLineDash * Math.sin(linesStartAlfa + 120);
            drawLine(
                    graphics,
                    new Point2D.Double(startLinesX, startLinesY),
                    new Point2D.Double(endLineX, endLineY)
            );

            startLinesPrevPoint = new Point2D.Double(startLinesX, startLinesY);
        }
    }
}
