package xyz.jxmm.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import xyz.jxmm.Fps_on_Minecraft;

public class Lobby extends Command {
    public Lobby(String name) {
        super(name);
    }

    /**
     * Executes the command, returning its success
     *
     * @param sender       Source object which is executing this command
     * @param commandLabel The alias of the command used
     * @param args         All arguments passed to the command, split via ' '
     * @return true if the command was successful, otherwise false
     */
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("This command is for player");
            return false;
        }

        Player p = (Player) sender;
        p.setGameMode(GameMode.ADVENTURE);
        p.teleport(Fps_on_Minecraft.lobbyLocation);
        p.sendMessage("§a§l[§6§l終末牽挂§a§l] §7已传送至 §6lobby");
        p.setBedSpawnLocation(Fps_on_Minecraft.lobbyLocation, false);
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        return true;
    }
}
