package com.vampire.rpg.mobs.spells;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.mobs.MobData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamMath;
import com.vampire.rpg.utils.VamScheduler;

import de.slikey.effectlib.util.ParticleEffect;

public class MelodaIceSpell extends MobSpell {

    private int range;
    private long cooldown;

    public MelodaIceSpell(int range, long cooldown) {
        this.range = range;
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        final ArrayList<Entity> hit = new ArrayList<Entity>();
        final Location start = md.entity.getLocation();
        start.setY(start.getY() + 1.2);
        for (Vector v : getVectorsNormal(md.entity)) {
            ArrayList<Location> locs = VamMath.calculateVectorPath(start.clone(), v, range, 2);
            int count = 0;
            for (int k = 0; k < locs.size(); k++) {
                final Location loc = locs.get(k);
                VamScheduler.schedule(Spell.plugin, new Runnable() {
                    public void run() {
                        ParticleEffect.REDSTONE.display(null, loc, Color.AQUA, 100, 0.2f, 0.2f, 0.2f, 1, 2);
                        int damage = (int) (md.getDamage() * 1.2);
                        ArrayList<Entity> damaged = Spell.damageNearby(damage, md.entity, loc, 1.0, hit, true, false, true);
                        hit.addAll(damaged);
                    }
                }, 1 * count);
                if (k % 2 == 0)
                    count++;
            }
        }
    }

    private ArrayList<Vector> getVectorsNormal(LivingEntity e) {
        ArrayList<Vector> vectors = new ArrayList<Vector>();
        Vector v = e.getEyeLocation().getDirection().normalize();
        v.setY(0);
        vectors.add(v);
        double z = v.getZ();
        double x = v.getX();
        double radians = Math.atan(z / x);
        if (x < 0)
            radians += Math.PI;
        for (int k = 1; k < 12; k++) {
            Vector v2 = new Vector();
            v2.setY(v.getY());
            v2.setX(Math.cos(radians + k * Math.PI / 6));
            v2.setZ(Math.sin(radians + k * Math.PI / 6));
            vectors.add(v2.normalize());
        }
        return vectors;
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
