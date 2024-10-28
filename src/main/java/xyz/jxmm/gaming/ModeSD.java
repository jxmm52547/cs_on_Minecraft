package xyz.jxmm.gaming;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import xyz.jxmm.gaming.sd.SdUpdateScoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static xyz.jxmm.Fps_on_Minecraft.plugin;
import static xyz.jxmm.utils.FileReaderMethod.fileReader;
import static xyz.jxmm.utils.ItemStackFromBase64.itemStackFromBase64;

public class ModeSD {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private Player player;
    private final String folder = plugin.getDataFolder() + "/arenas/";
    private World world;
    private final Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
    private String worldName;
    private int score = 0;

    public ModeSD(Player player) {
        this.player = player;
        this.world = player.getWorld();
        this.worldName = player.getWorld().getName();

        this.score = scoreboard.getObjective(worldName + "_killCount").getScore(player.getName()).getScore();

        // 给予玩家生命恢复1无限时间的效果
        player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(Integer.MAX_VALUE,1));
    }

    public void firstJoin(){
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();

        Objective killCount = scoreboard.getObjective(worldName + "_killCount");
        if (killCount != null) {
            killCount.getScore(player.getName()).setScore(0);
            this.score = killCount.getScore(player.getName()).getScore();
        }

        JsonArray locations = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("respawnPoints").getAsJsonArray();
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
        player.setBedSpawnLocation(locationList.get(new Random().nextInt(locations.size())), true);
        respawn();

    }

    public void respawn(){
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        JsonArray locations = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("respawnPoints").getAsJsonArray();
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
        int n = new Random().nextInt(locations.size());
        plugin.getLogger().warning(String.valueOf(n));
        player.setBedSpawnLocation(locationList.get(n), true);
        player.teleport(locationList.get(n));
        this.giveWeapon();
        player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
    }

    public void giveDefaultItem(){
        JsonArray defaultItems = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("items").getAsJsonArray();
        List<ItemStack> defaultItemsList = new ArrayList<>();
        for (int i = 0; i < defaultItems.size(); i++) {
            String item = defaultItems.get(i).getAsString();
            ItemStack itemStack = itemStackFromBase64(item);
            defaultItemsList.add(itemStack);
        }

        for (ItemStack itemStack : defaultItemsList){
            player.getInventory().addItem(itemStack);
        }

    }

    public void giveWeapon(){
        player.getInventory().clear();
        JsonArray weapons = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("weapons").getAsJsonArray();
        List<ItemStack> weaponsList = new ArrayList<>();
        for (int i = 0; i < weapons.size(); i++) {
            String weapon = weapons.get(i).getAsString();
            ItemStack itemStack = itemStackFromBase64(weapon);
            weaponsList.add(itemStack);
        }
        if (score >= weaponsList.size()){
            player.setGameMode(GameMode.SPECTATOR);
            player.sendTitle(ChatColor.RED + "游戏结束", ChatColor.GREEN + "good job", 10, 30, 10);
            for (Player all : world.getPlayers()){
                all.sendMessage(ChatColor.AQUA + player.getName() + "已完成进度!");
            }
            new SdUpdateScoreboard(player).clear();
        } else {
            player.getInventory().addItem(weaponsList.get(score));
            this.giveDefaultItem();
        }


    }

    public void updateWeapon(){
        this.giveWeapon();
    }
}
