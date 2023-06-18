package view.component.helper;

import view.component.Component2D;

public abstract class SelectHelper {

    public static Component2D component2D;

    public static Component2D getComponent2D() {
        return component2D;
    }

    public static void setComponent2D(Component2D component2D) {
        SelectHelper.component2D = component2D;
    }
}
