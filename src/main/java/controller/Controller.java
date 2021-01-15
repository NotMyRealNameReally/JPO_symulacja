package controller;

import model.OrganismType;
import model.Position;
import model.World;
import model.WorldLogger;
import view.*;

import javax.swing.*;
import java.io.*;

public class Controller implements ControlPanelListener, MenuBarListener, WorldPanelListener {
    private World world;
    private final WorldPanel worldPanel;
    private final ControlPanel controlPanel;
    private final LoggingPanel loggingPanel;
    private final MenuBar menuBar;
    private final MainFrame mainFrame;

    public Controller() {
        this.worldPanel = new WorldPanel(this);
        this.controlPanel = new ControlPanel(this);
        this.loggingPanel = new LoggingPanel();
        this.menuBar = new MenuBar(this);
        this.mainFrame = new MainFrame(worldPanel, controlPanel, loggingPanel, menuBar);
        World.setLogger(new WorldLogger(loggingPanel));
    }

    @Override
    public void createNewWorld(int width, int height) {
        this.world = new World(width, height, worldPanel);
    }

    @Override
    public void nextTurn() {
        if (world != null) {
            world.newTurn();
        }
    }

    @Override
    public void saveWorldToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                FileOutputStream fileStream = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
                outputStream.writeObject(world);
                outputStream.close();
                fileStream.close();
            } catch (IOException e) {
                mainFrame.showErrorMessage("Save failed");
            }
        }

    }

    @Override
    public void loadWorldFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                FileInputStream fileStream = new FileInputStream(file);
                ObjectInputStream inputStream = new ObjectInputStream(fileStream);
                this.world = (World) inputStream.readObject();
                world.setListener(worldPanel);
                inputStream.close();
                fileStream.close();
            } catch (IOException | ClassNotFoundException e) {
                mainFrame.showErrorMessage("Could not load");
            }
        }
    }

    @Override
    public void addOrganism(Position position, OrganismType type) {
        world.forceAddOrganism(position, type);
    }
}
