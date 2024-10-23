package xyz.jxmm.commands.admin.maps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import xyz.jxmm.Cs_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;

import java.util.List;
import java.util.function.Supplier;

import static xyz.jxmm.Cs_on_Minecraft.lobbyWorld;
import static xyz.jxmm.Cs_on_Minecraft.plugin;
import static xyz.jxmm.utils.FileReaderMethod.fileReader;

public class ReStart extends SubCommand implements ParentCommand {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final String folder = plugin.getDataFolder() + "/arenas/";
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public ReStart(ParentCommand parent, String name) {
        super(parent, name);
    }

    /**
     * Add your sub-command code under this method
     *
     * @param args
     * @param s
     */
    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;

        if (args.length == 0){
            for (Player all : ((Player) s).getWorld().getPlayers()){
                all.chat("/" + Cs_on_Minecraft.mainCmd + " exit");
            }
            return true;
        } else {
            if (args[0].equalsIgnoreCase(lobbyWorld)){
                s.sendMessage( ChatColor.RED + "无法对大厅使用该指令");
            } else {
                String worldName = args[0] + ".json";
                String mode = gson.fromJson(fileReader(folder + worldName), JsonObject.class).get("mode").getAsString();
                switch (mode){
                    case "team-sd":
                        Objective score = plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective(worldName + "_score");
                        if (score != null) {
                            score.getScore("队伍A").setScore(0);
                            score.getScore("队伍B").setScore(0);
                        }
                        break;

                }
                for (Player all : plugin.getServer().getWorld(args[0]).getPlayers()){
                    all.chat("/" + Cs_on_Minecraft.mainCmd + " exit");
                }
                return true;
            }

        }

        return true;
    }

    @Override
    public boolean needOp() {
        return true;
    }

    /**
     * Manage sub-command tab complete
     *
     * @param s
     * @param alias
     * @param args
     * @param location
     */
    @Override
    public List<String> getTabComplete(CommandSender s, String alias, String[] args, Location location) {
        return tabComplete(s, alias, args, location);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String alias, String[] args, Location location) throws IllegalArgumentException {
        if (args.length == 1) return plugin.getServer().getWorlds().stream().map(World::getName).toList();
        return List.of();
    }

    @Override
    public boolean hasSubCommand(String name) {
        return false;
    }

    @Override
    public void addSubCommand(SubCommand subCommand) {

    }

    @Override
    public void sendSubCommands(Player p) {

    }

    @Override
    public List<SubCommand> getSubCommands() {
        return List.of();
    }

    @Override
    public String getName() {
        return "";
    }
}
