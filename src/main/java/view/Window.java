package view;


import view.bar.ComponentsBar;
import view.component.impl.TriangularPost;
import view.listeners.panel.ConstructionPanel;

import javax.swing.*;
import java.awt.*;

public class Window{

    public static JFrame frame;

    public static JFrame getInstance() {
        if(frame == null) {
            initializeFrame();
            return frame;
        }
        return frame;
    }

    private static void initializeFrame() {
        frame = new JFrame();
        frame.setSize(800, 800);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TriangularPost triangularPost = new TriangularPost(50, 50);
        triangularPost.setDoubleBuffered(true);
        triangularPost.setBounds(50, 50, 100, 100);
        frame.setLayout(new BorderLayout());

        ConstructionPanel constructionPanel = ConstructionPanel.getInstance();
        //constructionPanel.setLayout(null);
//        constructionPanel.add(triangularPost);
//        constructionPanel.add(slideBlock);
        frame.add(constructionPanel, BorderLayout.CENTER);

        ComponentsBar componentsBar = new ComponentsBar();
        frame.add(componentsBar, BorderLayout.WEST);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


}
