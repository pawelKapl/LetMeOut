package data.gameEngine;

import data.movables.enemies.Enemy;
import data.movables.playerClass.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.random;

public class FightUtil {

    private Player player;
    private List<Enemy> enemies;
    private LinkedList<String> messages = new LinkedList<>();
    private Random random = new Random();

    public FightUtil(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
    }

    public void attackEnemy(int x, int y) {
        Enemy enemyAttacked = null;
        for (Enemy enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() == y) {
                enemyAttacked = enemy;
            }
        }
        calculateBasicAttack(enemyAttacked);
    }

    private void calculateBasicAttack(Enemy enemy) {
        int rollDice = random.nextInt(21);
        int attack;

        switch (rollDice) {
            case 20:
                //critical hit
                attack = 20 + player.attack();
                enemy.getHit(attack);
                addMessage(String.format("Fight: Critical Hit! Dealed %d damage to %s", attack, enemy.getClass().getSimpleName()));
                break;
            case 1:
                //miss
                addMessage("Fight: Missed hit!");
                break;
            default:
                //regular hit
                int dodgeRate = (int) random()*enemy.getDefense();
                attack = rollDice + player.attack() - dodgeRate;
                enemy.getHit(attack);
                addMessage(String.format("Fight: Dealed %d damage to %s", attack, enemy.getClass().getSimpleName()));
                break;
        }
        killEnemyIfZeroHp(enemy);
    }

    private void killEnemyIfZeroHp(Enemy enemy) {
        if (enemy.getHP() <= 0) {
            addMessage(String.format("Event: %s died!", enemy.getClass().getSimpleName()));
            enemies.remove(enemy);
        }
    }

    private void addMessage(String message) {
        if (messages.size() == 6) {
            messages.removeLast();
        }
        messages.push(message);
    }

    public LinkedList<String> getMessages() {
        return messages;
    }
}
