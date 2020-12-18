package view;

import java.awt.FlowLayout;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ControlPanelListener;
import controller.Controller;

public class ControlPanel extends JPanel {
    private JButton newWorldBtn;
    private JButton nextTurnBtn;

    public ControlPanel(ControlPanelListener listener){
        newWorldBtn = new JButton(new String("Nowy świat".getBytes(), StandardCharsets.UTF_8));
        nextTurnBtn = new JButton(new String("następna tura".getBytes(), StandardCharsets.UTF_8));
        attachBtnListeners(listener);
        setLayout(new FlowLayout());
        add(nextTurnBtn);
        add(newWorldBtn);
    }

    private void attachBtnListeners(ControlPanelListener listener) {
        newWorldBtn.addActionListener(e -> listener.createNewWorld(10, 10));
        nextTurnBtn.addActionListener(e -> listener.nextTurn());
    }
}
