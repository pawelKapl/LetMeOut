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
    }

    private void calculateBasicAttack(Enemy enemy) {
        int rollDice = random.nextInt(21);
        int attack;

        switch (rollDice) {
            case 20:
                //critical hit
                attack = 20 + player.attack();
                enemy.getHit(attack);
                addMessage(String.format("Fight: Critical Hit! You dealt %d damage to %s",
                        attack, enemy.getClass().getSimpleName()));
                break;
            case 1:
                //miss
                addMessage("Fight: Missed hit!");
                break;
            default:
                //regular hit
                int dodgeRate = random.nextInt(enemy.getDefense()+1);
                attack = rollDice + player.attack() - dodgeRate;
                enemy.getHit(attack);
                addMessage(String.format("Fight: You dealt %d damage to %s, enemy reflection rate was -%d.)",
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
                attack = rollDice/2 + enemy.attack();
                player.getHit(attack);
                addMessage(String.format("Attack: Critical hit! %s attacked you and dealt %d damage.",
                        enemy.getClass().getSimpleName(), attack));
            case 1:
                //miss
                addMessage(String.format("Attack: %s tried to hit you and missed!", enemy.getClass().getSimpleName()));
            default:
                int dodgeRate = random.nextInt(player.getDefense()+1);
                attack = rollDice/2 + enemy.attack() - player.getDefense();
                player.getHit(attack);
                addMessage(String.format("Attack: %s attacked you and dealt %d damage, your reflection rate was -%d.",
                        enemy.getClass().getSimpleName( ), attack, dodgeRate));

        }
    }

    private void killEnemyIfZeroHp(Enemy enemy) {
        if (enemy.getHP() <= 0) {
            addMessage(String.format("Event: %s died!", enemy.getClass().getSimpleName()));
            enemies.remove(enemy);
        }
    }

    private void addMessage(String message) {
        if (messages.size() == 7) {
            messages.removeLast();
        }
        messages.push(message);
    }

    public void tryAttackPlayer(Enemy enemy) {
        if (nextToPlayer(enemy)) {
            calculateEnemyBasicAttack(enemy);

        }
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
