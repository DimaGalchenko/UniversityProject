package view.component.impl;

import view.component.Component2D;
import view.listeners.MovingAdapter;
import view.listeners.ScaleHandler;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Shelf extends Component2D {

    public Shelf(int xPosition, int yPosition) {
        super(xPosition, yPosition, 1.0);
        setBounds(xPosition, yPosition, 100, 100);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.scale = 1.0;
        MovingAdapter<Shelf> movingAdapter = new MovingAdapter<>(this);
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

        Point2D startDashesPoint = new Point2D.Double(
                this.boundingHeight / 100.0 * 40, // start from 40% of component height
                this.boundingWidth / 100.0 * 5 // start from 5% of component width
        );

        double lengthOfLine = this.boundingWidth / 100.0 * 90;
        double lengthOfLineDash = 10 * scale;
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
                spaceBetween = lengthOfLine / 12;
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
    }
}
