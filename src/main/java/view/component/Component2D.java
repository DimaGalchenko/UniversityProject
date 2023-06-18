package view.component;

import lombok.Getter;
import lombok.Setter;
import view.component.helper.SelectHelper;
import view.component.impl.ComponentNode;
import view.component.impl.DeleteComponentButton;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Component2D extends AbstractButton {

    protected int xPosition;
    protected int yPosition;
    protected double scale;
    protected int boundingWidth = 100;
    protected int boundingHeight = 100;
    protected int rotateAngle = 0;
    private static final Color selectedColor = Color.magenta;
    private static final Color notSelectedColor = Color.BLACK;
    protected Color currentColor = notSelectedColor;

    private DeleteComponentButton deleteComponentButton;

    protected List<JComponent> subComponents = new ArrayList<>();

    protected boolean select = false;

    public Component2D(int xPosition, int yPosition, double scale) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.scale = scale;
    }

    public Component2D(int xPosition, int yPosition, double scale, int rotateAngle) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.scale = scale;
        this.rotateAngle = rotateAngle;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        if(!(this instanceof ComponentNode)) {
            g.setColor(currentColor);
        }
//        if(deleteComponentButton == null) {
//            deleteComponentButton = new DeleteComponentButton(boundingWidth - 20, 0, this);
//            subComponents.add(deleteComponentButton);
//            add(deleteComponentButton);
//        }
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
            //setBorder(BorderFactory.createDashedBorder(null, 2, 2));
            this.currentColor = selectedColor;
            SelectHelper.setComponent2D(this);
        } else {
            this.currentColor = notSelectedColor;
            //setBorder(BorderFactory.createEmptyBorder());
        }
//        if(deleteComponentButton != null) {
//            deleteComponentButton.setVisible(!select);
//            deleteComponentButton.repaint();
//        }
        this.repaint();
        select = !select;
    }

    public void removeSubComponents() {
        System.out.println(subComponents);
        setBounds(xPosition, yPosition, 0, 0);
        for(JComponent component: subComponents) {
            remove(component);
            this.revalidate();
            this.repaint();
        }
    }

    public void setRotateAngle(int rotateAngle) {
        if(rotateAngle == 360) {
            this.rotateAngle = 0;
            return;
        }
        this.rotateAngle = rotateAngle;
    }

    public int getRotateAngle() {
        return rotateAngle;
    }
}
