package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.persistence.PersistentDataType;

public final class AmuletListener implements Listener {
    private static final long COOLDOWN_MILLIS = 15_000L;
    private final Main plugin;

    public AmuletListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAmuletUse(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND ||
                (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        ItemStack item = event.getItem();
        if (!plugin.isAmulet(item)) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        long now = System.currentTimeMillis();
        Long availableAt = player.getPersistentDataContainer().get(plugin.getCooldownKey(), PersistentDataType.LONG);
        if (availableAt != null && availableAt > now) {
            long seconds = (availableAt - now + 999) / 1000;
            player.sendActionBar(Component.text("The amulet needs " + seconds + "s to recharge.").color(NamedTextColor.RED));
            return;
        }

        player.getPersistentDataContainer().set(plugin.getCooldownKey(), PersistentDataType.LONG, now + COOLDOWN_MILLIS);
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 300, 0, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 300, 0, false, false, true));
        player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation().add(0, 1, 0), 80, 1.2, 1.2, 1.2, 0.08);
        player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1, 0), 25, 0.6, 0.8, 0.6, 0.1);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.35f);
        player.sendActionBar(Component.text("Daylight answers your call.").color(NamedTextColor.GOLD));
    }
}
