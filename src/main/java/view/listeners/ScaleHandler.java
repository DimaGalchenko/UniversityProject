package view.listeners;

import view.component.Component2D;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ScaleHandler<T extends Component2D> implements MouseWheelListener {

    private T component;

    public ScaleHandler(T component) {
        this.component = component;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {

        int x = e.getX();
        int y = e.getY();

        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

            if (component.getBounds().getBounds2D().contains(x, y)) {
                float amount = e.getWheelRotation() * 5f;
                component.setScale(component.getScale() + e.getWheelRotation() * 0.1);
                component.setBoundingHeight(component.getBoundingHeight() + e.getWheelRotation() * 5);
                component.setBoundingWidth(component.getBoundingWidth() + e.getWheelRotation() * 5);
                component.repaint();
            }
        }
    }
}
