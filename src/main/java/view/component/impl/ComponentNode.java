package view.component.impl;

import lombok.Setter;
import view.component.Component2D;
import view.component.helper.ConnectionHelper;
import view.listeners.MovingAdapter;
import view.listeners.ScaleHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

@Setter
public class ComponentNode extends Component2D {

    private double radius;
    private static final Color SELECTED_COLOR = Color.YELLOW;
    private static final Color NOT_SELECTED_COLOR = Color.black;
    private Color currentColor = Color.black;
    private boolean isSelected;

    private List<ConnectionRod> connectionDependencies = new ArrayList<>();

    public ComponentNode(int xPosition, int yPosition, double scale, double radius) {
        super((int) (xPosition - 1 - radius), (int) (yPosition - 1 - radius), scale);
        this.radius = radius;
        setBounds(
                (int) (xPosition - radius),
                (int) (yPosition - radius),
                (int) (radius * 2) + 4,
                (int) (radius * 2) + 4
        );
        ComponentNodeMouseAdapter componentNodeMouseAdapter = new ComponentNodeMouseAdapter(this);
        addMouseMotionListener(componentNodeMouseAdapter);
        addMouseListener(componentNodeMouseAdapter);
    }

    public void activateDrag() {
        MovingAdapter<ComponentNode> movingAdapter = new MovingAdapter<>(this);
        addMouseMotionListener(movingAdapter);
        addMouseListener(movingAdapter);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Ellipse2D ellipse = new Ellipse2D.Double(1, 1, (radius - 1) * 2, (radius - 1) * 2);
        Ellipse2D innerEllipse = new Ellipse2D.Double(3, 3, (radius - 3) * 2, (radius - 3) * 2);
        double inner2Padding = radius / 100 * 50;
        Ellipse2D inner2Ellipse = new Ellipse2D.Double(
                inner2Padding,
                inner2Padding,
                (radius - inner2Padding) * 2,
                (radius - inner2Padding) * 2);
        double inner3Padding = radius / 100 * 60;
        Ellipse2D inner3Ellipse = new Ellipse2D.Double(
                inner3Padding,
                inner3Padding,
                (radius - inner3Padding) * 2,
                (radius - inner3Padding) * 2);
        graphics.setColor(currentColor);
        graphics.fill(ellipse);
        graphics.setColor(Color.DARK_GRAY);
        graphics.fill(innerEllipse);
        graphics.setColor(currentColor);
        graphics.fill(inner2Ellipse);
        graphics.setColor(Color.DARK_GRAY);
        graphics.fill(inner3Ellipse);
        this.updateDependencies();
    }

    public void addDependency(ConnectionRod connectionRod) {
        if(connectionRod == null) {
            return;
        }
        connectionDependencies.add(connectionRod);
    }

    public void updateDependencies() {
        connectionDependencies.forEach(ConnectionRod::update);
    }

    public Point getPositionRelatedToParent() {
        Point position = new Point();
        if(this.getParent() instanceof Component2D) {
            position.setLocation(
                    ((Component2D) this.getParent()).getXPosition() + getXPosition() + radius + 1,
                    ((Component2D) this.getParent()).getYPosition() + getYPosition() + radius + 1);
            return position;
        }
        position.setLocation(getXPosition() + radius, getYPosition() + radius);
        return position;
    }

    public void select() {
        this.isSelected = !this.isSelected;
        if(isSelected) {
            setSelectedColor();
            ConnectionHelper.addNode(this);
        } else {
            setNotSelectedColor();
            ConnectionHelper.removeNode(this);
        }
    }

    public void setSelectedColor() {
        setCurrentColor(SELECTED_COLOR);
    }

    public void setNotSelectedColor() {
        setCurrentColor(NOT_SELECTED_COLOR);
    }

    static class ComponentNodeMouseAdapter extends MouseAdapter {
        private final ComponentNode component;

        public ComponentNodeMouseAdapter(ComponentNode component) {
            this.component = component;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            component.select();
        }
    }
}
