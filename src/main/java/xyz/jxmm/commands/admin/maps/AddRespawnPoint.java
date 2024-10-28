package xyz.jxmm.commands.admin.maps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;
import xyz.jxmm.utils.FileReaderMethod;
import xyz.jxmm.utils.FileWriterMethod;

import java.util.List;

public class AddRespawnPoint extends SubCommand {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public AddRespawnPoint(ParentCommand parent, String name) {
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
        if (!(s instanceof Player)){
            s.sendMessage("This command is for player");
            return false;
        }

        Player p = (Player) s;
        String filePath = Fps_on_Minecraft.getPlugin().getDataFolder().toPath() + "/arenas/" + p.getWorld().getName() + ".json";

        JsonObject json = gson.fromJson(FileReaderMethod.fileReader(filePath), JsonObject.class);
        JsonArray respawnPoints = json.has("respawnPoints") ? json.get("respawnPoints").getAsJsonArray() : new JsonArray();

        JsonObject loc = new JsonObject();
        loc.addProperty("x", p.getLocation().getX());
        loc.addProperty("y", p.getLocation().getY());
        loc.addProperty("z", p.getLocation().getZ());
        loc.addProperty("yaw", p.getLocation().getYaw());
        loc.addProperty("pitch", p.getLocation().getPitch());

        respawnPoints.add(loc);
        json.add("respawnPoints", respawnPoints);
        FileWriterMethod.fileWriter(filePath, gson.toJson(json));
        p.sendMessage(ChatColor.BLUE + "Added respawn point at " + ChatColor.GREEN + p.getLocation());

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
