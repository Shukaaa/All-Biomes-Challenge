package rip.shukaaa.allbiomeschallenge.listener;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import rip.shukaaa.allbiomeschallenge.services.BossbarService;
import rip.shukaaa.allbiomeschallenge.stores.BiomesFoundStore;
import rip.shukaaa.allbiomeschallenge.wrapper.BiomeWrapper;

import java.io.IOException;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (BiomesFoundStore.hasPlayer(uuid)) {
            Biome nextBiomeToVisit = BiomesFoundStore.getNextBiomeToVisit(uuid);
            Biome[] visitedBiomes = BiomesFoundStore.getVisitedBiomes(uuid);

            BossbarService.sendBossbar(player, nextBiomeToVisit, visitedBiomes);
            return;
        }

        Biome randomBiome = BiomeWrapper.getAllBiomes()[(int) (Math.random() * Biome.values().length)];
        BiomesFoundStore.addPlayer(uuid, randomBiome);
        BossbarService.sendBossbar(player, randomBiome, new Biome[0]);
    }
}
