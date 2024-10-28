package xyz.jxmm.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import xyz.jxmm.gaming.ModeSD;
import xyz.jxmm.gaming.ModeTeamSD;
import xyz.jxmm.gaming.sd.SdUpdateScoreboard;
import xyz.jxmm.gaming.team_sd.TeamSdUpdateScoreboard;

import static xyz.jxmm.Fps_on_Minecraft.plugin;
import static xyz.jxmm.map.config.MapList.SD_WORLDS;
import static xyz.jxmm.map.config.MapList.TEAM_SD_WORLDS;

public class PlayerRespawn implements Listener {
    public static PlayerRespawn instance;

    public PlayerRespawn() {
        instance = this;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(org.bukkit.event.player.PlayerRespawnEvent e) {

        try {
            if (SD_WORLDS.contains(e.getPlayer().getWorld().getName())){
                Player deathPlayer = e.getPlayer();
                new SdUpdateScoreboard(deathPlayer).death();

                Player killer = e.getPlayer().getKiller();
                if (killer != null) {
                    new SdUpdateScoreboard(killer).kill();
                    new ModeSD(killer).updateWeapon();
                }

                new ModeSD(deathPlayer).respawn();
                if (killer != null && killer.getHealth() + 4 <=20) {
                    killer.setHealth(killer.getHealth() + 4);
                }
            } else if (TEAM_SD_WORLDS.contains(e.getPlayer().getWorld().getName())){
                Player deathPlayer = e.getPlayer();
                new TeamSdUpdateScoreboard(deathPlayer).death();
                new ModeTeamSD(deathPlayer).respawn();
                Objective sb = plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective(deathPlayer.getWorld().getName() + "_score");
                plugin.getServer().getScoreboardManager().getMainScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                sb.setDisplaySlot(DisplaySlot.SIDEBAR);
                Player killer = e.getPlayer().getKiller();
                new TeamSdUpdateScoreboard(killer).kill();
            }

        } catch (Exception e1){
            plugin.getLogger().warning("PlayerRespawn Error");
            throw e1;
        }



    }

}
