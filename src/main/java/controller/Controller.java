package controller;

import model.World;
import model.WorldLogger;
import view.ControlPanelListener;
import view.LoggingPanel;
import view.ControlPanel;
import view.MainFrame;
import view.WorldPanel;

public class Controller implements ControlPanelListener {
    private World world;
    private final WorldPanel worldPanel;
    private final ControlPanel controlPanel;
    private final LoggingPanel loggingPanel;
    private final MainFrame mainFrame;

    public Controller() {
        this.worldPanel = new WorldPanel();
        this.controlPanel = new ControlPanel(this);
        this.loggingPanel = new LoggingPanel();
        this.mainFrame = new MainFrame(worldPanel, controlPanel, loggingPanel);
    }

    @Override
    public void createNewWorld(int width, int height) {
        this.world = new World(new WorldLogger(loggingPanel), worldPanel);
    }

    @Override
    public void nextTurn() {
        if (world != null) {
            world.newTurn();
        }
    }
}
