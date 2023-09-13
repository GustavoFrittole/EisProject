package it.unipd.dei.eis.UI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private TitlePanel titlePanel;
    private OptionsPanel optionsPanel;
    private ConsolePanel consolePanel;
    private Controller controller;
    public MainFrame(Controller controller){
        super("EIS Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.decode("#9a7fc9"));
        setLayout(new BorderLayout());
        this.controller = controller;

        titlePanel = new TitlePanel();
        add(titlePanel, BorderLayout.PAGE_START);

        optionsPanel = new OptionsPanel(this);
        add(optionsPanel, BorderLayout.WEST);

        consolePanel = new ConsolePanel();
        add(consolePanel, BorderLayout.SOUTH);
    }


    public void logToTextArea(String str){
        consolePanel.log(str);
    }

    public void startTask(Options options) {
        controller.useModel(options);
        optionsPanel.setStartButtonActive();
    }
}
