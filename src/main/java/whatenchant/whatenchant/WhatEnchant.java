package whatenchant.whatenchant;

import org.bukkit.plugin.java.JavaPlugin;

public final class WhatEnchant extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("enchantcheck").setExecutor(new EnchantCore());
        getServer().getPluginManager().registerEvents(new Events(),this);
    }

    @Override
    public void onDisable() {
        //
    }
}
