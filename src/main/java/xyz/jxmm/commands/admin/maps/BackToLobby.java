package xyz.jxmm.commands.admin.maps;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;

import java.util.List;

public class BackToLobby extends SubCommand {
    private ParentCommand parent;
    private String name;
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public BackToLobby(ParentCommand parent, String name) {
        super(parent, name);
        this.parent = parent;
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
        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("This command is for player");
            return false;
        }

        Player player = (Player) s;
        if (!player.getWorld().getName().equals(Fps_on_Minecraft.lobbyWorld)){
            player.teleport(Fps_on_Minecraft.lobbyLocation);
            player.setGameMode(GameMode.ADVENTURE);
        } else {
            player.sendMessage(ChatColor.RED + "You are already in lobby");
            return true;
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
        return List.of();
    }
}
