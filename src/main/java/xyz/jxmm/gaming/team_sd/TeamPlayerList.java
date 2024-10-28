package xyz.jxmm.gaming.team_sd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

import static xyz.jxmm.Fps_on_Minecraft.plugin;
import static xyz.jxmm.utils.FileReaderMethod.fileReader;

public class TeamPlayerList {
    public static List<Player> playerListA = new ArrayList<>();
    public static List<Player> playerListB = new ArrayList<>();
    public static List<Player> spectatorList = new ArrayList<>();

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void main(Player p, String teamName){
        String worldName = p.getWorld().getName();
        teamName = teamName.replaceAll(worldName,"");

        for (Team team : plugin.getServer().getScoreboardManager().getMainScoreboard().getTeams()){
            if (team.hasPlayer(p)){
                team.removePlayer(p);
                playerListA.remove(p);
                playerListB.remove(p);
            }
        }
        Team team = plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam(worldName + teamName);
        team.addPlayer(p);

        switch (teamName){
            case "teamA": {
                playerListA.add(p);
                break;
            }
            case "teamB": {
                playerListB.add(p);
            }
        }


        String folder = plugin.getDataFolder() + "/arenas/";
        World world = p.getWorld();

        JsonArray locations = new JsonArray();
        if (playerListA.contains(p)){
            locations = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("TeamARespawnPoints").getAsJsonArray();
        } else if (playerListB.contains(p)){
            locations = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class).get("TeamBRespawnPoints").getAsJsonArray();
        }

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

        if (playerListA.contains(p)){
            int n = playerListA.size() - 1;
            p.setBedSpawnLocation(locationList.get(n), true);
          } else if (playerListB.contains(p)){
            int n = playerListB.size() - 1;
            p.setBedSpawnLocation(locationList.get(n), true);
           }

    }

    /**
     * 处理玩家是否加入观战
     *
     * @param p  要操作的玩家
     * @param target
     *
     * "join" - 加入观战
     * "quit" - 退出观战
     */
    public static void spectator(Player p, String target){
        switch (target) {
            case "join" -> spectatorList.add(p);
            case "quit" -> spectatorList.remove(p);
        }
        // spectatorList 去重
        spectatorList = new ArrayList<>(new java.util.LinkedHashSet<>(spectatorList));
    }
}
