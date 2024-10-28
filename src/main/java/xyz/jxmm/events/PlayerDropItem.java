package xyz.jxmm.events;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import static xyz.jxmm.map.config.MapList.TEAM_SD_WORLDS;

public class PlayerDropItem implements Listener {
    public static PlayerDropItem instance;
    public PlayerDropItem() {
        instance = this;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerDropItem(PlayerDropItemEvent event){
        World w = event.getItemDrop().getWorld();

        if (TEAM_SD_WORLDS.contains(w.getName())){
            event.setCancelled(true);
        }
    }
}
