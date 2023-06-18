package view.component.impl;

import view.component.util.DrawUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Crank extends ConnectionRod {

    public Crank(ComponentNode firstNode, ComponentNode secondNode) {
        super(firstNode, secondNode);
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Line2D line2D;
        if(firstNode.getPositionRelatedToParent().getY() > secondNode.getPositionRelatedToParent().getY()) {
            line2D = new Line2D.Double(
                    PADDING,
                    boundingHeight + PADDING,
                    boundingWidth + PADDING,
                    PADDING
            );
        } else {
            line2D = new Line2D.Double(
                    PADDING,
                    PADDING,
                    boundingWidth + PADDING,
                    boundingHeight + PADDING
            );
        }

        Font font = new Font("Serif", Font.PLAIN, 19);
        g.setColor(Color.BLUE);
        g.setFont(font);
        graphics.drawString(serialNumber,
                (boundingWidth + PADDING * 2 + 13) / 2,
                (int) ((boundingHeight + PADDING * 2)  / 2));
        g.setColor(Color.BLACK);

        try {
            BufferedImage img = ImageIO.read(new File("src/main/resources/refresh.png"));

            graphics.drawImage(img, (boundingWidth + 5) / 2, (int) ((boundingHeight + 5)  / 2), null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        graphics.draw(line2D);
    }
}

