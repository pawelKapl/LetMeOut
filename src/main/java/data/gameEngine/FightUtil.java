package data.gameEngine;

import data.movables.enemies.Enemy;
import data.movables.player.Player;
import data.terrains.TerrainType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FightUtil {

    private Player player;
    private List<Enemy> enemies;
    private LinkedList<String> messages = new LinkedList<>();
    private Random random = new Random();
    private EffectsLayer effectsLayer;

    public FightUtil(Player player, List<Enemy> enemies, EffectsLayer effectsLayer) {
        this.player = player;
        this.enemies = enemies;
        this.effectsLayer = effectsLayer;
    }

    public void attackEnemy(int x, int y) {
        Enemy enemyAttacked = null;

        for (Enemy enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() == y) {
                enemyAttacked = enemy;
                effectsLayer.mark(x, y, TerrainType.HIT_MARK);
            }
        }
        if (enemyAttacked == null) {
            return;
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
                attack = negativeAttackBlock(attack);
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
                attack = negativeAttackBlock(attack);
                player.getHit(attack);
                addMessage(String.format("[ATTACK]: %s attacked you and dealt %d damage, your reflection rate was -%d.",
                        enemy.getClass().getSimpleName( ), attack, dodgeRate));
                break;
        }
    }

    private int negativeAttackBlock(int attack) {
        if (attack < 0) {
            attack = 0;
        }
        return attack;
    }

    private void killEnemyIfZeroHp(Enemy enemy) {
        if (enemy.getHP() <= 0) {
            addMessage(String.format("[EVENT]: %s died!", enemy.getClass().getSimpleName()));
            player.gainExp((long) enemy.getExpReward());
            enemies.remove(enemy);
            effectsLayer.mark(enemy.getX(), enemy.getY(), TerrainType.DEAD_MARK);
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
        if (NearPlayer.nearPlayer(enemy, player, 1)) {
            attacked = true;
            calculateEnemyBasicAttack(enemy);
            player.lock();
        }
        return attacked;
    }

    public LinkedList<String> getMessages() {
        return messages;
    }

    public void performSpecialAttack(SpecialAttacks specialAttack) {
        if (player.getCooldown() != 0) {
            addMessage("[INFO]: Cant do that, cooldown remaining:" + player.getCooldown());
            return;
        }
        addMessage("[SKILL]: Using " + specialAttack.name());
        for (Enemy enemy : enemies) {
            String result = specialAttack.attack(enemy, player);
            if (result != null) {
                addMessage(result);
                if (result.startsWith("[ATTACK]")) {
                    effectsLayer.mark(enemy.getX(), enemy.getY(), TerrainType.HIT_MARK);
                } else if (result.startsWith("[WEAKNESS]")) {
                    effectsLayer.mark(enemy.getX(), enemy.getY(), TerrainType.WEAKNESS_MARK);
                }
            }
        }


    }
}
