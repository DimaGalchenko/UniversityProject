package view.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DragCopyAdapter<T extends JComponent> extends MouseAdapter {
    private int x;
    private int y;
    private T component;

    public DragCopyAdapter(T component) {
        this.component = component;
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
            JButton button = (JButton) e.getSource();
            TransferHandler handle = button.getTransferHandler();
            Point dragPoint = e.getPoint();
            dragPoint.setLocation(component.getX() + dx, component.getY() + dy);
            System.out.println(dragPoint);
            handle.exportAsDrag(button, e, TransferHandler.COPY);
        }

    }
}
