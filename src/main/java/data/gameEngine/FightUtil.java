package data.gameEngine;

import data.movables.enemies.Enemy;
import data.movables.playerClass.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        player.lock();
    }

    private void calculateBasicAttack(Enemy enemy) {
        int rollDice = random.nextInt(21);
        int attack;

        switch (rollDice) {
            case 20:
                //critical hit
                attack = 20 + player.getAttack();
                enemy.getHit(attack);
                addMessage(String.format("[FIGHT]: Critical Hit! You dealt %d damage to %s",
                        attack, enemy.getClass().getSimpleName()));
                break;
            case 1:
                //miss
                addMessage("[FIGHT]: Missed hit!");
                break;
            default:
                //regular hit
                int dodgeRate = random.nextInt(enemy.getDefense()+1);
                attack = rollDice + player.getAttack() - dodgeRate;
                enemy.getHit(attack);
                addMessage(String.format("[FIGHT]: You dealt %d damage to %s, enemy reflection rate was -%d.",
                        attack, enemy.getClass().getSimpleName(), dodgeRate));
                break;
        }
        killEnemyIfZeroHp(enemy);
    }

    private void calculateEnemyBasicAttack(Enemy enemy) {
        int rollDice = random.nextInt(21);
        int attack;

        switch (rollDice) {
            case 20:
                //critical hit
                attack = rollDice/2 + enemy.getAttack();
                player.getHit(attack);
                addMessage(String.format("[ATTACK]: Critical hit! %s attacked you and dealt %d damage.",
                        enemy.getClass().getSimpleName(), attack));
                break;
            case 1:
                //miss
                addMessage(String.format("[ATTACK]: %s tried to hit you and missed!", enemy.getClass().getSimpleName()));
                break;
            default:
                //regular hit
                int dodgeRate = random.nextInt(player.getDefense()+1);
                attack = rollDice/2 + enemy.getAttack() - player.getDefense();
                player.getHit(attack);
                addMessage(String.format("[ATTACK]: %s attacked you and dealt %d damage, your reflection rate was -%d.",
                        enemy.getClass().getSimpleName( ), attack, dodgeRate));
                break;
        }
    }

    private void killEnemyIfZeroHp(Enemy enemy) {
        if (enemy.getHP() <= 0) {
            addMessage(String.format("[EVENT]: %s died!", enemy.getClass().getSimpleName()));
            enemies.remove(enemy);
        }
    }

    private void addMessage(String message) {
        if (messages.size() == 7) {
            messages.removeLast();
        }
        messages.push(message);
    }

    public boolean tryAttackPlayer(Enemy enemy) {
        boolean attacked = false;
        if (nextToPlayer(enemy)) {
            attacked = true;
            calculateEnemyBasicAttack(enemy);
            player.lock();
        }
        return attacked;
    }

    private boolean nextToPlayer(Enemy enemy) {
        int x = enemy.getX();
        int y = enemy.getY();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int dx = x + j;
                int dy = y + i;
                if (dx == player.getX() && dy == player.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    public LinkedList<String> getMessages() {
        return messages;
    }
}
