package it.unipd.dei.eis.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel implements ActionListener{

    private JCheckBox saveCheck;
    private JCheckBox guardianSourceCheck;
    private JCheckBox analyzeCheck;
    private JCheckBox csvSourceCheck;
    private JButton csvFileChooserButton;
    private JFileChooser csvFileChooser;
    private JButton confFileChooserButton;
    private JFileChooser confFileChooser;
    private JTextField queryText;
    private JButton startButton;
    private MainFrame mainFrame;
    private String csvFilePath;
    private String confFilePath;
    public OptionsPanel(MainFrame mainFrame){
        super();

        this.mainFrame = mainFrame;

        setLayout(null);
        setPreferredSize(new Dimension(700, 300));
        setOpaque(false);

        JLabel selectSteps = new JLabel("Select steps to perform");
        selectSteps.setBounds(20,10, 400, 20 );
        selectSteps.setFont(new Font("Arial Black", Font.BOLD,  18));
        add(selectSteps);

        JLabel selectSources = new JLabel("Select sources");
        selectSources.setBounds(20,120, 400, 20 );
        selectSources.setFont(new Font("Arial Black", Font.BOLD,  18));
        add(selectSources);

        saveCheck = new JCheckBox("Fetch and save");
        analyzeCheck = new JCheckBox("Load and analyze");
        saveCheck.setOpaque(false);
        analyzeCheck.setOpaque(false);
        saveCheck.setFocusable(false);
        analyzeCheck.setFocusable(false);
        saveCheck.setBounds(30, 45,160,30);
        analyzeCheck.setBounds(30, 75,160,30);
        saveCheck.addActionListener(this);
        add(saveCheck);
        add(analyzeCheck);

        guardianSourceCheck = new JCheckBox("The Guardian API");
        csvSourceCheck = new JCheckBox("NYT CSV");
        guardianSourceCheck.setEnabled(false);
        csvSourceCheck.setEnabled(false);
        guardianSourceCheck.setOpaque(false);
        csvSourceCheck.setOpaque(false);
        guardianSourceCheck.setFocusable(false);
        csvSourceCheck.setFocusable(false);
        guardianSourceCheck.setBounds(30, 45+110,160,30);
        csvSourceCheck.setBounds(30, 75+110,160,30);
        guardianSourceCheck.addActionListener(this);
        add(guardianSourceCheck);
        add(csvSourceCheck);
        
        queryText = new JTextField("Your query");
        queryText.setBounds(30+180, 45+110,200,25);
        queryText.setEnabled(false);
        queryText.setOpaque(false);
        queryText.setFont(new Font("Arial Black", Font.BOLD,  12));
        queryText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        queryText.setDisabledTextColor(Color.gray);
        add(queryText);

        csvFileChooserButton = new JButton(".csv File");
        csvFileChooserButton.setBounds(30+180, 75+110,200,30);
        csvFileChooserButton.addActionListener(this);
        csvFileChooserButton.setOpaque(false);
        csvFileChooserButton.setEnabled(false);
        csvFileChooserButton.setFocusable(false);
        csvFileChooserButton.setFont(new Font("Arial Black", Font.BOLD,  12));
        csvFileChooserButton.setBackground(Color.blue);
        add(csvFileChooserButton);

        confFileChooserButton = new JButton("Config File");
        confFileChooserButton.setBounds(30+440, 45+110,180,30);
        confFileChooserButton.addActionListener(this);
        confFileChooserButton.setOpaque(false);
        confFileChooserButton.setFocusable(false);
        confFileChooserButton.setFont(new Font("Arial Black", Font.BOLD,  12));
        confFileChooserButton.setBackground(Color.blue);
        add(confFileChooserButton);

        startButton = new JButton("Start");
        startButton.setBounds(30+440, 75+110,180,30);
        startButton.addActionListener(this);
        startButton.setBackground(Color.decode("#815ccc"));
        startButton.setFocusable(false);
        startButton.setFont(new Font("Arial Black", Font.BOLD,  12));
        add(startButton);

        csvFileChooser = new JFileChooser();

        confFileChooser = new JFileChooser();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveCheck){
            guardianSourceCheck.setEnabled(saveCheck.isSelected());
            csvSourceCheck.setEnabled(saveCheck.isSelected());
            queryText.setEnabled(saveCheck.isSelected());
            csvFileChooserButton.setEnabled(saveCheck.isSelected());

        }else if(e.getSource() == csvFileChooserButton)
        {
            if(csvFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                csvFilePath = csvFileChooser.getSelectedFile().getAbsolutePath();
        }else if(e.getSource() == confFileChooserButton)
        {
            if(confFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                confFilePath = confFileChooser.getSelectedFile().getAbsolutePath();
        }else if(e.getSource() == startButton){
            startButton.setEnabled(false);
            mainFrame.startTask(new Options(
                    csvFilePath,
                    confFilePath,
                    queryText.getText(),
                    guardianSourceCheck.isSelected(),
                    csvSourceCheck.isSelected(),
                    saveCheck.isSelected(),
                    analyzeCheck.isSelected()
            ));
        }
    }
    public void setStartButtonActive(){
        startButton.setEnabled(true);
    }
}
