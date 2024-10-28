package xyz.jxmm.commands.admin.maps;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;

import java.util.ArrayList;
import java.util.List;

public class EditMap extends SubCommand implements ParentCommand {
    private String name;
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public EditMap(ParentCommand parent, String name) {
        super(parent, name);
        this.name = name;
    }

    /**
     * Add your sub-command code under this method
     *
     * @param args
     * @param s
     */
    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (!(s instanceof Player)) return false;

        Player p = (Player) s;
        if (args.length == 1) {
            String worldName = args[0];
            for (World w : Bukkit.getWorlds()){
                if (!w.getName().equals(Fps_on_Minecraft.lobbyWorld) && w.getName().equalsIgnoreCase(worldName)){
                    p.setGameMode(GameMode.CREATIVE);
                    p.teleport(w.getSpawnLocation());
                    return true;
                }
            }
        }
        return false;
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
        List<String> list = new ArrayList<>();
        for (World w : Bukkit.getWorlds()){
            if (!w.getName().equals(Fps_on_Minecraft.lobbyWorld)){
                list.add(w.getName());
            }
        }
        return list;
    }

    /**
     * Check if a parent command has the target sub-command
     *
     * @param name
     */
    @Override
    public boolean hasSubCommand(String name) {
        return false;
    }

    /**
     * Add a subCommand
     *
     * @param subCommand
     */
    @Override
    public void addSubCommand(SubCommand subCommand) {

    }

    /**
     * Send sub-commands list to a player
     * This includes subCommands with showInList true only
     * He can see only commands which he have permission
     *
     * @param p
     */
    @Override
    public void sendSubCommands(Player p) {

    }

    /**
     * Get available subCommands
     */
    @Override
    public List<SubCommand> getSubCommands() {
        return List.of();
    }

    /**
     * Get parent name
     */
    @Override
    public String getName() {
        return name;
    }
}
