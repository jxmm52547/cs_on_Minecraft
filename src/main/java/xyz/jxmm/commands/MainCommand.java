package xyz.jxmm.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;
import xyz.jxmm.commands.admin.Admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommand extends Command implements ParentCommand {
    private static List<SubCommand> subCommandList = new ArrayList<>();
    private String name;
    public static MainCommand instance;

    public MainCommand(String name) {
        super(name);
        this.name = name;
        new Admin(this, "admin");
        new Join(this, "join");
        new Exit(this, "exit");

    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("This command is for player");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(" ");
            sender.sendMessage("§8§l|-" + " §6" +
                    Fps_on_Minecraft.getPlugin().getDescription().getName() + " v" +
                    Fps_on_Minecraft.getPlugin().getDescription().getVersion() + " §7- §cCommands");
            sender.sendMessage(" ");
            sender.sendMessage(s);
            sender.sendMessage(args);
            return true;
        }

        boolean commandFound = false;
        for (SubCommand sb : getSubCommands()) {
            if (sb.getSubCommandName().equalsIgnoreCase(args[0])) {
               commandFound = (!sb.needOp() || (sb.needOp() && sender.isOp())) && sb.execute(Arrays.copyOfRange(args, 1, args.length), sender);


            }
        }

        if (!commandFound) {
            sender.sendMessage("§c未知指令!");
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        if (args.length == 1) {
            if (sender.isOp()){
                // 如果玩家为OP 则显示所有子指令
                List<String> sub = new ArrayList<>();
                for (SubCommand sb : getSubCommands()) {
                    sub.add(sb.getSubCommandName());
                }
                return sub;
            } else {
                List<String> sub = new ArrayList<>();
                for (SubCommand sb : getSubCommands()) {
                    // 如果该指令不需要OP 则显示
                    if (!sb.needOp()) sub.add(sb.getSubCommandName());

                }
                return sub;
            }

        } else if (args.length > 1){
            return getSubCommand(args[0]).getTabComplete(sender,alias,Arrays.copyOfRange(args, 1, args.length), null);
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
        for (SubCommand sc : getSubCommands()) {
            if (sc.getSubCommandName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
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

    public SubCommand getSubCommand(String name) {
        for (SubCommand sc : getSubCommands()) {
            if (sc.getSubCommandName().equalsIgnoreCase(name)) {
                return sc;
            }
        }
        return null;
    }
}
