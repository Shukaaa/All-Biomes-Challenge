package rip.shukaaa.allbiomeschallenge.services;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import rip.shukaaa.allbiomeschallenge.wrapper.BiomeWrapper;

import java.util.HashMap;

public class BossbarService {
    private static HashMap<Player, BossBar> bossbars = new HashMap<>();

    public static void sendBossbar(Player player, Biome biome, Biome[] visitedBiomes) {
        // remove old boss bar
        if (bossbars.containsKey(player)) {
            bossbars.get(player).removePlayer(player);
            bossbars.remove(player);
        }

        // calculate progress
        int visitedBiomesCount = visitedBiomes.length;
        int allBiomesCount = BiomeWrapper.getAllBiomes().length;
        double progress = (double) visitedBiomesCount / allBiomesCount;

        // create new boss bar
        String title = "Next Biome is: " + ChatColor.GOLD + ChatColor.BOLD + biome.toString() + ChatColor.RESET + " (" + visitedBiomesCount + "/" + allBiomesCount + ")";
        BossBar bossbar = Bukkit.createBossBar(title, BarColor.BLUE, BarStyle.SOLID);
        bossbar.addPlayer(player);
        bossbar.setProgress(progress);
        bossbars.put(player, bossbar);
    }

    public static void removeAllBossbars() {
        for (BossBar bossbar : bossbars.values()) {
            bossbar.removeAll();
        }

        bossbars.clear();
    }
}
