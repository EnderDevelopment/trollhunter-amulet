package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Main extends JavaPlugin implements TabExecutor {
    private NamespacedKey amuletKey;
    private NamespacedKey cooldownKey;

    @Override
    public void onEnable() {
        amuletKey = new NamespacedKey(this, "daylight_amulet");
        cooldownKey = new NamespacedKey(this, "ability_cooldown");
        registerRecipe();
        Bukkit.getPluginManager().registerEvents(new AmuletListener(this), this);
        if (getCommand("amuleto") != null) {
            getCommand("amuleto").setExecutor(this);
            getCommand("amuleto").setTabCompleter(this);
        }
        getLogger().info("Amulet of Daylight enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Amulet of Daylight disabled.");
    }

    private void registerRecipe() {
        ItemStack result = createAmulet();
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "daylight_amulet_recipe"), result);
        recipe.shape("GSG", "DND", "GDG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.SUNFLOWER);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('N', Material.NETHER_STAR);
        Bukkit.addRecipe(recipe);
    }

    public ItemStack createAmulet() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }
        meta.displayName(Component.text("Amulet of Daylight").color(NamedTextColor.GOLD));
        meta.lore(List.of(
                Component.text("A relic that answers the call of the light.").color(NamedTextColor.GRAY),
                Component.text("Right-click to release daylight.").color(NamedTextColor.YELLOW),
                Component.text("Cooldown: 15 seconds").color(NamedTextColor.DARK_GRAY)
        ));
        meta.getPersistentDataContainer().set(amuletKey, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isAmulet(ItemStack item) {
        if (item == null || item.getType() != Material.NETHER_STAR || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(amuletKey, PersistentDataType.BYTE);
    }

    public NamespacedKey getCooldownKey() {
        return cooldownKey;
    }

    public ItemStack giveAmulet(Player player) {
        ItemStack amulet = createAmulet();
        player.getInventory().addItem(amulet);
        return amulet;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("give")) {
            giveAmulet(player);
            player.sendMessage(Component.text("The Amulet of Daylight is now yours.").color(NamedTextColor.GOLD));
            return true;
        }
        player.sendMessage(Component.text("/amuleto give - receive the Amulet of Daylight").color(NamedTextColor.YELLOW));
        player.sendMessage(Component.text("Craft it with gold, diamonds, a sunflower and a nether star.").color(NamedTextColor.GRAY));
        return true;
    }

    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return java.util.List.of("give");
        }
        return java.util.List.of();
    }
}
