package view.listeners.panel;

import lombok.Getter;
import view.component.Component2D;
import view.component.helper.ConnectionHelper;
import view.component.helper.ConnectionType;
import view.component.impl.DeleteComponentButton;
import view.handler.ComponentImportTransferHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

@Getter
public class ConstructionPanel extends JPanel {

    private static ConstructionPanel instance;
    private JPanel buildSpace;

    public static ConstructionPanel getInstance() {
        if(instance == null) {
            instance = new ConstructionPanel();
        }
        return instance;
    }
    private ConstructionPanel() {
        setLayout(new BorderLayout());
        JPanel toolBar = new JPanel();
        toolBar.setPreferredSize(new Dimension(0, 55));
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton button = new JButton(new ImageIcon("src/main/resources/connection-rod.png"));
        button.setPreferredSize(new Dimension(50,50));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                ConnectionHelper.createConnection(ConnectionType.CONNECTION_ROD);
            }
        });
        toolBar.add(button);
        JButton button1 = new JButton(new ImageIcon("src/main/resources/crank.png"));
        button1.setPreferredSize(new Dimension(50,50));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ConnectionHelper.createConnection(ConnectionType.CRANK);
            }
        });
        toolBar.add(button1);

        this.buildSpace = new JPanel();
        buildSpace.setPreferredSize(new Dimension(0, 400));
        buildSpace.setTransferHandler(new ComponentImportTransferHandler());
        buildSpace.setLayout(null);

        add(toolBar, BorderLayout.NORTH);
        add(buildSpace, BorderLayout.CENTER);
    }

    public void removeFromBuildSpace(Component2D component) {
        if(instance != null) {
            component.removeSubComponents();
            buildSpace.remove(component);
            buildSpace.revalidate();
            buildSpace.repaint();
            System.out.println(Arrays.toString(buildSpace.getComponents()));
        }
    }
}
