package xyz.jxmm.events;

import com.mohistmc.bukkit.entity.MohistModsEntity;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.world.entity.Entity;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static xyz.jxmm.gaming.team_sd.TeamPlayerList.spectatorList;
import static xyz.jxmm.utils.SendActionBar.sendActionBar;

public class DamageByEntity implements Listener {
    public static DamageByEntity instance;

    public DamageByEntity(){
        instance = this;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player victim && event.getDamager() instanceof MohistModsEntity entity){

            Entity nmsEntity = entity.getHandle();

            if (nmsEntity instanceof EntityKineticBullet){
                EntityKineticBullet kineticBullet = (EntityKineticBullet) nmsEntity;
                CraftPlayer craftPlayer = (CraftPlayer) kineticBullet.projectileSource;
                Player damager = craftPlayer.getPlayer();

                if (damager != null && spectatorList.contains(damager)){
                    sendActionBar(damager, ChatColor.RED + "旁观模式下, 您无法攻击玩家");
                    event.setCancelled(true);
                } else {
                    sendActionBar(damager, ChatColor.GREEN + victim.getName() + ChatColor.GOLD + " 剩余 " + ChatColor.RED + (int) victim.getHealth()  + "❤");
                }
            }


        }
    }
}
