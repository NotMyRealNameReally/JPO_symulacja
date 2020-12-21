package model;

import javax.swing.ImageIcon;

public abstract class Organism implements Comparable<Organism>{
    protected int strength;
    protected int initiative;
    protected int posX;
    protected int posY;
    protected int age;
    protected int actionPoints;
    protected final World world;
    private boolean dead = false;
    private final ImageIcon icon;

    public Organism(int strength, int initiative, int posX, int posY, World world, String iconName) {
        this.strength = strength;
        this.initiative = initiative;
        this.posX = posX;
        this.posY = posY;
        this.age = 0;
        this.actionPoints = 1;
        this.world = world;
        this.icon = new ImageIcon(ClassLoader.getSystemResource(iconName));
    }

    public Organism(int strength, int initiative, int posX, int posY, int age, World world, String iconName) {
        this.strength = strength;
        this.initiative = initiative;
        this.posX = posX;
        this.posY = posY;
        this.age = age;
        this.world = world;
        this.icon = new ImageIcon(ClassLoader.getSystemResource(iconName));
    }

    protected abstract void action();
    protected abstract FightResults fight(Organism attacker);

    public void addActionPoints(int amount){
        actionPoints += amount;
    }

    void tagAsDead(){
        dead = true;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isDead(){
        return dead;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    @Override
    public int compareTo(Organism other) {
        int result = Integer.compare(other.initiative, this.initiative);
        if(result != 0){
            return result;
        }
        return Integer.compare(other.age, this.age);
    }
}
