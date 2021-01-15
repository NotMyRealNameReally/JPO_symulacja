package view;

import java.awt.*;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(WorldPanel worldPanel, ControlPanel controlPanel, LoggingPanel loggingPanel, MenuBar menuBar) {
        super("Super Symulator");
        setLayout(new BorderLayout());
        add(new JScrollPane(loggingPanel), BorderLayout.EAST);
        add(new JScrollPane(worldPanel), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
