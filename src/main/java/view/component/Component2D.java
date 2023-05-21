package view.component;

import lombok.Getter;
import lombok.Setter;
import view.Window;
import view.component.impl.DeleteComponentButton;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

@Getter
@Setter
public abstract class Component2D extends JComponent {

    protected int xPosition;
    protected int yPosition;
    protected double scale;
    protected int boundingWidth = 100;
    protected int boundingHeight = 100;

    private DeleteComponentButton deleteComponentButton;

    protected boolean select = false;

    public Component2D(int xPosition, int yPosition, double scale) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.scale = scale;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;

        if(deleteComponentButton == null) {
            deleteComponentButton = new DeleteComponentButton(boundingWidth - 20, 0, this);
        }
        add(deleteComponentButton);
    }

    protected Point getBoundingBoxCenter() {
        int x = (getXPosition() + getXPosition() + getBoundingWidth()) / 2;
        int y = (getYPosition() + getYPosition() + getBoundingHeight()) / 2;
        return new Point(x, y);
    }

    protected Point2D.Double pointByAngleAndLengthFromDot(Point2D point, double length, double angle) {
        double x = point.getX() + length * Math.cos(angle);
        double y = point.getY() + length * Math.sin(angle);
        return new Point2D.Double(x, y);
    }

    protected void drawLine(Graphics2D g,Point2D startPoint, Point2D endPoint) {
        Line2D line2D = new Line2D.Double(startPoint, endPoint);
        g.draw(line2D);
    }

    public void select() {
        if(!select) {
            setBorder(BorderFactory.createDashedBorder(null, 2, 2));
        } else {
            setBorder(BorderFactory.createEmptyBorder());
        }
        deleteComponentButton.repaint();
        select = !select;
        deleteComponentButton.setVisible(select);

    }

    public void removeSubComponents() {
        remove(deleteComponentButton);
        deleteComponentButton = null;
        revalidate();
        repaint();
    }
}
