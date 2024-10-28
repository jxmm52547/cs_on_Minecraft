package xyz.jxmm.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public class HungryEvent implements Listener {
    public static HungryEvent instance;
    public HungryEvent() {
        instance = this;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onHungry(org.bukkit.event.entity.FoodLevelChangeEvent e) {
        Player player = (Player) e.getEntity();
        player.addPotionEffect(PotionEffectType.SATURATION.createEffect(100, 2));
    }
}
