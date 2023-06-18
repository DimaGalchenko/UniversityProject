package view.panel;

import lombok.Getter;
import view.bar.ComponentsBar;
import view.component.Component2D;
import view.component.helper.ConnectionHelper;
import view.component.helper.ConnectionType;
import view.component.helper.SelectHelper;
import view.handler.ComponentImportTransferHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
public class ConstructionPanel extends JPanel {

    private static ConstructionPanel instance;
    private final JPanel buildSpace;

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
        // Connection button
        JButton createConnectionButton = new JButton(new ImageIcon("src/main/resources/connection-rod.png"));
        createConnectionButton.setPreferredSize(new Dimension(50,50));
        createConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectionHelper.createConnection(ConnectionType.CONNECTION_ROD);
            }
        });
        toolBar.add(createConnectionButton);
        // Crank button
        JButton createCrankButton = new JButton(new ImageIcon("src/main/resources/crank.png"));
        createCrankButton.setPreferredSize(new Dimension(50,50));
        createCrankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e);
                ConnectionHelper.createConnection(ConnectionType.CRANK);
            }
        });
        toolBar.add(createCrankButton);
        // Delete button
        JButton deleteButton = new JButton(new ImageIcon("src/main/resources/delete.png"));
        deleteButton.setPreferredSize(new Dimension(50,50));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e);
                if(SelectHelper.getComponent2D() != null) {
                    ConstructionPanel.getInstance().removeFromBuildSpace(SelectHelper.getComponent2D());
                }
            }
        });
        toolBar.add(deleteButton);

        JButton rotateButton = new JButton(new ImageIcon("src/main/resources/rotate.png"));
        rotateButton.setPreferredSize(new Dimension(50,50));
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(e);
                if(SelectHelper.getComponent2D() != null) {
                    SelectHelper.getComponent2D().setRotateAngle(SelectHelper.getComponent2D().getRotateAngle() + 90);
                    SelectHelper.getComponent2D().repaint();
                }
            }
        });
        toolBar.add(rotateButton);

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
            ComponentsBar.buttons.get(0).doClick();
        }
    }
}
