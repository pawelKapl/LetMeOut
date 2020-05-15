package data.gui;

import data.equipment.Equipment;
import data.equipment.Item;
import data.equipment.armors.Armor;
import data.equipment.weapons.Weapon;
import data.gameEngine.GameLogic;
import data.movables.enemies.Enemy;
import data.movables.enemies.Lizard;
import data.movables.enemies.Predator;
import data.movables.player.Player;
import data.other.Preferences;
import data.terrains.TerrainType;
import data.terrains.TerrainTypeColors;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static data.other.Colors.ALMOST_BLACK;
import static data.other.Colors.ATT_GREEN;
import static data.other.Colors.BLINDING_PINK;
import static data.other.Colors.CALM_WHITE;
import static data.other.Colors.DARK_FRAMES;
import static data.other.Colors.DARK_RED;
import static data.other.Colors.DEF_BLUE;
import static data.other.Colors.LIGHT_RED;
import static data.other.Colors.LIZARD_RED;
import static data.other.Colors.PLAYER_BLUE;
import static data.other.Colors.PREDATOR_ORANGE;
import static data.other.Colors.TRAP_GREY;


@SuppressWarnings("serial")
public class GameBoard extends JPanel implements Updatable {

    private GameLogic game;


    public GameBoard(GameLogic game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ALMOST_BLACK);
        g.fillRect(0, 0, Preferences.windowWidth-20, Preferences.windowHeight-40);
        //pretty large 'façade' :)
        printFrames(g);
        printLocation(g);
        printPlayer(g);
        printEnemies(g);
        printPlayerStatus(g);
        printEquipmentMenu(g);
        printInventoryMenu(g);
        printFightLog(g);
        printEquipmentLog(g);
        printFogOfWar(g);

    }

    private void printFogOfWar(Graphics g) {
        boolean[][] fog = game.getFogOfWar().getFog();

        int dx = 35;
        int dy = 83;

        g.setColor(ALMOST_BLACK);
        for (int i = 0; i < fog.length; i++) {
            for (int j = 0; j < fog[0].length; j++) {
                if(!fog[i][j]) {
                    g.fillRect(dx, dy, 12, 20);
                }
                dx += 12;
            }
            dy += 20;
            dx = 35;
        }
    }

    private void printPlayerStatus(Graphics g) {
        setLegendFont(g);
        Player player = game.getPlayer();

        String profession = player.getClass().getSimpleName();
        int height = Preferences.windowHeight*2/3 + 50;
        int width = 35;

        g.drawString("Location:  " + game.getTerrain().getName(), width, 40);
        g.drawString("Player: " + player.getName() + "    Class: " + profession + "   lvl: " + player.getLevel(), width, height);

        g.setColor(DARK_RED);
        width += 200 + player.getName().length()*7 + profession.length()*7;
        g.drawString("hp: " + player.getHP() + "/" + player.getMaxHp(),  width,
                height);
        width += 100;
        g.setColor(ATT_GREEN);
        g.drawString("att: " + player.getAttack(), width, height);
        width += 60;
        g.setColor(DEF_BLUE);
        g.drawString("def: " + player.getDefense(), width, height);
        width += 100;
        g.setColor(CALM_WHITE);
        g.drawString("Exp: " + player.getExperience(), width, height);
    }

    private void printFightLog(Graphics g) {
        setLogFont(g);

        LinkedList<String> fightLog = game.getFightUtil().getMessages();
        int y = Preferences.windowHeight*2/3 + 90;
        for (String s : fightLog) {
            if (s.startsWith("[FIGHT]")) {
                g.setColor(Color.YELLOW);
            } else if (s.startsWith("[EVENT]")) {
                g.setColor(DEF_BLUE);
            } else if (s.startsWith("[ATTACK]")) {
                g.setColor(DARK_RED);
            }
            g.drawString(s, 35, y);
            y += 20;
        }
    }

    private void printEquipmentLog(Graphics g) {
        setLogFont(g);

        LinkedList<String> fightLog = game.getPlayer().getMessages();
        int y = Preferences.windowHeight*2/3 + 50;
        for (String s : fightLog) {
            if (s.startsWith("[INFO]")) {
                g.setColor(CALM_WHITE);
            } else if (s.startsWith("[WARN]")) {
                g.setColor(DARK_RED);
            } else if (s.startsWith("[LOOT]")) {
                g.setColor(Color.YELLOW);
            }
            g.drawString(s, 1070, y);
            y += 20;
        }
    }

    private void printLocation(Graphics g) {
        int dx = 35;
        int dy = 100;

        TerrainType[][] location = game.getTerrain().getMap();
        TerrainTypeColors colors = TerrainTypeColors
                .valueOf(game.getTerrain().getTerrainType());

        for (int i = 0; i < location.length; i++) {
            for (int j = 0; j < location[0].length; j++) {
                switch (location[i][j]) {
                    case WALL:
                        g.setColor(colors.getWallPaint());
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case GROUND:
                        g.setColor(colors.getGroundPaint());
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case FOREST:
                        g.setColor(ATT_GREEN);
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case DOOR:
                        g.setColor(BLINDING_PINK);
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case ITEM:
                    case UNIQUE_ITEM:
                        g.setColor(Color.YELLOW);
                        g.drawString(location[i][j].getStamp(), dx, dy);
                        break;
                    case TRAP:
                        g.setColor(TRAP_GREY);
                        g.drawString(location[i][j].getStamp(), dx, dy);
                }
                dx += 12;
            }
            dy += 20;
            dx = 35;
        }
    }

    private void printPlayer(Graphics g) {
        g.setColor(PLAYER_BLUE);
        g.drawString(TerrainType.PLAYER.getStamp(), (game.getPlayer().getX()*12)+30, (game.getPlayer().getY()*20)+100);
    }

    private void printEnemies(Graphics g) {
        for (Enemy e : game.getEnemies()) {
            if (e instanceof Lizard) {
                g.setColor(LIZARD_RED);
                g.drawString(TerrainType.LIZARD.getStamp(), (e.getX() * 12) + 35, (e.getY() * 20) + 100);
            } else if (e instanceof Predator) {
                g.setColor(PREDATOR_ORANGE);
                g.drawString(TerrainType.PREDATOR.getStamp(), (e.getX() * 12) + 35, (e.getY() * 20) + 100);
            }
        }
    }

    private void printInventoryMenu(Graphics g) {
        setLegendFont(g);

        Player player = game.getPlayer();

        int startWidth = Preferences.windowWidth*4/5 + 30;
        int startHeight = 40;
        g.drawString("Inventory:  ", startWidth, startHeight);
        setLogFont(g);
        startHeight += 30;

        g.drawString("[Equipped Weapons]", startWidth + 65, startHeight);
        startHeight += 30;

        int y = startHeight;
        List<Weapon> weapons = player.getWeapons();
        g.setColor(ATT_GREEN);

        for (Weapon weapon : weapons) {
            g.drawString(weapon.getName(), startWidth + 20, y);
            y += 20;
        }

        startHeight += 55;
        g.setColor(CALM_WHITE);
        g.drawString("[Equipped Armor]", startWidth + 73, startHeight);

        y = startHeight + 30;
        List<Armor> armors = player.getArmorList();
        g.setColor(DEF_BLUE);
        for (Armor armor : armors) {
            g.drawString(armor.getName(), startWidth + 20, y);
            y += 20;
        }
    }

    private void printEquipmentMenu(Graphics g) {
        Equipment eq = game.getPlayer().getEquipment();
        setLogFont(g);

        int startWidth = Preferences.windowWidth*4/5 + 30;
        int startHeight = 290;

        g.drawString("Equipment:", startWidth,startHeight);
        startWidth += 5;
        g.setColor(LIGHT_RED);
        g.drawString("Small Health Potions: " + eq.getSmallPotions() + "  <P>", startWidth, startHeight + 30);
        g.setColor(DARK_RED);
        g.drawString("Large Health Potions: " + eq.getLargePotions() + "  <L>", startWidth, startHeight + 50);

        startHeight += 85;
        g.setColor(CALM_WHITE);
        g.drawString("Items: ", startWidth, startHeight);

        startWidth += 10;
        int y = startHeight + 20;
        if (eq.getItems().isEmpty()) {
            g.drawString("<Empty>", startWidth, y);
        } else {
            Map<Integer, Item> items = eq.getItems();
            for (int item : items.keySet()) {
                if (items.get(item) instanceof Weapon) {
                    g.setColor(ATT_GREEN);
                } else if (items.get(item) instanceof Armor) {
                    g.setColor(DEF_BLUE);
                }
                g.drawString(String.format("%d. %s", item, items.get(item).getName()), startWidth, y);
                y += 20;
            }
        }
        printItemDescription(g, startWidth);
    }

    private void printItemDescription(Graphics g, int startWidth) {
        g.setColor(CALM_WHITE);
        String[] description = formatDescription();
        int y = 585;
        if (description != null) {
            for (String s : description) {
                if (s != null) {
                    g.drawString(s, startWidth - 5, y);
                    y += 20;
                }
            }
            return;
        }
        g.drawString("-", startWidth - 5, y);

    }

    private String[] formatDescription() {
        String currentDesc = game.getPlayer().getEquipment().getCurrentDesc();
        if (!currentDesc.isBlank()) {
            String[] strings = currentDesc.split(" ");
            String[] newDescription = new String[5];
            StringBuilder sb = new StringBuilder();

            int i = 0;
            int length = 0;
            for (String s : strings) {
                if (length + s.length() > 40) {
                    length = 0;
                    newDescription[i++] = sb.toString();
                    sb = new StringBuilder();
                }
                sb.append(s + " ");
                length += s.length() + 1;
            }
            newDescription[i] = sb.toString();
            return newDescription;
        }
        return null;
    }

    private void printFrames(Graphics g) {
        g.setColor(CALM_WHITE);
        g.drawRect(10,10, Preferences.windowWidth*4/5, Preferences.windowHeight*2/3);
        g.drawRect(Preferences.windowWidth*4/5 + 20, 10, Preferences.windowWidth/5 - 50, Preferences.windowHeight*2/3);
        g.drawRect(10, Preferences.windowHeight*2/3 + 20, Preferences.windowWidth - 40, Preferences.windowHeight/3 - 110);
        g.setColor(DARK_FRAMES);
        g.drawRect(Preferences.windowWidth*4/5 + 30,300,250,260);
        g.drawRect(Preferences.windowWidth*4/5 + 30, 565, 250, 100);
        g.drawRect(25, Preferences.windowHeight*2/3 + 70, 500,150);
        g.drawRect(1060,Preferences.windowHeight*2/3 + 30, 500, 200);

        g.setColor(Color.RED);
        g.drawString("DarkOnion", Preferences.windowWidth/2 - 30, Preferences.windowHeight - 60);
    }

    private void setLogFont(Graphics g) {
        Font font = new Font("log", Font.PLAIN, 12);
        g.setFont(font);
        g.setColor(CALM_WHITE);
    }

    private void setLegendFont(Graphics g) {
        Font font = new Font("legend", Font.BOLD, 15);
        g.setFont(font);
        g.setColor(CALM_WHITE);
    }

    @Override
    public void update() {
        super.repaint();
    }
}
