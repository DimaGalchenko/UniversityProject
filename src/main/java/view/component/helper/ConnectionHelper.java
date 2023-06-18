package view.component.helper;

import view.Window;
import view.bar.ComponentsBar;
import view.component.impl.ComponentNode;
import view.component.impl.ConnectionRod;
import view.component.impl.Crank;
import view.panel.ConstructionPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHelper {

    private static ConnectionHelper instance;
    private static List<ComponentNode> componentNodes = new ArrayList<>();

    public static ConnectionHelper getInstance() {
        if(instance == null) {
            instance = new ConnectionHelper();
            return instance;
        }
        return instance;
    }

    private ConnectionHelper() {
        componentNodes = new ArrayList<>();
    }

    public static void addNode(ComponentNode componentNode) {
        componentNodes.add(componentNode);
    }

    public static void removeNode(ComponentNode componentNode) {
        componentNodes.remove(componentNode);
    }

    public static void createConnection(ConnectionType connectionType) {
        if(componentNodes.size() != 2) {
            JOptionPane.showMessageDialog(
                    ConstructionPanel.getInstance().getBuildSpace(),
                    "Please choose 2 nodes. You choose: " + componentNodes.size(),
                    "Invalid count of nodes",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComponent component2D = null;

        if(connectionType == ConnectionType.CONNECTION_ROD) {
            component2D = new ConnectionRod(
                    componentNodes.get(0),
                    componentNodes.get(1)
            );
        } else if (connectionType == ConnectionType.CRANK) {
            component2D = new Crank(
                    componentNodes.get(0),
                    componentNodes.get(1)
            );
        }
        System.out.println("Create connection: " + connectionType + ", " + component2D);

        if (component2D != null) {
            ConstructionPanel.getInstance().getBuildSpace().add(component2D);
            ConstructionPanel.getInstance().getBuildSpace().repaint();
            Window.getInstance().repaint();
            component2D.repaint();
            componentNodes.forEach(ComponentNode::setNotSelectedColor);
            componentNodes = new ArrayList<>();
        }
    }
}
