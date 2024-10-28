package xyz.jxmm.map;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

import static xyz.jxmm.Fps_on_Minecraft.plugin;

public class CreateScoreboard {
    public CreateScoreboard() {
        try {
            plugin.getServer().getScoreboardManager().getMainScoreboard()
                    .registerNewObjective("Health", Criteria.HEALTH, ChatColor.RED + "❤")
                    .setDisplaySlot(DisplaySlot.BELOW_NAME);
        } catch (IllegalArgumentException e){
            plugin.getLogger().info("已存在Health计分板，已跳过");
        }
    }

    public static void load(){

    }
}
