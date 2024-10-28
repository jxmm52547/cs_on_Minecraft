package xyz.jxmm.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import static xyz.jxmm.Fps_on_Minecraft.plugin;
import static xyz.jxmm.utils.ItemStackToBase64.itemStackToBase64;

public class TestCommand extends Command {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public TestCommand(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player p = (Player) sender;
        ItemStack i = p.getInventory().getItemInMainHand();
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(ChatColor.BLUE + "加入队伍A");
        i.setItemMeta(m);
        plugin.getLogger().warning(itemStackToBase64(i));

        m.setDisplayName(ChatColor.RED + "加入队伍B");
        i.setItemMeta(m);
        plugin.getLogger().warning(itemStackToBase64(i));

        return true;
    }
}
