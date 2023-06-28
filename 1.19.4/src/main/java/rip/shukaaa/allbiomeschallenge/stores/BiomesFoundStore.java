package rip.shukaaa.allbiomeschallenge.stores;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.UUID;

public class BiomesFoundStore {
    // Content of json looks like this:
    // {
    //     "player": [
    //         {
    //             "uuid": "uuid",
    //             "visitedBiomes": [
    //                 "biome1",
    //                 "biome2"
    //             ],
    //             "nextBiomeToVisit": "biome3"
    //         }
    //     ]
    // }

    // Storing data in json files

    private static final String path = System.getProperty("user.dir") + "\\biomesFound.json";
    private static JSONObject data;

    public static void checkFile() throws IOException {
        File file = new File(path);

        if (!file.exists()) {
            if (file.createNewFile()) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AllBiomesChallenge] File created!");
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[AllBiomesChallenge] File could not be created!");
            }

            String data = "{\"player\":[]}";

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(data);
            writer.close();
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AllBiomesChallenge] Data loaded successfully!");
        }

        getData();
    }

    private static void readData() throws IOException {
        File file = new File(path);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();

        BiomesFoundStore.data = new JSONObject(builder.toString());
    }

    private static JSONObject getData() throws IOException {
        if (data == null) {
            readData();
        }

        return data;
    }

    private static void setData(JSONObject data) throws IOException {
        File file = new File(path);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(data.toString());
        writer.close();

        BiomesFoundStore.data = data;
    }

    public static Biome[] getVisitedBiomes(UUID uuid) throws IOException {
        JSONObject data = getData();

        for (Object p : data.getJSONArray("player")) {
            JSONObject player = (JSONObject) p;

            if (player.getString("uuid").equals(uuid.toString())) {
                JSONArray biomesJson = player.getJSONArray("visitedBiomes");
                Biome[] biomes = new Biome[biomesJson.length()];

                for (int i = 0; i < biomes.length; i++) {
                    biomes[i] = Biome.valueOf(player.getJSONArray("visitedBiomes").getString(i));
                }

                return biomes;
            }
        }

        return null;
    }

    public static void addBiomeToList(UUID uuid, Biome biome) throws IOException {
        JSONObject data = getData();

        for (Object p : data.getJSONArray("player")) {
            JSONObject player = (JSONObject) p;

            if (player.getString("uuid").equals(uuid.toString())) {
                // Check if biome is already in list
                for (Object b : player.getJSONArray("visitedBiomes")) {
                    if (b.equals(biome.toString())) {
                        return;
                    }
                }

                player.getJSONArray("visitedBiomes").put(biome.toString());
            }
        }

        setData(data);
    }

    public static Biome getNextBiomeToVisit(UUID uuid) throws IOException {
        JSONObject data = getData();

        for (Object p : data.getJSONArray("player")) {
            JSONObject player = (JSONObject) p;

            if (player.getString("uuid").equals(uuid.toString())) {
                return Biome.valueOf(player.getString("nextBiomeToVisit"));
            }
        }

        return null;
    }

    public static void setNextBiomeToVisit(UUID uuid, Biome biome) throws IOException {
        JSONObject data = getData();

        for (Object p : data.getJSONArray("player")) {
            JSONObject player = (JSONObject) p;

            if (player.getString("uuid").equals(uuid.toString())) {
                player.put("nextBiomeToVisit", biome.toString());
            }
        }

        setData(data);
    }

    public static boolean hasPlayer(UUID uuid) throws IOException {
        JSONObject data = getData();

        for (Object p : data.getJSONArray("player")) {
            JSONObject player = (JSONObject) p;

            if (player.getString("uuid").equals(uuid.toString())) {
                return true;
            }
        }

        return false;
    }

     public static void addPlayer(UUID uuid, Biome biome) throws IOException {
         JSONObject data = getData();

         JSONObject player = new JSONObject();
         player.put("uuid", uuid.toString());
         player.put("visitedBiomes", new JSONArray());
         player.put("nextBiomeToVisit", biome.toString());

         data.getJSONArray("player").put(player);

         setData(data);
     }
}
