package view.component.factory;

import view.component.Component2D;
import view.component.impl.*;

import java.awt.*;

import static view.component.impl.ComponentConstant.COMPONENT_NODE_RADIUS;

public class ComponentFactory {

    public static Component2D createComponent(ComponentType type, Point point) {
        Component2D component2D = null;
        if (type == ComponentType.TRIANGULAR_POST) {
            component2D = new TriangularPost(point.x, point.y);
        } else if (type == ComponentType.SLIDE) {
            component2D = new SlideBlock(point.x, point.y);
        } else if (type == ComponentType.NODE) {
            component2D = new ComponentNode(point.x, point.y, 1, COMPONENT_NODE_RADIUS);
            ((ComponentNode) component2D).activateDrag();
        } else if (type == ComponentType.SHELF) {
            component2D = new Shelf(point.x, point.y);
        } else if (type == ComponentType.PISTON) {
            component2D = new Piston(point.x, point.y);
        }

        return component2D;
    }
}
