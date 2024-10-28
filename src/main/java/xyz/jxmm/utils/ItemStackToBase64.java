package xyz.jxmm.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

import static xyz.jxmm.Fps_on_Minecraft.plugin;

public class ItemStackToBase64 {
    public static String itemStackToBase64(ItemStack item){
        if (item == null) return "";
        try(ByteArrayOutputStream output = new ByteArrayOutputStream()){
            try (BukkitObjectOutputStream out = new BukkitObjectOutputStream(output)){
                out.writeObject(item);
                return Base64Coder.encodeLines(output.toByteArray());
            }catch (Throwable t) {
                plugin.getLogger().warning((Supplier<String>) t);
                return "";
            }


    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
