package xyz.jxmm.events;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffectType;
import xyz.jxmm.Fps_on_Minecraft;

public class PlayerWorldChange implements Listener {
    //玩家切换世界事件
    public static PlayerWorldChange instance;

    public PlayerWorldChange() {
        instance = this;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerWorldChange(PlayerChangedWorldEvent e){
        Player p = e.getPlayer();
        World w = p.getWorld();

        if (w.getName().equals(Fps_on_Minecraft.lobbyWorld)){
            p.getInventory().clear();
            p.setGameMode(GameMode.ADVENTURE);
            p.setFlying(false);
            p.setAllowFlight(false);
            for (PotionEffectType potionEffectType : PotionEffectType.values()) {
                p.removePotionEffect(potionEffectType);
            }
        }

    }

}

