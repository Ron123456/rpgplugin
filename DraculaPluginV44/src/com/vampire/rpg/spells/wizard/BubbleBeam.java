package com.vampire.rpg.spells.wizard;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamScheduler;
import com.vampire.rpg.utils.VamTicks;

import de.slikey.effectlib.util.ParticleEffect;

public class BubbleBeam extends SpellEffect {

    @Override
    public boolean cast(final Player p, final PlayerData pd, final int level) {
        VamScheduler.schedule(Spell.plugin, new Runnable() {
            private int count = 0;
            final Vector dir = p.getLocation().getDirection();
            final Location start = p.getLocation().add(0, p.getEyeHeight() * 0.75, 0).add(dir.multiply(0.5));

            public void run() {
                ArrayList<Entity> hit = new ArrayList<Entity>();
                int damage = pd.getDamage(true);
                switch (level) {
                    case 1:
                        damage *= 0.55;
                        break;
                    case 2:
                        damage *= 0.65;
                        break;
                    case 3:
                        damage *= 0.75;
                        break;
                    case 4:
                        damage *= 0.85;
                        break;
                    case 5:
                        damage *= 0.95;
                        break;
                    case 6:
                        damage *= 1.05;
                        break;
                    case 7:
                        damage *= 1.15;
                        break;
                    case 8:
                        damage *= 1.25;
                        break;
                    case 9:
                        damage *= 1.35;
                        break;
                    case 10:
                        damage *= 1.45;
                        break;
                }
                for (Location loc : VamMath.calculateVectorPath(start.clone(), dir, 10, 4)) {
                    VamParticles.showWithOffset(ParticleEffect.WATER_WAKE, loc, 0.2, 7);
                    hit.addAll(Spell.damageNearby(damage, p, loc, 0.6, hit));
                }
                if (++count < 4) {
                    VamScheduler.schedule(Spell.plugin, this, VamTicks.seconds(0.5));
                }
            }
        });
        Spell.notify(p, "You shoot out a stream of magical bubbles.");
        return true;
    }
}
