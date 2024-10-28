package xyz.jxmm.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.*;
import xyz.jxmm.Fps_on_Minecraft;

import java.util.List;

import static xyz.jxmm.Fps_on_Minecraft.plugin;
import static xyz.jxmm.map.config.MapList.SD_WORLDS;
import static xyz.jxmm.map.config.MapList.TEAM_SD_WORLDS;

public class PlayerJoin implements Listener {
    public static PlayerJoin instance;

    public PlayerJoin() {
        instance = this;
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        org.bukkit.entity.Player p = e.getPlayer();
        p.getInventory().clear();
        if (Fps_on_Minecraft.lobbyLocation != null){
            p.teleport(Fps_on_Minecraft.lobbyLocation);
        }
        p.sendTitle("§a§l欢迎来到§b§l終末牽挂§a§l的§b§lTACZ", "§a§lWelcome back!", 10, 70, 20);
        p.setGameMode(GameMode.ADVENTURE);

        if (plugin.getServer().getOnlinePlayers().size() == 1){

            List<World> worlds = plugin.getServer().getWorlds();
            for (World w : worlds){
                if (!w.getName().equalsIgnoreCase(Fps_on_Minecraft.lobbyWorld)){
                    if (TEAM_SD_WORLDS.contains(w.getName())){
                        try {
                            Team teamA = plugin.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam(w.getName() + "teamA");
                            Team teamB = plugin.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam(w.getName() + "teamB");

                            teamA.setColor(ChatColor.BLUE);
                            teamB.setColor(ChatColor.RED);

                            teamA.setCanSeeFriendlyInvisibles(true);
                            teamB.setCanSeeFriendlyInvisibles(true);

                            teamA.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
                            teamB.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);

                            teamA.addEntry("队伍A");
                            teamB.addEntry("队伍B");

                            teamA.setAllowFriendlyFire(false);
                            teamB.setAllowFriendlyFire(false);

                            plugin.getServer().getScoreboardManager().getMainScoreboard().registerNewObjective(w.getName() + "_score", Criteria.DUMMY, ChatColor.GOLD + "计分板");
                            plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective(w.getName() + "_score").setDisplaySlot(DisplaySlot.SIDEBAR);

                            plugin.getServer().getScoreboardManager().getMainScoreboard()
                                    .registerNewObjective(w.getName() + "_deathCount", Criteria.DUMMY, ChatColor.DARK_GRAY + w.getName() + "死亡数");
                            plugin.getServer().getScoreboardManager().getMainScoreboard()
                                    .registerNewObjective(w.getName() + "_killCount", Criteria.DUMMY, ChatColor.RED + w.getName() + "击杀数");
                        } catch (IllegalArgumentException exception){
                            Team teamA = plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam(w.getName() + "teamA");
                            Team teamB = plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam(w.getName() + "teamB");

                            Scoreboard sb = plugin.getServer().getScoreboardManager().getMainScoreboard();
                            Objective objective = sb.getObjective(w.getName() + "_score");
                            Objective objective2 = sb.getObjective(w.getName() + "_killCount");
                            Objective objective3 = sb.getObjective(w.getName() + "_deathCount");

                            if (objective != null){
                                objective.unregister();
                            }
                            if (objective2 != null){
                                objective2.unregister();
                            }
                            if (objective3 != null){
                                objective3.unregister();
                            }

                            if (teamA != null) {
                                for (String entry : teamA.getEntries()){
                                    teamA.removeEntry(entry);
                                }
                            }
                            if (teamB != null) {
                                for (String entry : teamB.getEntries())
                                    teamB.removeEntry(entry);
                            }

                            plugin.getServer().getScoreboardManager().getMainScoreboard().registerNewObjective(w.getName() + "_score", Criteria.DUMMY, ChatColor.GOLD + "计分板");
                            plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective(w.getName() + "_score").setDisplaySlot(DisplaySlot.SIDEBAR);

                            plugin.getServer().getScoreboardManager().getMainScoreboard()
                                    .registerNewObjective(w.getName() + "_deathCount", Criteria.DUMMY, ChatColor.DARK_GRAY + w.getName() + "死亡数");
                            plugin.getServer().getScoreboardManager().getMainScoreboard()
                                    .registerNewObjective(w.getName() + "_killCount", Criteria.DUMMY, ChatColor.RED + w.getName() + "击杀数");

                        }
                    } else if (SD_WORLDS.contains(w.getName())){
                        try{
                            plugin.getServer().getScoreboardManager().getMainScoreboard()
                                    .registerNewObjective(w.getName() + "_killCount", Criteria.DUMMY, ChatColor.RED + w.getName() + "击杀数")
                                    .setDisplaySlot(DisplaySlot.SIDEBAR);
                            plugin.getServer().getScoreboardManager().getMainScoreboard()
                                    .registerNewObjective(w.getName() + "_deathCount", Criteria.DUMMY, ChatColor.BLUE + w.getName() + "死亡数")
                                    .setDisplaySlot(DisplaySlot.PLAYER_LIST);
                        } catch (IllegalArgumentException exception){
                            plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective(w.getName() + "_killCount").unregister();
                            plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective(w.getName() + "_deathCount").unregister();
                            Objective killCount = plugin.getServer().getScoreboardManager()
                                    .getMainScoreboard()
                                    .registerNewObjective(w.getName() + "_killCount", Criteria.DUMMY, ChatColor.RED + w.getName() + "击杀数");
                            killCount.setDisplaySlot(DisplaySlot.SIDEBAR);

                            Objective deathCount = plugin.getServer().getScoreboardManager().
                                    getMainScoreboard()
                                    .registerNewObjective(w.getName() + "_deathCount", Criteria.DUMMY, ChatColor.BLUE + w.getName() + "死亡数");
                            deathCount.setDisplaySlot(DisplaySlot.PLAYER_LIST);

                        }
                    }

                }
            }
        }
    }
}