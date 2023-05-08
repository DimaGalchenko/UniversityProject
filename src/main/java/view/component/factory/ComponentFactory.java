package view.component.factory;

import view.component.Component2D;
import view.component.impl.ComponentNode;
import view.component.impl.Shelf;
import view.component.impl.SlideBlock;
import view.component.impl.TriangularPost;

import java.awt.*;

public class ComponentFactory {

    public static Component2D createComponent(ComponentType type, Point point) {
        Component2D component2D = null;
        if (type == ComponentType.TRIANGULAR_POST) {
            component2D = new TriangularPost(point.x, point.y);
        } else if (type == ComponentType.SLIDE) {
            component2D = new SlideBlock(point.x, point.y);
        } else if (type == ComponentType.NODE) {
            component2D = new ComponentNode(point.x, point.y, 1, 10);
            ((ComponentNode) component2D).activateDrag();
        } else if (type == ComponentType.SHELF) {
            component2D = new Shelf(point.x, point.y);
        } else if (type == ComponentType.PISTON) {
            component2D = new SlideBlock(point.x, point.y);
        }

        return component2D;
    }
}
