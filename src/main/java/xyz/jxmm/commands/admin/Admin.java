package xyz.jxmm.commands.admin;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;
import xyz.jxmm.commands.admin.maps.*;
import xyz.jxmm.commands.admin.maps.team.AddTeamSpawnPoint;
import xyz.jxmm.commands.admin.sub_commands.SetLobby;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin extends SubCommand implements ParentCommand {
    private static List<SubCommand> subCommandList = new ArrayList<>();
    private ParentCommand parten;
    private String name;
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public Admin(ParentCommand parent, String name) {
        super(parent, name);
        this.parten = parent;
        this.name = name;
        setDisplayInfo(new TextComponent("§c管理员指令"));
        addSubCommand(new SetLobby(this, "setupLobby"));
        addSubCommand(new CreateMap(this, "createMap"));
        addSubCommand(new SetWaitingSpawn(this, "setupWaitingSpawn"));
        addSubCommand(new BackToLobby(this,"backToLobby"));
        addSubCommand(new AddRespawnPoint(this,"addSpawnPoint"));
        addSubCommand(new EditMap(this,"editMap"));
        addSubCommand(new AddWeapon(this,"addWeapon"));
        addSubCommand(new AddDefaultItem(this,"addDefaultItem"));
        addSubCommand(new DeleteMap(this,"deleteMap"));
        addSubCommand(new ReStart(this,"restart"));
        addSubCommand(new AddTeamSpawnPoint(this,"addTeamSpawnPoint"));

    }

    @Override
    public List<String> tabComplete(CommandSender s, String alias, String[] args, Location location) throws IllegalArgumentException {
        if (args.length == 1) {
            List<String> sub = new ArrayList<>();
            for (SubCommand sb : getSubCommands()) {
                sub.add(sb.getSubCommandName());
            }
            return sub;
        } else if (args.length > 1){
            return getSubCommand(args[0]).getTabComplete(s,alias, Arrays.copyOfRange(args, 1, args.length), null);
        }

        return null;
    }

    /**
     * Check if a parent command has the target sub-command
     *
     * @param name
     */
    @Override
    public boolean hasSubCommand(String name) {
        return true;
    }

    /**
     * Add a subCommand
     *
     * @param subCommand
     */
    @Override
    public void addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
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
        return subCommandList;
    }

    /**
     * Get parent name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Add your sub-command code under this method
     *
     * @param args
     * @param s
     */
    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("This command is for player");
            return true;
        }

        if (args.length > 0) {
            for (SubCommand subCommand : subCommandList) {
                if (subCommand.getSubCommandName().equalsIgnoreCase(args[0])) {
                    if (s.isOp()) {
                        subCommand.execute(Arrays.copyOfRange(args, 1, args.length), s);
                    } else {
                        return false;
                    }
                    return true;
                }
            }
        }

        if (args.length == 0) {
            s.sendMessage(" ");
            s.sendMessage("§8§l|-" + " §6" +
                    Fps_on_Minecraft.getPlugin().getDescription().getName() + " v" +
                    Fps_on_Minecraft.getPlugin().getDescription().getVersion() + " §7- §cCommands");
            s.sendMessage(" ");
            s.sendMessage(getName());
            s.sendMessage(args);
            return true;
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

    public SubCommand getSubCommand(String name) {
        for (SubCommand sc : getSubCommands()) {
            if (sc.getSubCommandName().equalsIgnoreCase(name)) {
                return sc;
            }
        }
        return null;
    }
}
