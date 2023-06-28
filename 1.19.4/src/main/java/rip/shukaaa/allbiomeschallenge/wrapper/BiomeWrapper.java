package rip.shukaaa.allbiomeschallenge.wrapper;

import org.bukkit.block.Biome;

import java.util.ArrayList;

public class BiomeWrapper {
    public static Biome[] getAllBiomes() {
        // Wrapping Biomes because CHERRY_GROVE is 1.20+ and CUSTOM isn't a real biome
        Biome[] blacklistedBiomes = new Biome[]{Biome.CHERRY_GROVE, Biome.CUSTOM};
        Biome[] biomes = Biome.values();

        ArrayList<Biome> filteredBiomes = new ArrayList<>();
        for (Biome biome : biomes) {
            boolean blacklisted = false;

            for (Biome blacklistedBiome : blacklistedBiomes) {
                if (biome == blacklistedBiome) {
                    blacklisted = true;
                    break;
                }
            }

            if (!blacklisted) {
                filteredBiomes.add(biome);
            }
        }

        return filteredBiomes.toArray(new Biome[0]);
    }
}
