package xyz.jxmm.commands.admin.maps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.jxmm.Fps_on_Minecraft;
import xyz.jxmm.api.command.ParentCommand;
import xyz.jxmm.api.command.SubCommand;
import xyz.jxmm.utils.FileReaderMethod;

import java.util.*;

import static xyz.jxmm.utils.FileWriterMethod.fileWriter;
import static xyz.jxmm.utils.ItemStackToBase64.itemStackToBase64;

public class AddWeapon extends SubCommand {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    /**
     * @param parent parent command
     * @param name   sub-command name
     */
    public AddWeapon(ParentCommand parent, String name) {
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

        Player player = (Player) s;
        ItemStack item =  player.getInventory().getItemInMainHand();
        if (item.getAmount() == 0){
            player.sendMessage(ChatColor.RED + "请手持武器!");
            return false;
        } else {
            player.sendMessage("Added weapon");
            String filePath = Fps_on_Minecraft.getPlugin().getDataFolder().toPath() + "/arenas/" + player.getWorld().getName() + ".json";
            JsonObject json = gson.fromJson(FileReaderMethod.fileReader(filePath), JsonObject.class);
            JsonArray weapons = json.has("weapons") ? json.get("weapons").getAsJsonArray() : new JsonArray();
            weapons.add(itemStackToBase64(item));
            json.add("weapons", weapons);
            fileWriter(filePath, gson.toJson(json));
            return true;
        }
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
