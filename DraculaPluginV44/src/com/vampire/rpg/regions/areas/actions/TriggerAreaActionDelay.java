package com.vampire.rpg.regions.areas.actions;

import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.regions.areas.TriggerAreaAction;


public class TriggerAreaActionDelay extends TriggerAreaAction {

    public long delay;

    public TriggerAreaActionDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public void act(Player p, PlayerData pd) {
    }

}
