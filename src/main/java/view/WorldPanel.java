package view;

import dto.WorldDto;
import model.OrganismType;
import model.Position;
import model.WorldListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorldPanel extends JPanel implements WorldListener {
    private WorldDto worldModel;
    private int preferredWidth;
    private int preferredHeight;
    private int mapWidth;
    private int mapHeight;

    private final JPopupMenu addOrganismPopupMenu;
    private final List<OrganismMenuItem> organismMenuItems;
    private Position positionToAdd;

    private final WorldPanelListener listener;

    private final int MAP_PADDING = 10;
    private final int CELL_SIZE = 30;

    public WorldPanel(WorldPanelListener listener) {
        this.listener = listener;
        preferredWidth = 800;
        preferredHeight = 800;

        organismMenuItems = new ArrayList<>();
        for (OrganismType type : OrganismType.values()) {
            organismMenuItems.add(new OrganismMenuItem(type));
        }
        addOrganismPopupMenu = new JPopupMenu();
        organismMenuItems.forEach(addOrganismPopupMenu::add);
        addMouseListener(new PopupListener());
        addMenuItemListeners();
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

    private Optional<Position> getPositionAt(int x, int y) {
        int posX = (x - ((getWidth() - mapWidth) / 2)) / CELL_SIZE;
        int posY = (y - ((getHeight() - mapHeight) / 2)) / CELL_SIZE;
        if (posX >= 0 && posX < worldModel.getSizeX() && posY >= 0 && posY < worldModel.getSizeY()) {
            return Optional.of(new Position(posX, posY));
        }
        return Optional.empty();
    }

    private void addMenuItemListeners() {
        organismMenuItems
                .forEach(organismMenuItem ->
                        organismMenuItem.addActionListener(e ->
                                listener.addOrganism(positionToAdd, organismMenuItem.getOrganismType())));
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
        if (worldModel != null) {
            g.translate((getWidth() - mapWidth) / 2, (getHeight() - mapHeight) / 2);
            drawMap(g);
            drawOrganisms(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth, preferredHeight);
    }

    class PopupListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                getPositionAt(e.getX(), e.getY()).ifPresent(position -> {
                    addOrganismPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    positionToAdd = position;
                });
            }
        }
    }
}
