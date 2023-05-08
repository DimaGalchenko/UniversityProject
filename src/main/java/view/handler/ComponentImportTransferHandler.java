package view.handler;

import view.component.Component2D;
import view.component.factory.ComponentFactory;
import view.component.factory.ComponentType;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;

public class ComponentImportTransferHandler extends TransferHandler {
    public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;

    public ComponentImportTransferHandler() {
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        boolean accept = false;
        if (canImport(support)) {
            try {
                Transferable t = support.getTransferable();
                Object value = t.getTransferData(SUPPORTED_DATE_FLAVOR);
                if (value instanceof String) {
                    JComponent panel = (JComponent) support.getComponent();
                    if (panel instanceof JPanel) {
                        ComponentType type = ComponentType.valueOf((String) value);
                        Point dropPoint = support.getDropLocation().getDropPoint();
                        dropPoint.setLocation(dropPoint.x - 50, dropPoint.y - 20);
                        Component2D component2D = ComponentFactory.createComponent(type, dropPoint);
                        panel.add(component2D);
                        panel.repaint();
                    }
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        return accept;
    }
}

