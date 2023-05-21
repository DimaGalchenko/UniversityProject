package view.component.impl;

import view.component.Component2D;
import view.listeners.panel.ConstructionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DeleteComponentButton extends JButton {
    public DeleteComponentButton(int xPosition, int yPosition, Component2D component2D) {
        super(new ImageIcon("src/main/resources/delete.png"));
        setBounds(xPosition, yPosition, 20, 20);
        addActionListener(e -> {
            System.out.println(e.getActionCommand());
            ConstructionPanel.getInstance().removeFromBuildSpace(component2D);
        });
    }


}
