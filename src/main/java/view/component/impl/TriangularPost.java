package view.component.impl;

import view.component.Component2D;
import view.component.label.ComponentNameHelper;
import view.listeners.MovingAdapter;
import view.listeners.ScaleHandler;

import java.awt.*;
import java.awt.geom.Point2D;

import static view.component.impl.ComponentConstant.COMPONENT_NODE_RADIUS;

public class TriangularPost extends Component2D {
    private ComponentNode componentNode;
    private boolean isDeleted = false;
    private final String componentNodeSerialName;

    public TriangularPost(int xPosition, int yPosition) {
        super(xPosition, yPosition, 1.0, 0);
        this.boundingWidth = 115;
        this.boundingHeight = 115;
        setBounds(xPosition, yPosition, boundingWidth, boundingHeight);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.scale = 1.0;
        this.componentNodeSerialName = ComponentNameHelper.generateFixedComponentName();
        MovingAdapter<TriangularPost> movingAdapter = new MovingAdapter<>(this);
        addMouseMotionListener(movingAdapter);
        addMouseListener(movingAdapter);
        addMouseWheelListener(new ScaleHandler<>(this));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (isDeleted) {
            return;
        }

        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        double centerOfComponentX = 0;
        double centerOfComponentY = 0;
        if (rotateAngle >= 0 && rotateAngle < 90) {
            centerOfComponentX = boundingWidth / 2.0;
            centerOfComponentY = boundingHeight / 100.0 * 20;
        } else if (rotateAngle >= 90 && rotateAngle < 180) {
            centerOfComponentX = boundingWidth / 100.0 * 80;
            centerOfComponentY = boundingHeight / 2.0;
        } else if (rotateAngle >= 180 && rotateAngle < 270) {
            centerOfComponentX = boundingWidth / 2.0;
            centerOfComponentY = boundingHeight / 100.0 * 80;
        } else if (rotateAngle >= 270 && rotateAngle < 360) {
            centerOfComponentX = boundingWidth / 100.0 * 20;
            centerOfComponentY = boundingHeight / 2.0;
        }


        double radius = COMPONENT_NODE_RADIUS * scale;
        double lengthOfLine = 70 * scale;
        double lengthOfLineDash = 15 * scale;

        // List which consist three sides of a triangle
        Point2D[] points = new Point2D[3];

        Point2D prevPoint = new Point2D.Double(centerOfComponentX, centerOfComponentY);
        // angle between sides of the triangle
        int alfa = 60;

        // draws the triangle
        for (int i = 0; i < 3; i++) {
            double startAngle = Math.toRadians(rotateAngle + alfa + alfa * i * 2);
            Point2D endPoint = pointByAngleAndLengthFromDot(prevPoint, lengthOfLine, startAngle);
            points[i] = endPoint;
            drawLine(graphics, prevPoint, endPoint);
            prevPoint = endPoint;
        }

        if (componentNode == null) {
            componentNode = new ComponentNode(
                    (int) centerOfComponentX,
                    (int) centerOfComponentY,
                    scale,
                    radius,
                    componentNodeSerialName
            );
            add(componentNode);
            subComponents.add(componentNode);
        }

        componentNode.setXPosition((int) centerOfComponentX);
        componentNode.setYPosition((int) centerOfComponentY);
        componentNode.setBounds();
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
            double startLinesY = startLinesPrevPoint.getY() + spaceBetween * Math.sin(linesStartAlfa);
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
        Font font = new Font("Serif", Font.PLAIN, 16);
        g.setColor(Color.BLUE);
        g.setFont(font);
        g.drawString("O", (int) startLinesPrevPoint.getX(), (int) startLinesPrevPoint.getY());
        g.setColor(Color.BLACK);

    }
}
