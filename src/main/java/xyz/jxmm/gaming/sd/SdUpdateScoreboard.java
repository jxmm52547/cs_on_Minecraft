package xyz.jxmm.gaming.sd;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import static xyz.jxmm.Fps_on_Minecraft.plugin;

public class SdUpdateScoreboard {
    private Player player;
    private final Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
    private String worldName;

    public SdUpdateScoreboard(Player player){
        this.player = player;
        this.worldName = player.getWorld().getName();
    }

    public void death(){
        Objective deathCount = scoreboard.getObjective(worldName + "_deathCount");
        if (deathCount != null) {
            int score = deathCount.getScore(player.getName()).getScore();
            deathCount.getScore(player.getName()).setScore(score+1);
        }
        plugin.getServer().getScoreboardManager().getMainScoreboard().clearSlot(DisplaySlot.PLAYER_LIST);
        deathCount.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }

    public void kill(){
        Objective killCount = scoreboard.getObjective(worldName + "_killCount");
        if (killCount != null) {
            int score = killCount.getScore(player.getName()).getScore();
            killCount.getScore(player.getName()).setScore(score+1);

            if (score % 4 == 0){

            }
        }


        plugin.getServer().getScoreboardManager().getMainScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        killCount.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

    public void clear(){
        Objective killCount = scoreboard.getObjective(worldName + "_killCount");
        Objective deathCount = scoreboard.getObjective(worldName + "_deathCount");
        if (killCount != null) {
            killCount.getScoreboard().resetScores(player.getName());
        }
        if (deathCount != null) {
            deathCount.getScoreboard().resetScores(player.getName());
        }

        plugin.getServer().getScoreboardManager().getMainScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        plugin.getServer().getScoreboardManager().getMainScoreboard().clearSlot(DisplaySlot.PLAYER_LIST);
        killCount.setDisplaySlot(DisplaySlot.SIDEBAR);
        deathCount.setDisplaySlot(DisplaySlot.PLAYER_LIST);

    }
}
