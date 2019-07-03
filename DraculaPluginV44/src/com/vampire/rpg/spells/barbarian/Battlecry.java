package com.vampire.rpg.spells.barbarian;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.spells.SpellEffect;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamSound;

import de.slikey.effectlib.util.ParticleEffect;

public class Battlecry extends SpellEffect {

	@Override
	public boolean cast(Player p, PlayerData pd, int level) {
		  int damage = pd.getDamage(true);
	        switch (level) {
	            case 1:
	                damage *= 1.2;
	                break;
	            case 2:
	                damage *= 1.4;
	                break;
	            case 3:
	                damage *= 1.6;
	                break;
	            case 4:
	                damage *= 1.8;
	                break;
	            case 5:
	                damage *= 2.0;
	                break;
	            case 6:
	                damage *= 2.2;
	                break;
	            case 7:
	                damage *= 2.4;
	                break;
	            case 8:
	                damage *= 2.6;
	                break;
	            case 9:
	                damage *= 2.8;
	                break;
	            case 10:
	                damage *= 3.0;
	                break;
	        }
	        Vector direction = p.getLocation().getDirection().normalize();
	        double x = direction.getX();
	        double y = direction.getY();
	        double z = direction.getZ();
	        double radians = Math.atan(z / x);
	        if (x < 0)
	            radians += Math.PI;

	        ArrayList<Location> arc = new ArrayList<Location>();

	        Location start = p.getLocation().clone().add(0, -1, 0);

	        arc.add(start.clone().add(direction.multiply(1.5)));

	        Vector v = new Vector();
	        v.setY(y);
	        v.setX(Math.cos(radians - 2 * Math.PI / 9));
	        v.setZ(Math.sin(radians - 2 * Math.PI / 7));
	        arc.add(start.clone().add(v.multiply(1.5)));

	        v = new Vector();
	        v.setY(y);
	        v.setX(Math.cos(radians - Math.PI / 9));
	        v.setZ(Math.sin(radians - Math.PI / 9));
	        arc.add(start.clone().add(v.multiply(1.5)));

	        v = new Vector();
	        v.setY(y);
	        v.setX(Math.cos(radians + Math.PI / 9));
	        v.setZ(Math.sin(radians + Math.PI / 9));
	        arc.add(start.clone().add(v.multiply(1.5)));

	        v = new Vector();
	        v.setY(y);
	        v.setX(Math.cos(radians + 2 * Math.PI / 9));
	        v.setZ(Math.sin(radians + 2 * Math.PI / 9));
	        arc.add(start.clone().add(v.multiply(1.5)));

	        ArrayList<Entity> hit = new ArrayList<Entity>();
	        VamParticles.showWithSpeed(ParticleEffect.SWEEP_ATTACK, arc.get(0).clone().add(0, 1.5, 0), 0, 2);
	        for (int k = 0; k < 8; k++) {
	            for (Location loc : arc) {
	                hit.addAll(Spell.damageNearby(damage, p, loc, 2, hit));
	                loc.add(0, 0.5, 0);
	            }
	        }
		VamSound.playSound(p, Sound.ENTITY_GHAST_SCREAM);
		  Spell.notify(p, "You scream at your enemies with fierce.");
		return true;
	}

}
