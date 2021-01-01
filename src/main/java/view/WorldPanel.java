package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import dto.WorldDto;
import model.WorldListener;

public class WorldPanel extends JPanel implements WorldListener {
    private WorldDto worldModel;
    private int preferredWidth;
    private int preferredHeight;
    private int mapWidth;
    private int mapHeight;

    private final int MAP_PADDING = 10;
    private final int CELL_PADDING = 2;
    private final int CELL_SIZE = 30;

    public WorldPanel() {
        preferredWidth = 800;
        preferredHeight = 800;
    }

    @Override
    public void worldCreated(WorldDto worldDto) {
        this.worldModel = worldDto;
        mapWidth = worldModel.getSizeX() * CELL_SIZE;
        mapHeight = worldModel.getSizeY() * CELL_SIZE;
        preferredWidth = mapWidth + MAP_PADDING * 2;
        preferredHeight = mapHeight + MAP_PADDING * 2;
        revalidate();
        repaint();
    }

    @Override
    public void worldChanged() {
        repaint();
    }

    private void drawMap(Graphics g) {
        g.drawRect(0, 0, mapWidth, mapHeight);

        for (int i = 0; i < worldModel.getSizeX(); i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, mapHeight);
        }
        for (int i = 0; i < worldModel.getSizeY(); i++) {
            g.drawLine(0, i * CELL_SIZE, mapWidth, i * CELL_SIZE);
        }
    }

    private void drawOrganisms(Graphics g) {
        worldModel.getOrganisms().forEach(organism -> {
            Image icon = organism
                    .getIcon()
                    .getImage();

            g.drawImage(icon,
                    organism.getPosition().getX() * CELL_SIZE + (CELL_SIZE - icon.getWidth(this)) / 2,
                    organism.getPosition().getY() * CELL_SIZE + (CELL_SIZE - icon.getHeight(this)) / 2,
                    this);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(worldModel != null) {
            g.translate((getWidth() - mapWidth) / 2, (getHeight() - mapHeight) / 2);
            drawMap(g);
            drawOrganisms(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth, preferredHeight);
    }
}
