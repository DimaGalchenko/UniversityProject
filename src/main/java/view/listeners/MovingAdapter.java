package view.listeners;

import view.component.Component2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MovingAdapter<T extends Component2D> extends MouseAdapter {

    private int x;
    private int y;
    private T component;

    private boolean isSelected;

    public MovingAdapter(T component) {
        this.component = component;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        isSelected = !isSelected;
        if(isSelected) {
            component.setBorder(BorderFactory.createDashedBorder(null, 2, 2));
        } else {
            component.setBorder(BorderFactory.createEmptyBorder());
        }
    }

    public void mousePressed(MouseEvent e) {
        if(!isSelected) {
            mouseClicked(e);
        }
        x = e.getXOnScreen();
        y = e.getYOnScreen();
    }

    public void mouseDragged(MouseEvent e) {
        if(!isSelected) {
            mouseClicked(e);
        }
        Point point = e.getLocationOnScreen();
        int dx = (int) (point.getX() - x);
        int dy = (int) (point.getY() - y);

        if (dx != 0 || dy != 0) {
            component.setXPosition(component.getXPosition() + dx);
            component.setYPosition(component.getYPosition() + dy);
            component.setLocation(component.getXPosition(), component.getYPosition());
            component.repaint();
            x += dx;
            y += dy;
        }
    }
}
