package com.vampire.rpg.regions.areas.actions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.regions.areas.TriggerAreaAction;

public class TriggerAreaActionMessage extends TriggerAreaAction {

    private String message = "";

    //    private HashMap<UUID, Long> last = new HashMap<UUID, Long>();

    public TriggerAreaActionMessage(String s) {
        this.message = ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public void act(Player p, PlayerData pd) {
        //        if (System.currentTimeMillis() - last.getOrDefault(p.getUniqueId(), 0l) > 30000) {
        //            last.put(p.getUniqueId(), System.currentTimeMillis());
        pd.sendMessage(message);
        //        }
    }

}