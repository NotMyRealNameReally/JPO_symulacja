package model;

import javax.swing.ImageIcon;

public abstract class Organism implements Comparable<Organism>{
    private int strength;
    private int initiative;
    private Position position;
    private int age;
    private int actionPoints;
    private final World world;
    private boolean dead = false;
    private final ImageIcon icon;

    Organism(int strength, int initiative, Position position, World world, String iconName) {
        this.strength = strength;
        this.initiative = initiative;
        this.position = position;
        this.age = 0;
        this.actionPoints = 1;
        this.world = world;
        this.icon = new ImageIcon(ClassLoader.getSystemResource(iconName));
    }

    Organism(int strength, int initiative, Position position, int age, World world, String iconName) {
        this.strength = strength;
        this.initiative = initiative;
        this.position = position;
        this.age = age;
        this.world = world;
        this.icon = new ImageIcon(ClassLoader.getSystemResource(iconName));
    }

    abstract ActionResult action();
    protected abstract FightResults fight(Organism attacker);

    void addActionPoints(int amount){
        actionPoints += amount;
    }

    void tagAsDead(){
        dead = true;
    }

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }

    public World getWorld() {
        return world;
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
