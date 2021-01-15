package view;

import java.awt.FlowLayout;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

public class ControlPanel extends JPanel {
    private final JButton newWorldBtn;
    private final JButton nextTurnBtn;
    private final JSpinner worldSizeSpinner;

    public ControlPanel(ControlPanelListener listener){
        newWorldBtn = new JButton(new String("Nowy świat".getBytes(), StandardCharsets.UTF_8));
        nextTurnBtn = new JButton(new String("następna tura".getBytes(), StandardCharsets.UTF_8));
        worldSizeSpinner = new JSpinner(new SpinnerNumberModel(20, 5, 1000, 1));
        attachBtnListeners(listener);

        setLayout(new FlowLayout());
        add(nextTurnBtn);
        add(newWorldBtn);
        add(worldSizeSpinner);
    }

    private void attachBtnListeners(ControlPanelListener listener) {
        newWorldBtn.addActionListener(e -> {
            int size = (int) worldSizeSpinner.getValue();
            listener.createNewWorld(size, size);
        });
        nextTurnBtn.addActionListener(e -> listener.nextTurn());
    }
}
