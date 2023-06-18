package view.component.impl;

import view.component.Component2D;
import view.panel.ConstructionPanel;

import javax.swing.*;

public class DeleteComponentButton extends JButton {
    public DeleteComponentButton(int xPosition, int yPosition, Component2D component2D) {
        super(new ImageIcon("src/main/resources/delete.png"));
        setBounds(xPosition, yPosition, 20, 20);
        addActionListener(e -> {
            ConstructionPanel.getInstance().removeFromBuildSpace(component2D);
        });
    }


}
