package view.component.impl;

import view.component.Component2D;
import view.component.label.ComponentNameHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class ConnectionRod extends JComponent {
    protected ComponentNode firstNode;
    protected ComponentNode secondNode;

    protected final static int PADDING = 15;

    protected final String serialNumber;

    protected int boundingWidth = 100;
    protected int boundingHeight = 100;
    public ConnectionRod(ComponentNode firstNode, ComponentNode secondNode) {

        orderNodes(firstNode, secondNode);
        firstNode.addDependency(this);
        secondNode.addDependency(this);
        this.serialNumber = ComponentNameHelper.generateComponentNameForConnection();
        this.boundingWidth = (int) Math.abs(this.secondNode.getPositionRelatedToParent().getX() - this.firstNode.getPositionRelatedToParent().getX());
        this.boundingHeight = (int) Math.abs(this.secondNode.getPositionRelatedToParent().getY() - this.firstNode.getPositionRelatedToParent().getY());
        setBounds(
                getMinXBetweenNodes(),
                getMinYBetweenNodes() - PADDING,
                boundingWidth,
                boundingHeight + PADDING
        );
    }

    private void orderNodes(ComponentNode firstNode, ComponentNode secondNode) {
        if(secondNode.getPositionRelatedToParent().getX() < firstNode.getPositionRelatedToParent().getX()) {
            this.firstNode = secondNode;
            this.secondNode = firstNode;
        } else {
            this.firstNode = firstNode;
            this.secondNode = secondNode;
        }
    }

    private int getMinXBetweenNodes() {
        return (int) (Math.min(firstNode.getPositionRelatedToParent().getX(), secondNode.getPositionRelatedToParent().getX()));
    }

    private int getMinYBetweenNodes() {
        return (int) (Math.min(firstNode.getPositionRelatedToParent().getY(), secondNode.getPositionRelatedToParent().getY()));
    }

    public void update() {
        orderNodes(firstNode, secondNode);
        this.boundingWidth = (int) Math.abs(this.secondNode.getPositionRelatedToParent().getX() - this.firstNode.getPositionRelatedToParent().getX());
        this.boundingHeight = (int) Math.abs(this.secondNode.getPositionRelatedToParent().getY() - this.firstNode.getPositionRelatedToParent().getY());
        setBounds(
                getMinXBetweenNodes() - PADDING,
                getMinYBetweenNodes() - PADDING,
                boundingWidth + PADDING * 2,
                boundingHeight + PADDING * 2
        );
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Line2D line2D;
        if(firstNode.getPositionRelatedToParent().getY() > secondNode.getPositionRelatedToParent().getY()) {
            line2D = new Line2D.Double(
                    PADDING,
                    boundingHeight + PADDING,
                    boundingWidth + PADDING,
                    PADDING
            );
        } else {
            line2D = new Line2D.Double(
                    PADDING,
                    PADDING,
                    boundingWidth + PADDING,
                    boundingHeight + PADDING
            );
        }

        Font font = new Font("Serif", Font.PLAIN, 19);
        g.setColor(Color.BLUE);
        g.setFont(font);
        graphics.drawString(serialNumber,
                (boundingWidth + PADDING * 2) / 2,
                (int) ((boundingHeight + PADDING * 1.8)  / 2));
        g.setColor(Color.BLACK);

        graphics.draw(line2D);

    }
}
