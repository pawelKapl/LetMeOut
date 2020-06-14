package data.gameEngine;

import data.movables.player.Player;

public enum SpecialSkills {

    ARMOR_BOOST {
        @Override
        public void performSkill(Player player) {
            if (player.getCooldown() > 0) {
                player.addMessage("[INFO]: Cant do that, cooldown remaining:" + player.getCooldown());
                return;
            }
            player.setCooldown(15);
            player.addDefenseBoost(10);
            player.addMessage("[INFO]: Armor boosted by 10!");
        }
    },

    ATTACK_BOOST {
        @Override
        public void performSkill(Player player) {
            if (player.getCooldown() > 0) {
                player.addMessage("[INFO]: Cant do that, cooldown remaining:" + player.getCooldown());
                return;
            }
            player.setCooldown(15);
            player.addAttackBoost(10);
            player.addMessage("[INFO]: Attack boosted by 10!");
        }
    };

    public abstract void performSkill(Player player);
}
