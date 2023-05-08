package view.bar;

import view.component.factory.ComponentType;
import view.handler.ComponentExportTransferHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponentsBar extends JPanel {

    private static final String[][] IMAGE_SRC_LIST = new String[][]{
            {"triangular-post.png", "post.png"},
            {"component-node.png"},
            {"piston.png", "slide.png"}
    };

    private static final ComponentType[][] COMPONENT_TYPES = new ComponentType[][]{
            {ComponentType.TRIANGULAR_POST, ComponentType.SHELF},
            {ComponentType.NODE},
            {ComponentType.PISTON, ComponentType.SLIDE}
    };

    public ComponentsBar() {
        setPreferredSize(new Dimension(80, 100));
        setBackground(Color.decode("#e1e0df"));
        for (int i = 0; i < IMAGE_SRC_LIST.length; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(IMAGE_SRC_LIST[i].length, 1, 1, 2));
            JScrollPane scrollPane = new JScrollPane(panel);
            for (int j = 0; j < IMAGE_SRC_LIST[i].length; j++) {
                JButton button = new JButton(new ImageIcon("src/main/resources/" + IMAGE_SRC_LIST[i][j]));
                panel.add(button);
                button.setPreferredSize(new Dimension(60, 60));
                button.setTransferHandler(new ComponentExportTransferHandler(COMPONENT_TYPES[i][j].name()));
                button.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        JButton button = (JButton) e.getSource();
                        TransferHandler handle = button.getTransferHandler();
                        handle.exportAsDrag(button, e, TransferHandler.COPY);
                    }
                });
            }
            add(scrollPane);
        }
    }
}
