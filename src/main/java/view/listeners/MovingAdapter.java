package view.listeners;

import view.component.Component2D;
import view.component.impl.DeleteComponentButton;

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
            component.select();
        } else {
            component.select();
        }
    }

    public void mousePressed(MouseEvent e) {
        x = e.getXOnScreen();
        y = e.getYOnScreen();
    }

    public void mouseDragged(MouseEvent e) {
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
