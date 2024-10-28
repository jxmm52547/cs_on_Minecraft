package xyz.jxmm.commands.admin.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeleteMap extends SubCommand {
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public DeleteMap(ParentCommand parent, String name) {
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
        if (!(s instanceof Player)) {
            s.sendMessage("This command is for player");
            return true;
        }

        if (args.length > 0){
            String worldName = args[0];
            World world = Bukkit.getWorld(worldName);
            if (world != null){
                Bukkit.unloadWorld(world, false);
                Bukkit.getWorlds().remove(world);
                return true;
            } else {
                s.sendMessage("World not found");
                return false;
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
        if (args.length == 1) {
            return getWorldsList();
        }
        return null;
    }

    public List<String> getWorldsList() {
        List<String> worlds = new ArrayList<>();
        File dir = Bukkit.getWorldContainer();
        if (dir.exists()) {
            File[] fls = dir.listFiles();
            for (File fl : Objects.requireNonNull(fls)) {
                if (fl.isDirectory()) {
                    File dat = new File(fl.getName() + "/region");
                    if (dat.exists() && !fl.getName().startsWith("bw_temp")) {
                        worlds.add(fl.getName());
                    }
                }
            }
        }
        worlds.remove(Fps_on_Minecraft.lobbyWorld);

        return worlds;
    }
}
