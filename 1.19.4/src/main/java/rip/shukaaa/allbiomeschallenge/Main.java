package rip.shukaaa.allbiomeschallenge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import rip.shukaaa.allbiomeschallenge.listener.PlayerJoinListener;
import rip.shukaaa.allbiomeschallenge.listener.PlayerMoveListener;
import rip.shukaaa.allbiomeschallenge.services.BossbarService;
import rip.shukaaa.allbiomeschallenge.stores.BiomesFoundStore;

import java.io.IOException;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AllBiomesChallenge] Plugin started!");

        registerEvents(new PlayerJoinListener(), new PlayerMoveListener());

        try {
            BiomesFoundStore.checkFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        BossbarService.removeAllBossbars();
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}
