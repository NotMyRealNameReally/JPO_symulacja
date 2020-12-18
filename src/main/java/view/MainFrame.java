package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {

    public MainFrame(WorldPanel worldPanel, ControlPanel controlPanel, LoggingPanel loggingPanel) {
        super("Super Symulator");
        setLayout(new BorderLayout());
        add(new JScrollPane(loggingPanel), BorderLayout.EAST);
        add(new JScrollPane(worldPanel), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
