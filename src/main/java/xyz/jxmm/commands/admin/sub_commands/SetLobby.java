package xyz.jxmm.commands.admin.sub_commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;

import java.util.List;

public class SetLobby extends SubCommand {
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public SetLobby(ParentCommand parent, String name) {
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
        Player p = (Player) s;
        xyz.jxmm.config.Change.change(new String[]{"lobby", "world"}, p.getLocation().getWorld().getName());

        int x = (int) p.getLocation().getX();
        int y = (int) p.getLocation().getY();
        int z = (int) p.getLocation().getZ();
        int yaw = (int) p.getLocation().getYaw();
        int pitch = (int) p.getLocation().getPitch();

        xyz.jxmm.config.Change.change(new String[]{"lobby", "x"}, String.valueOf(x));
        xyz.jxmm.config.Change.change(new String[]{"lobby", "y"}, String.valueOf(y));
        xyz.jxmm.config.Change.change(new String[]{"lobby", "z"}, String.valueOf(z));
        xyz.jxmm.config.Change.change(new String[]{"lobby", "yaw"}, String.valueOf(yaw));
        xyz.jxmm.config.Change.change(new String[]{"lobby", "pitch"}, String.valueOf(pitch));

        Fps_on_Minecraft.setLobbyLocation();
        s.sendMessage("设置成功");
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
        return List.of();
    }


}
