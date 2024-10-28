package xyz.jxmm.map.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.map.SetupSession;
import xyz.jxmm.utils.FileReaderMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapList {
    static Plugin plugin = Fps_on_Minecraft.getPlugin();
    static File file = new File(plugin.getDataFolder() + "\\arenas");
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public final static List<String> SD_WORLDS = new ArrayList<>();
    public final static List<String> TEAM_SD_WORLDS = new ArrayList<>();
    public static void main(){
        //遍历file目录下的所有文件
        for (File f : file.listFiles()) {
            //判断是否是文件夹
            if (!f.isDirectory()) {
                JsonObject json = gson.fromJson(FileReaderMethod.fileReader(f.getPath()), JsonObject.class);
                String worldName = json.get("world").getAsString();
                JsonObject loc = json.getAsJsonObject("lobbyLoc");

                switch (json.get("mode").getAsString()){
                    case "sd":
                        SD_WORLDS.add(worldName);
                        break;
                    case "team-sd":
                        TEAM_SD_WORLDS.add(worldName);
                        break;
                }

                loadWorld(worldName, loc);

            }
        }
    }
    public static void loadWorld(String worldName, JsonObject loc){
        WorldCreator wc = new WorldCreator(worldName);
        Bukkit.getScheduler().runTask(plugin, () -> {
            try {
                File level = new File(Bukkit.getWorldContainer(), worldName + "/region");
                if (level.exists()) {
                    plugin.getLogger().info(ChatColor.GREEN + "Loading " + worldName + " from Bukkit worlds container.");
                    SetupSession.deleteWorldTrash(worldName);
                    World w = Bukkit.createWorld(wc);
                    w.setKeepSpawnInMemory(true);
                    w.setGameRule(GameRule.NATURAL_REGENERATION, false);
                    w.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
                    w.setGameRule(GameRule.KEEP_INVENTORY, true);
                    w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                    w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                    w.setSpawnLocation(
                            loc.get("x").getAsInt(),
                            loc.get("y").getAsInt(),
                            loc.get("z").getAsInt());

                } else {
                    try {
                        plugin.getLogger().info(ChatColor.GREEN + "Creating a new void map: " + worldName);
                        World w = Bukkit.createWorld(wc);
                        plugin.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam(worldName);
                        w.setKeepSpawnInMemory(true);
                        w.setSpawnLocation(0, 64, 0);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
