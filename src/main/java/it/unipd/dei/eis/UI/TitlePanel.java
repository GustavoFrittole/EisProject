package it.unipd.dei.eis.UI;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    public TitlePanel(){
        super();
        setPreferredSize(new Dimension(100, 80));
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel labelTitle = new JLabel();
        labelTitle.setText("EIS Project");
        labelTitle.setFont(new Font("Arial Black", Font.BOLD,  30));
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        labelTitle.setPreferredSize(new Dimension(330, 100));
        add(labelTitle, BorderLayout.WEST);
    }
}
