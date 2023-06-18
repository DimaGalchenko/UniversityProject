package view.component.impl;

import lombok.Setter;
import view.component.Component2D;
import view.component.helper.ConnectionHelper;
import view.component.label.ComponentNameHelper;
import view.listeners.MovingAdapter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

@Setter
public class ComponentNode extends Component2D {

    private double radius;
    private static final Color SELECTED_COLOR = Color.RED;
    private static final Color NOT_SELECTED_COLOR = Color.black;
    private Color currentColor = Color.black;
    private boolean isSelected;

    private final String serialName;

    private List<ConnectionRod> connectionDependencies = new ArrayList<>();

    public ComponentNode(int xPosition, int yPosition, double scale, double radius) {
        super((int) (xPosition - (radius * 3)), (int) (yPosition - (radius * 3)), scale);
        this.radius = radius;
        this.boundingWidth = (int) (radius * 6);
        this.boundingHeight = (int) (radius * 6);

        setBounds(
                (int) (xPosition - (radius * 3)),
                (int) (yPosition - (radius * 3)),
                boundingWidth,
                boundingHeight
        );
        this.serialName = ComponentNameHelper.generateSerialNameForNode();
        ComponentNodeMouseAdapter componentNodeMouseAdapter = new ComponentNodeMouseAdapter(this);
  //      addMouseMotionListener(componentNodeMouseAdapter);
        addMouseListener(componentNodeMouseAdapter);
//        MouseAdapter actionListener1 = new ActionList(this);
//        addMouseListener(actionListener1);
    }

    public ComponentNode(int xPosition, int yPosition, double scale, double radius, String serialName) {
        super((int) (xPosition - (radius * 3)), (int) (yPosition - (radius * 3)), scale);
        this.radius = radius;
        this.boundingWidth = (int) (radius * 6);
        this.boundingHeight = (int) (radius * 6);

        setBounds(
                (int) (xPosition - (radius * 3)),
                (int) (yPosition - (radius * 3)),
                boundingWidth,
                boundingHeight
        );
        this.serialName = serialName;
        ComponentNodeMouseAdapter componentNodeMouseAdapter = new ComponentNodeMouseAdapter(this);
        //addMouseMotionListener(componentNodeMouseAdapter);
        addMouseListener(componentNodeMouseAdapter);
//        MouseAdapter actionListener1 = new ActionList(this);
//        addMouseListener(actionListener1);
//        addMouseMotionListener(actionListener1);
    }

    public void activateDrag() {
        MovingAdapter<ComponentNode> movingAdapter = new MovingAdapter<>(this);
        addMouseMotionListener(movingAdapter);
        addMouseListener(movingAdapter);
    }

    public void setBounds() {
        setBounds(
                (int) (xPosition - (radius * 3)),
                (int) (yPosition - (radius * 3)),
                boundingWidth,
                boundingHeight
        );
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font font = new Font("Serif", Font.PLAIN, 16);
        g.setColor(Color.BLUE);
        g.setFont(font);
        graphics.drawString(serialName, (int) (radius / 6), (int) (radius * 1.5));

        g.setColor(Color.BLACK);

        double ellipsePadding = radius * 1.75;
        double ellipseDiameter = radius * 2.5;
        Ellipse2D ellipse = new Ellipse2D.Double(ellipsePadding, ellipsePadding, ellipseDiameter, ellipseDiameter);

        double padding = ellipsePadding / 100 * 15;
        ellipsePadding = ellipsePadding + padding;
        ellipseDiameter = ellipseDiameter - (padding * 2);
        Ellipse2D innerEllipse = new Ellipse2D.Double(
                ellipsePadding,
                ellipsePadding,
                ellipseDiameter,
                ellipseDiameter
        );

        padding = ellipsePadding / 100 * 23;
        ellipsePadding = ellipsePadding + padding;
        ellipseDiameter = ellipseDiameter - (padding * 2);
        Ellipse2D inner2Ellipse = new Ellipse2D.Double(
                ellipsePadding,
                ellipsePadding,
                ellipseDiameter,
                ellipseDiameter
        );
        padding = ellipsePadding / 100 * 10;
        ellipsePadding = ellipsePadding + padding;
        ellipseDiameter = ellipseDiameter - (padding * 2);
        Ellipse2D inner3Ellipse = new Ellipse2D.Double(
                ellipsePadding,
                ellipsePadding,
                ellipseDiameter,
                ellipseDiameter
        );
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
        if (connectionRod == null) {
            return;
        }
        connectionDependencies.add(connectionRod);
    }

    public void updateDependencies() {
        connectionDependencies.forEach(ConnectionRod::update);
    }

    public Point getPositionRelatedToParent() {
        Point position = new Point();
        if (this.getParent() instanceof TriangularPost) {
            position.setLocation(
                    ((Component2D) this.getParent()).getXPosition() + getXPosition(),
                    ((Component2D) this.getParent()).getYPosition() + getYPosition()
            );
            return position;
        }
        if (this.getParent() instanceof Component2D) {
            position.setLocation(
                    ((Component2D) this.getParent()).getXPosition() + getXPosition() + radius * 3,
                    ((Component2D) this.getParent()).getYPosition() + getYPosition() + radius * 3
            );
            return position;
        }
        position.setLocation(getXPosition() + radius * 3, getYPosition() + radius * 3);
        return position;
    }

    public void selectNode() {
        this.isSelected = !this.isSelected;
        if (isSelected) {
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
        private int x;
        private int y;

        public ComponentNodeMouseAdapter(ComponentNode component) {
            this.component = component;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON2) {
                component.selectNode();
                return;
            }
            if(component.getParent() instanceof Component2D && e.getButton() == MouseEvent.BUTTON1) {
                ((Component2D) component.getParent()).select();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getXOnScreen();
            y = e.getYOnScreen();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point point = e.getLocationOnScreen();
            int dx = (int) (point.getX() - x);
            int dy = (int) (point.getY() - y);

            if ((Math.abs(dx) <= 5 && Math.abs(dx) >= 2) || (Math.abs(dy) <= 5 && Math.abs(dy) >= 2)) {
                mouseClicked(e);
            }
        }
    }
}
