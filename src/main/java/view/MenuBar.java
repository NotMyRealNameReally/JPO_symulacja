package view;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    private final JMenuItem saveToFile;
    private final JMenuItem loadFromFile;
    private final MenuBarListener listener;

    public MenuBar(MenuBarListener listener){
        this.listener = listener;
        JMenu fileMenu = new JMenu("Plik");
        add(fileMenu);

        saveToFile = new JMenuItem("Zapisz");
        loadFromFile = new JMenuItem("Wczytaj");
        fileMenu.add(saveToFile);
        fileMenu.add(loadFromFile);

        attachItemListeners();
    }

    private void attachItemListeners(){
        saveToFile.addActionListener(e -> listener.saveWorldToFile());
        loadFromFile.addActionListener(e -> listener.loadWorldFromFile());
    }
}
