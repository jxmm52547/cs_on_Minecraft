package xyz.jxmm.gaming;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import xyz.jxmm.Cs_on_Minecraft;
import xyz.jxmm.gaming.team_sd.TeamPlayerList;
import xyz.jxmm.gaming.team_sd.inventory.WeaponsMenuItems;

import java.util.Objects;

import static xyz.jxmm.Cs_on_Minecraft.plugin;
import static xyz.jxmm.utils.FileReaderMethod.fileReader;

public class CheckMode {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final Player player;
    private final String folder = plugin.getDataFolder() + "/arenas/";
    private final World world;

    public CheckMode(Player player){
        this.player = player;
        this.world = player.getWorld();
    }

    public void join(){
        JsonObject jsonObject = gson.fromJson(fileReader(folder + world.getName() + ".json"), JsonObject.class);
        String mode = jsonObject.get("mode").getAsString();
        switch (mode) {
            case "sd" -> new ModeSD(player).firstJoin();
            case "team-sd" -> new ModeTeamSD(player).firstJoin();
        }

        // 将玩家移出旁观者列表
        TeamPlayerList.spectator(player, "quit");
    }


    /**
     *
     * @param worldName 世界名称
     * @param joinOrWait 是否为等待程序
     *                   -true:等待程序
     *                   -false:从主大厅加入
     */
    public void waiting(String worldName, boolean joinOrWait){
        JsonObject jsonObject = gson.fromJson(fileReader(folder + worldName + ".json"), JsonObject.class);
        String mode = jsonObject.get("mode").getAsString();
        World w = plugin.getServer().getWorld(worldName);
        switch (mode) {
            case "sd":
                player.teleport(w.getSpawnLocation());
//                w.setKeepSpawnInMemory(true);
                player.setGameMode(GameMode.SPECTATOR);
                player.setScoreboard(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getMainScoreboard().getObjective(worldName + "_killCount")).getScoreboard()));
                break;
            case "team-sd":
                player.getInventory().clear();
//                w.setKeepSpawnInMemory(true);
                player.teleport(w.getSpawnLocation());
                player.setGameMode(GameMode.ADVENTURE);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setNoDamageTicks(Integer.MAX_VALUE);
                player.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(Integer.MAX_VALUE, 255));
                player.addPotionEffect(PotionEffectType.GLOWING.createEffect(Integer.MAX_VALUE, 255));
                player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(Integer.MAX_VALUE, 255));
                player.getInventory().addItem(WeaponsMenuItems.OPEN_MENU);
                player.setScoreboard(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(plugin.getServer().getScoreboardManager()).getMainScoreboard().getObjective(worldName + "_score")).getScoreboard()));
                break;
        }

        // 将玩家加入旁观者列表
        TeamPlayerList.spectator(player, "join");

        if (joinOrWait){
            player.sendTitle("§a§l已切换至旁观模式", "§a§l休息会吗", 10, 70, 20);
//            player.setBedSpawnLocation(Cs_on_Minecraft.lobbyLocation,true);
        } else {
            player.sendTitle(ChatColor.GREEN + "You have joined " + worldName, ChatColor.AQUA + "Have fun!", 10, 70, 20);
        }
    }
}
