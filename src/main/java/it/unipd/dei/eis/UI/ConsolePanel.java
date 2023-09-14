package it.unipd.dei.eis.UI;

import javax.swing.*;
import java.awt.*;

public class ConsolePanel extends JPanel {
    private JTextArea consoleOut;
    public ConsolePanel(){
        super();
        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(700, 260));

        consoleOut = new JTextArea();
        consoleOut.setEditable(false);
        consoleOut.setBackground(Color.decode("#b5a5d1"));
        JScrollPane jScrollPane = new JScrollPane(consoleOut);

        jScrollPane.setBounds(10,25,660, 220);
        jScrollPane.setBackground(Color.decode("#b5a5d1"));

        add(jScrollPane);

    }

    public void log(String string){
        consoleOut.append(string + "\n");
    }
}
