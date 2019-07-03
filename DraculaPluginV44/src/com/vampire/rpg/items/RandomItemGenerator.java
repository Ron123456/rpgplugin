package com.vampire.rpg.items;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.vampire.rpg.items.stats.StatAccumulator;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamMessages;

public class RandomItemGenerator {
	private static double rand(int rarity) {
        double val = Math.random() - (0.02 * rarity);
        if (val < 0)
            val = 0;
        return val;
    }

    public static ItemStack generateEquip(EquipType et, int level) {
        return generateEquip(et, level, 0);
    }

    public static ItemStack generateEquip(EquipType et, int level, int rarityFinder) {
        // Roll rarity
        int rarity = 0;
        double rand = Math.random();
        rand *= 1 / (1 + (rarityFinder / 100.0));
        for (int k = 0; k < ItemBalance.RARITY_CHANCES.length; k++) {
            if (rand < ItemBalance.RARITY_CHANCES[k])
                rarity = k;
        }
        return generateEquip(et, level, rarityFinder, rarity);
    }

    public static ItemStack generateEquip(EquipType et, int level, int rarityFinder, int rarity) {
       // String setName = null;
   /*     double rand = Math.random();
        for (int k = 0; k < ItemBalance.RARITY_CHANCES.length; k++) {
            if (rand < ItemBalance.RARITY_CHANCES[k])
                rarity = k;
        }*/
        double rand2 = Math.random();
        int dur=0;
        for (int k = 0; k < ItemBalance.DURABILLITY_CHANCES.length; k++) {
            if (rand2 < ItemBalance.DURABILLITY_CHANCES[k])
                dur = k;
        }
        //"Broken"-tier 1
        
        //"Fine"-tier 2
        //
        //"Perfect"- max tier
       /* if (rand(rarity) < ItemBalance.PREFIX_CHANCE) {
            setName = ItemBalance.SET_PREFIXES[(int) (Math.random() * ItemBalance.SET_PREFIXES.length)];
        }*/
        return generateEquip(et, level, rarityFinder, rarity, dur);
    }

    public static ItemStack generateEquip(EquipType et, int level, int rarityFinder, int rarity, int Durabillity) {
        // Roll sage name
        boolean sage = false;
        if (Math.random() < ItemBalance.SAGE_CHANCE) {
            sage = true;
        }
        return generateEquip(et, level, rarityFinder, rarity, Durabillity, sage);
    }

    public static ItemStack generateEquip(EquipType et, int level, int rarityFinder, int rarity, int Durballity, boolean isSage) {
        // Set up stat accumulator
        StatAccumulator stats = new StatAccumulator();
        stats.level = level;

        // Check if armor
        boolean isArmor = EquipType.isArmor(et);

        // Calculate level including rarity bonus
        int rarityLevel = level + StatBalance.getRarityLevelBonus(rarity)+StatBalance.getDurabillityBonus(Durballity);

        // Add armor-only stats
        if (isArmor) {
            stats.setHP(StatBalance.getHP(rarityLevel));
            stats.setDefense(StatBalance.getDefense(rarityLevel));
            if (rand(rarity) < 0.1) {
                stats.setHPMultiplier(StatBalance.getHPMultiplier(rarityLevel));
            }
            if (rand(rarity) < 0.1) {
                stats.setDefenseMultiplier(StatBalance.getDefenseMultiplier(rarityLevel));
            }
            if (et == EquipType.BOOTS && rand(rarity) < 0.5) {
                stats.setSpeed(StatBalance.getSpeed(rarityLevel));
            } else if (rand(rarity) < 0.15) {
                stats.setSpeed(StatBalance.getSpeed(rarityLevel));
            }
            if (rand(rarity) < 0.15)
                stats.setHPRegen(StatBalance.getHPRegen(rarityLevel));
        }

        // Add weapon-only stats
        if (!isArmor) {
            int[] range = StatBalance.getDamageRange(rarityLevel);
            stats.setDamage(range[0], range[1]);
            if (rand(rarity) < 0.25)
                stats.setCritChance(StatBalance.getCritChanceWeapon(rarityLevel));
            if (rand(rarity) < 0.15)
                stats.setAttackSpeed(StatBalance.getAttackSpeed(rarityLevel));
            if (rand(rarity) < 0.10)
                stats.setAttackDamage(StatBalance.getAttackDamage(rarityLevel));
            if(rand(rarity)<0.4)
            	stats.setLifeSteal(StatBalance.getLifesteal(rarityLevel));
        }

        // add non-exclusive stats
        if (rand(rarity) < 0.15)
            stats.setRarityFinder(StatBalance.getRarityFinder(rarityLevel));
        if (rand(rarity) < 0.10)
            stats.setManaRegenRate(StatBalance.getManaRegenRate(rarityLevel));
        if (rand(rarity) < 0.15)
            stats.setSpellDamage(StatBalance.getSpellDamage(rarityLevel));

        // Build name
        StringBuilder sb = new StringBuilder();
        if (isSage) {
            sb.append(ItemBalance.SAGE_NAMES[VamMath.randInt(0, ItemBalance.SAGE_NAMES.length - 1)]);
            sb.append("'s "); //Misaka's
        }
        if (rarity > 0) {
            sb.append(ItemBalance.RARITY_NAMES[rarity]); //Misaka's Godlike
            sb.append(' ');
        }
      //  if ( != null) {
            sb.append(ItemBalance.getDUrName(Durballity));
            sb.append(' ');
    //    }
        if (isArmor) {
            sb.append(ItemBalance.getTierPrefixArmor(level));
        } else {
            sb.append(ItemBalance.getTierPrefixWeapon(level));
        }
        sb.append(' ');
        sb.append(et.equipName); //Sword

        // Roll suffixes - these DON'T use the rarity random b/c it'd make high rarity equips too consistently strong
        double rand = Math.random(); // the last number is the chance of that suffix
        if (rand < 0.03) {//0.03
            int a = (int) (Math.random() * 5);
            switch (a) {
                case 0:
                    sb.append(" of Seeking");
                    stats.setCritChance(StatBalance.getCritChanceWeapon(rarityLevel + 10));
                    stats.setCritDamage(StatBalance.getCritDamage(rarityLevel + 10));
                    break;
                case 1:
                    sb.append(" of Swiftness");
                    stats.setSpeed(StatBalance.getSpeed(rarityLevel + 10));
                    stats.setAttackSpeed(StatBalance.getAttackSpeed(rarityLevel + 10));
                    break;
                case 2:
                    sb.append(" of Mysticism");
                    stats.setSpellDamage(StatBalance.getSpellDamage(rarityLevel + 10));
                    stats.setManaRegenRate(StatBalance.getManaRegenRate(rarityLevel + 10));
                    break;
                case 3:
                    sb.append(" of Wealth");
                    stats.setRarityFinder(StatBalance.getRarityFinder(rarityLevel + 50));
                    break;
                case 4:
                	sb.append(" of Vampirism");
                	stats.setLifeSteal(StatBalance.getLifesteal(rarityLevel+30));
                default:
                    break;
            }
        }

        ChatColor rarityColor = ItemBalance.RARITY_COLORS[rarity];
        sb.insert(0, rarityColor); //&6Misaka's Godlike Sword

        // sage stats are incremented only at the end
        stats.setSage(isSage);

        // Create the item
        EquipItem ei = new EquipItem(et.getMaterial(level));
        ei.name = sb.toString();
        ei.stats = stats;
        if(rarity>=6)
        	VamMessages.announcePlayers(ei.name+ChatColor.RESET+" Has Been Found");
        return ei.generate();
    }
}
