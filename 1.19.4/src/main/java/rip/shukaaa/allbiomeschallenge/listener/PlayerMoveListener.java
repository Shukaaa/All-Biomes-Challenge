package rip.shukaaa.allbiomeschallenge.listener;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import rip.shukaaa.allbiomeschallenge.services.BossbarService;
import rip.shukaaa.allbiomeschallenge.stores.BiomesFoundStore;
import rip.shukaaa.allbiomeschallenge.wrapper.BiomeWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) throws IOException {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Biome currentBiome = player.getLocation().getBlock().getBiome();
        Biome nextBiomeToVisit = BiomesFoundStore.getNextBiomeToVisit(uuid);

        if (currentBiome != nextBiomeToVisit) {
            return;
        }

        BiomesFoundStore.addBiomeToList(uuid, currentBiome);

        Biome[] visitedBiomes = BiomesFoundStore.getVisitedBiomes(uuid);
        Biome[] allBiomes = BiomeWrapper.getAllBiomes();
        ArrayList<Biome> remainingBiomesList = new ArrayList<>();

        assert visitedBiomes != null;
        for (Biome biome : allBiomes) {
            for (Biome visitedBiome : visitedBiomes) {
                if (biome == visitedBiome) {
                    break;
                }

                remainingBiomesList.add(biome);
            }
        }

        Biome nextBiome = remainingBiomesList.get((int) (Math.random() * remainingBiomesList.size()));

        BiomesFoundStore.setNextBiomeToVisit(uuid, nextBiome);
        BossbarService.sendBossbar(player, nextBiome, visitedBiomes);
        player.playSound(player.getLocation(), "minecraft:entity.player.levelup", 1, 1);
    }
}
