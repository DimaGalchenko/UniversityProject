package view.component.impl;

import view.component.Component2D;

import java.awt.*;
import java.awt.geom.Line2D;

public class ConnectionRod extends Component2D {
    protected ComponentNode firstNode;
    protected ComponentNode secondNode;
    public ConnectionRod(ComponentNode firstNode, ComponentNode secondNode) {
        super(0, 0, 0);
        orderNodes(firstNode, secondNode);
        firstNode.addDependency(this);
        secondNode.addDependency(this);
        this.boundingWidth = (int) Math.abs(this.secondNode.getPositionRelatedToParent().getX() - this.firstNode.getPositionRelatedToParent().getX());
        this.boundingHeight = (int) Math.abs(this.secondNode.getPositionRelatedToParent().getY() - this.firstNode.getPositionRelatedToParent().getY());
        setBounds(
                getMinXBetweenNodes(),
                getMinYBetweenNodes(),
                boundingWidth,
                boundingHeight
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
                getMinXBetweenNodes() - 2,
                getMinYBetweenNodes() - 2,
                boundingWidth + 2,
                boundingHeight + 2
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
                    0,
                    boundingHeight,
                    boundingWidth,
                    0
            );
        } else {
            line2D = new Line2D.Double(
                    0,
                    0,
                    boundingWidth ,
                    boundingHeight
            );
        }
        graphics.draw(line2D);

    }
}
