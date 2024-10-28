package xyz.jxmm.gaming;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static xyz.jxmm.Fps_on_Minecraft.mainCmd;
import static xyz.jxmm.Fps_on_Minecraft.plugin;
import static xyz.jxmm.gaming.team_sd.TeamPlayerList.*;
import static xyz.jxmm.utils.FileReaderMethod.fileReader;
import static xyz.jxmm.utils.ItemStackFromBase64.itemStackFromBase64;

public class ModeTeamSD {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private Player player;
    private final String folder = plugin.getDataFolder() + "/arenas/";
    private World world;
    private final Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
    private String worldName;
    private int score;

    public ModeTeamSD(Player player){
        this.player = player;
        this.world = player.getWorld();
        this.worldName = player.getWorld().getName();

        if (playerListA.contains(player)){
            score = scoreboard.getObjective(worldName + "_score").getScore("teamA").getScore();
        } else if (playerListB.contains(player)){
            score = scoreboard.getObjective(worldName + "_score").getScore("teamB").getScore();
        }

        player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(Integer.MAX_VALUE,1));
    }

    public void firstJoin(){
        if (playerListA.contains(player)){
            player.setFlying(false);
            player.setAllowFlight(false);
            player.setNoDamageTicks(0);
            player.setScoreboard(scoreboard.getObjective(worldName + "_score").getScoreboard());
            // player.getWorld().setGameRule(GameRule.KEEP_INVENTORY, true);

            for (PotionEffectType potionEffectType : PotionEffectType.values()) {
                player.removePotionEffect(potionEffectType);
            }
            player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(Integer.MAX_VALUE,1));

            JsonArray locations = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("TeamARespawnPoints").getAsJsonArray();
            List<Location> locationList = new ArrayList<>();
            for (int i = 0; i < locations.size(); i++) {
                double x = locations.get(i).getAsJsonObject().get("x").getAsDouble();
                double y = locations.get(i).getAsJsonObject().get("y").getAsDouble();
                double z = locations.get(i).getAsJsonObject().get("z").getAsDouble();
                float yaw = locations.get(i).getAsJsonObject().get("yaw").getAsFloat();
                float pitch = locations.get(i).getAsJsonObject().get("pitch").getAsFloat();

                Location loc = new Location(world, x, y, z, yaw, pitch);
                locationList.add(loc);
            }

            if (playerListA.size() > locationList.size()){
                player.sendMessage(ChatColor.RED + "队伍人数大于该队伍的出生点数量!");
                player.sendMessage(ChatColor.RED + "请选择其他队伍或联系管理!");
            } else {
                player.teleport(Objects.requireNonNull(player.getBedSpawnLocation()));
//                int n = playerListA.size() - 1;
//                player.setBedSpawnLocation(locationList.get(n), true);
//                player.teleport(locationList.get(n));
            }

        } else if (playerListB.contains(player)){
            player.setFlying(false);
            player.setAllowFlight(false);
            player.setNoDamageTicks(0);

            for (PotionEffectType potionEffectType : PotionEffectType.values()) {
                player.removePotionEffect(potionEffectType);
            }
            player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(Integer.MAX_VALUE,1));

            JsonArray locations = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("TeamBRespawnPoints").getAsJsonArray();
            List<Location> locationList = new ArrayList<>();
            for (int i = 0; i < locations.size(); i++) {
                double x = locations.get(i).getAsJsonObject().get("x").getAsDouble();
                double y = locations.get(i).getAsJsonObject().get("y").getAsDouble();
                double z = locations.get(i).getAsJsonObject().get("z").getAsDouble();
                float yaw = locations.get(i).getAsJsonObject().get("yaw").getAsFloat();
                float pitch = locations.get(i).getAsJsonObject().get("pitch").getAsFloat();

                Location loc = new Location(world, x, y, z, yaw, pitch);
                locationList.add(loc);
            }

            if (playerListB.size() > locationList.size()){
                player.sendMessage(ChatColor.RED + "队伍人数大于该队伍的出生点数量!");
                player.sendMessage(ChatColor.RED + "请选择其他队伍或联系管理!");
            } else {
                player.teleport(Objects.requireNonNull(player.getBedSpawnLocation()));
//                int n = playerListB.size() - 1;
//                player.setBedSpawnLocation(locationList.get(n), true);
//                player.teleport(locationList.get(n));
            }
        } else {
            player.sendMessage(ChatColor.RED + "加入任意队伍后可进入游戏!");
        }
    }

    public void respawn(){
        JsonArray locations = new JsonArray();
        if (playerListA.contains(player)){
            locations = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("TeamARespawnPoints").getAsJsonArray();
        } else if (playerListB.contains(player)){
            locations = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("TeamBRespawnPoints").getAsJsonArray();
        }
        player.setGameMode(GameMode.ADVENTURE);

        List<Location> locationList = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            double x = locations.get(i).getAsJsonObject().get("x").getAsDouble();
            double y = locations.get(i).getAsJsonObject().get("y").getAsDouble();
            double z = locations.get(i).getAsJsonObject().get("z").getAsDouble();
            float yaw = locations.get(i).getAsJsonObject().get("yaw").getAsFloat();
            float pitch = locations.get(i).getAsJsonObject().get("pitch").getAsFloat();

            Location loc = new Location(world, x, y, z, yaw, pitch);
            locationList.add(loc);
        }
        int n = playerListA.size() - 1;
//        player.setBedSpawnLocation(locationList.get(n), true);
//        player.teleport(locationList.get(n));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));

        Objective score = scoreboard.getObjective(worldName + "_score");
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        score.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(score.getScoreboard());
    }

    public void win(){
        for (int i = 0; i < player.getWorld().getPlayers().size(); i++) {
            if (!spectatorList.contains(player)){
                player.chat("/" + mainCmd + " exit");
            }
        }
        Objective score = plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective(worldName + "_score");
        score.getScore("队伍A").setScore(0);
        score.getScore("队伍B").setScore(0);
    }

    public void giveDefaultItem(){
        JsonArray defaultItems;
        try {
            defaultItems = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("items").getAsJsonArray();
            List<ItemStack> defaultItemsList = new ArrayList<>();
            for (int i = 0; i < defaultItems.size(); i++) {
                String item = defaultItems.get(i).getAsString();
                ItemStack itemStack = itemStackFromBase64(item);
                defaultItemsList.add(itemStack);
            }

            for (ItemStack itemStack : defaultItemsList){
                if (!player.getInventory().contains(itemStack)){
                    player.getInventory().addItem(itemStack);
                }

            }
        } catch (NullPointerException e){
//            plugin.getLogger().warning("未找到该地图的默认物品!");
        }



    }
}
