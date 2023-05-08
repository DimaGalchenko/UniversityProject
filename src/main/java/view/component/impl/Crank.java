package view.component.impl;

import geometry.line.LineUtil;
import view.component.util.DrawUtil;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Crank extends ConnectionRod {

    public Crank(ComponentNode firstNode, ComponentNode secondNode) {
        super(firstNode, secondNode);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D) g;

        Point2D crankCenter;
        Point2D firstPoint;
        Point2D secondPoint;
        int direction = 0;

        if (firstNode.getPositionRelatedToParent().getY() > secondNode.getPositionRelatedToParent().getY()) {
            firstPoint = new Point2D.Double(0, boundingHeight);
            secondPoint = new Point2D.Double(boundingWidth, 0);
            direction = 1;
        } else {
            firstPoint = new Point2D.Double(0, 0);
            secondPoint = new Point2D.Double(boundingWidth, boundingHeight);
        }

        drawAngularVelocityVector(graphics2D, firstPoint, secondPoint, direction);
    }

    private void drawAngularVelocityVector(Graphics2D graphics2D, Point2D point1, Point2D point2, int direction) {
        if(direction == 0) {
            DrawUtil.drawArrow(graphics2D, point1.getX(), point1.getY(), point2.getX(), point2.getY());
        } else {
            DrawUtil.drawArrow(graphics2D, point2.getX(), point2.getY(), point1.getX(), point1.getY());
        }

    }
}

