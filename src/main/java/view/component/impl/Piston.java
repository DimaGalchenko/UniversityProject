package view.component.impl;

import view.component.Component2D;
import view.listeners.MovingAdapter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static view.component.impl.ComponentConstant.COMPONENT_NODE_RADIUS;

public class Piston extends Component2D {

    private ComponentNode componentNode;

    public Piston(int xPosition, int yPosition) {
        super(xPosition, yPosition, 1.0, 0);
        MovingAdapter<Piston> movingAdapter = new MovingAdapter<>(this);
        addMouseMotionListener(movingAdapter);
        addMouseListener(movingAdapter);
        this.boundingWidth = 140;
        this.boundingHeight = 140;
        setBounds(xPosition, yPosition, boundingWidth, boundingHeight);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics = (Graphics2D) g;

        Font font = new Font("Serif", Font.PLAIN, 16);

        double radius = COMPONENT_NODE_RADIUS * scale;

        double xStart = boundingWidth / 100.0 * 15;
        double yStart = boundingHeight / 100.0 * 30;

        Rectangle2D rectangle2D;
        Point2D startLinesPrevPoint = null;

        if ((rotateAngle >= 0 && rotateAngle < 90) || (rotateAngle >= 180 && rotateAngle < 270)) {
            if(rotateAngle == 180) {
                rotateAngle = 0;
            }
            rectangle2D = new Rectangle2D.Double(
                    xStart,
                    yStart,
                    boundingWidth / 100.0 * 70,
                    boundingHeight / 100.0 * 45
            );
            startLinesPrevPoint = drawShelf(graphics, xStart, yStart + boundingHeight / 100.0 * 48, boundingWidth / 100.0 * 70);
            int tempRotateAngle = rotateAngle;
            rotateAngle += 180;
            drawShelf(graphics, xStart + boundingWidth / 100.0 * 70, yStart - boundingHeight / 100.0 * 3, boundingWidth / 100.0 * 70);
            rotateAngle = tempRotateAngle;
            Line2D line2D = new Line2D.Double(
                    xStart,
                    yStart + boundingHeight / 100.0 * 48,
                    xStart + boundingWidth / 100.0 * 70,
                    yStart + boundingHeight / 100.0 * 48);

            Line2D line2D2 = new Line2D.Double(
                    xStart,
                    yStart - boundingHeight / 100.0 * 3,
                    xStart + boundingWidth / 100.0 * 70,
                    yStart - boundingHeight / 100.0 * 3);
            graphics.draw(line2D);
            graphics.draw(line2D2);

        } else {

            xStart = boundingWidth / 100.0 * 30;
            yStart = boundingHeight / 100.0 * 15;
            rectangle2D = new Rectangle2D.Double(
                    xStart,
                    yStart,
                    boundingWidth / 100.0 * 45,
                    boundingHeight / 100.0 * 70
            );
            startLinesPrevPoint = drawShelf(graphics, xStart  - boundingHeight / 100.0 * 3, yStart, boundingHeight / 100.0 * 70);
            int tempRotateAngle = rotateAngle;
            rotateAngle += 180;
            drawShelf(graphics, xStart + boundingWidth / 100.0 * 48, yStart + boundingHeight / 100.0 * 70, boundingHeight / 100.0 * 70);
            rotateAngle = tempRotateAngle;

            Line2D line2D = new Line2D.Double(
                    xStart  - boundingHeight / 100.0 * 3,
                    yStart,
                    xStart  - boundingHeight / 100.0 * 3,
                    yStart + boundingHeight / 100.0 * 70);

            Line2D line2D2 = new Line2D.Double(
                    xStart + boundingWidth / 100.0 * 48,
                    yStart,
                    xStart + boundingWidth / 100.0 * 48,
                    yStart + boundingHeight / 100.0 * 70);
            graphics.draw(line2D);
            graphics.draw(line2D2);
            startLinesPrevPoint.setLocation(xStart - 13, yStart - 5);
        }

        graphics.draw(rectangle2D);



        g.setColor(Color.BLUE);
        g.setFont(font);
        g.drawString("O", (int) startLinesPrevPoint.getX() + 2, (int) startLinesPrevPoint.getY());
        g.setColor(Color.BLACK);

        if (componentNode == null) {
            componentNode = new ComponentNode(
                    (int) (boundingWidth / 2.0),
                    (int) (boundingHeight / 2.0),
                    scale,
                    radius);
            add(componentNode);
        }
        componentNode.repaint();
        componentNode.updateDependencies();
    }

    public Point2D drawShelf(Graphics2D graphics, double xStart, double yStart, double lengthOfLine) {
        // This angle specifies that the lines will be drawn parallel to the rotation angle
        double linesStartAlfa = rotateAngle;
        double lengthOfLineDash = 7;
        linesStartAlfa = Math.toRadians(linesStartAlfa);

        // This is the point from which the lines will be drawn (left angle at the base)
        Point2D startDashesPoint = new Point2D.Double(xStart, yStart);
        Point2D startLinesPrevPoint = startDashesPoint;

        double spaceBetween;
        for (int i = 0; i < 8; i++) {
            // first line draws on distanse 0 from start point
            if (i == 0) {
                spaceBetween = 0;
                // Subsequent lines draws with space between
            } else {
                spaceBetween = lengthOfLine / 7;
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
        return startLinesPrevPoint;
    }
}
