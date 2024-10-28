package xyz.jxmm.commands.admin.maps.team;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;
import xyz.jxmm.utils.FileReaderMethod;
import xyz.jxmm.utils.FileWriterMethod;

import java.util.List;

public class AddTeamSpawnPoint extends SubCommand {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public AddTeamSpawnPoint(ParentCommand parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;

        if (args.length < 1) s.sendMessage(ChatColor.RED + "非法使用");

        Player p = (Player) s;
        String filePath = Fps_on_Minecraft.getPlugin().getDataFolder().toPath() + "/arenas/" + p.getWorld().getName() + ".json";
        JsonObject json = gson.fromJson(FileReaderMethod.fileReader(filePath), JsonObject.class);

        if (!(json.get("mode").getAsString().equalsIgnoreCase("team-sd"))){
            s.sendMessage(ChatColor.RED + "当前模式不是团队死斗模式");
            return true;
        }

        JsonArray spawnPoints = new JsonArray();
        switch (args[0]){
            case "A" -> spawnPoints = json.has("TeamARespawnPoints") ? json.get("TeamARespawnPoints").getAsJsonArray() : new JsonArray();
            case "B" -> spawnPoints = json.has("TeamBRespawnPoints") ? json.get("TeamBRespawnPoints").getAsJsonArray() : new JsonArray();
        }

        JsonObject loc = new JsonObject();
        loc.addProperty("x", p.getLocation().getX());
        loc.addProperty("y", p.getLocation().getY());
        loc.addProperty("z", p.getLocation().getZ());
        loc.addProperty("yaw", p.getLocation().getYaw());
        loc.addProperty("pitch", p.getLocation().getPitch());

        spawnPoints.add(loc);
        json.add("Team" + args[0] + "RespawnPoints", spawnPoints);
        FileWriterMethod.fileWriter(filePath, gson.toJson(json));
        p.sendMessage(ChatColor.GREEN + "已添加 Team"+ ChatColor.GOLD + "     ▪     " + args[0] + "     ▪     " + ChatColor.GREEN + "复活点");
        p.sendMessage(ChatColor.GREEN + "当前队伍已有 " + spawnPoints.size() + " 个复活点");

        return true;
    }

    @Override
    public boolean needOp() {
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender s, String alias, String[] args, Location location) {
        return List.of();
    }
}
