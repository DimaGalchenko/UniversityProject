package view.component.impl;

import view.component.Component2D;

import javax.swing.*;
import java.awt.*;

public class DeleteComponentButton extends Component2D {
    public DeleteComponentButton(int xPosition, int yPosition, double scale) {
        super(xPosition, yPosition, scale);
        setBounds(0, 0, 100, 100);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        ImageIcon icon = new ImageIcon("src/main/resources/delete.png");

        icon.paintIcon(this, g, 0, 0);
        graphics.setBackground(Color.gray);
    }
}
