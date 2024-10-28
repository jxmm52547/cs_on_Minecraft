package xyz.jxmm.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.util.function.Supplier;

import static xyz.jxmm.Fps_on_Minecraft.plugin;

public class ItemStackFromBase64 {
    public static ItemStack itemStackFromBase64(String s) {
        if (s.trim().isEmpty()) return null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(Base64Coder.decodeLines(s))) {
            try (BukkitObjectInputStream out = new BukkitObjectInputStream(in)) {
                return (ItemStack) out.readObject();
            }
        } catch (Throwable t) {
            plugin.getLogger().warning((Supplier<String>) t);
            return null;
        }
    }
}
