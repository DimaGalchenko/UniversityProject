package view.component.impl;

import view.component.Component2D;
import view.listeners.MovingAdapter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SlideBlock extends Component2D {

    private ComponentNode componentNode;

    public SlideBlock(int xPosition, int yPosition) {
        super(xPosition, yPosition, 1.0);
        MovingAdapter<SlideBlock> movingAdapter = new MovingAdapter<>(this);
        addMouseMotionListener(movingAdapter);
        addMouseListener(movingAdapter);
        this.boundingWidth = 100;
        this.boundingHeight = 100;
        setBounds(xPosition, yPosition, boundingWidth, boundingHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        double radius = 6 * scale;

        double xStart = boundingWidth / 100.0 * 10;
        double yStart = boundingHeight / 100.0 * 30;

        Rectangle2D rectangle2D = new Rectangle2D.Double(
                xStart,
                yStart,
                boundingWidth / 100.0 * 80,
                boundingHeight / 100.0 * 40
        );
        graphics.draw(rectangle2D);

        Point2D startDashesPoint = new Point2D.Double(
                xStart, // start from 40% of component height
                yStart + boundingHeight / 100.0 * 40 + boundingHeight / 100.0 * 5 // start from 5% of component width
        );

        double lengthOfLine = boundingWidth / 100.0 * 80;
        double lengthOfLineDash = 5 * scale;
        double rotateAngle = 0;

        Point2D startLinesPrevPoint = startDashesPoint;

        Line2D line2D = new Line2D.Double(
                startDashesPoint.getX(),
                startDashesPoint.getY(),
                startDashesPoint.getX() + lengthOfLine,
                startDashesPoint.getY());
        graphics.draw(line2D);

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
            double startLinesX = startLinesPrevPoint.getX() + spaceBetween * Math.cos(rotateAngle);
            double startLinesY = startLinesPrevPoint.getY() + spaceBetween  * Math.sin(rotateAngle);
            // Calculate end point of line
            double endLineX = startLinesX + lengthOfLineDash * Math.cos(rotateAngle + 120);
            double endLineY = startLinesY + lengthOfLineDash * Math.sin(rotateAngle + 120);
            drawLine(
                    graphics,
                    new Point2D.Double(startLinesX, startLinesY),
                    new Point2D.Double(endLineX, endLineY)
            );

            startLinesPrevPoint = new Point2D.Double(startLinesX, startLinesY);
        }

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
}
