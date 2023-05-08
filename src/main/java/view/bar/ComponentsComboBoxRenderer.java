package view.bar;

import view.component.factory.ComponentType;
import view.handler.ComponentExportTransferHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponentsComboBoxRenderer extends JLabel
        implements ListCellRenderer {
    private Font uhOhFont;

    public ComponentsComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        //Get the selected index. (The index param isn't
        //always valid, so just use the value.)
        int selectedIndex = ((Integer) value).intValue();

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Set the icon and text.  If icon was null, say so.
        ImageIcon icon = new ImageIcon("src/main/resources/" + "piston.png");
        JButton button = new JButton(new ImageIcon("src/main/resources/" + "piston.png"));

        setTransferHandler(new ComponentExportTransferHandler(ComponentType.PISTON.name()));
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                TransferHandler handle = button.getTransferHandler();
                System.out.println(e.getPoint());
                handle.exportAsDrag(button, e, TransferHandler.COPY);

            }
        });

        add(button);
        setIcon(icon);
//            if (icon != null) {
//                setText(pet);
//                setFont(list.getFont());
//            } else {
//                setUhOhText(pet + " (no image available)",
//                        list.getFont());
//            }

        return this;
    }

    //Set the font and text when no image was found.
    protected void setUhOhText(String uhOhText, Font normalFont) {
        if (uhOhFont == null) { //lazily create this font
            uhOhFont = normalFont.deriveFont(Font.ITALIC);
        }
        setFont(uhOhFont);
        setText(uhOhText);
    }
}

