package xyz.jxmm.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import xyz.jxmm.gaming.team_sd.TeamPlayerList;
import xyz.jxmm.gaming.team_sd.inventory.InventoryList;
import xyz.jxmm.gaming.team_sd.inventory.WeaponsMenuItems;

import java.util.Objects;

public class InventoryClick implements Listener {
    public static InventoryClick instance;
    public InventoryClick() {
        instance = this;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if (Objects.equals(inventory.getItem(49), WeaponsMenuItems.CLOSE_MENU)){
            switch (event.getSlot()){
                // 0 9 18 27 36 45  枪械菜单
                case 0:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.PISOTL_MENU);
                    break;
                case 9:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.SNIPER_MENU);
                    break;
                case 18:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.RIFLE_MENU);
                    break;
                case 27:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.SHOTGUN_MENU);
                    break;
                case 36:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.SMG_MENU);
                    break;
                case 45:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.MG_MENU);
                    break;
                case 8:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.SCOPE_MENU);
                    break;
                case 17:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.MUZZLE_MENU);
                    break;
                case 26:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.STOCK_MENU);
                    break;
                case 35:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.GRIP_MENU);
                    break;
                case 44:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.EXTENDED_MAG_MENU);
                    break;
                // 4 49 53  杂项物品
                case 4:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(InventoryList.instance.WEAPON_MENU);
                    break;
                case 49:
                    event.getWhoClicked().closeInventory();
                    break;
                case 53:
                    player.getInventory().addItem(WeaponsMenuItems.AMMO);
                    break;
                case 48:
                    TeamPlayerList.main(player, player.getWorld().getName() + "teamA");
                    player.sendMessage(ChatColor.BLUE + "已切换到队伍A");
                    break;
                case 50:
                    TeamPlayerList.main(player, player.getWorld().getName() + "teamB");
                    player.sendMessage(ChatColor.RED + "已切换到队伍B");
                    break;
            }
            event.setCancelled(true);
        } else if (Objects.equals(inventory.getItem(4), WeaponsMenuItems.BACK_MAIN_MENU)){
            if (event.getSlot() > 8 && event.getSlot() < 53){
                if (event.getCurrentItem() != null){
                    player.getInventory().addItem(event.getCurrentItem());
                }
            }
            if (Objects.equals(event.getCurrentItem(), WeaponsMenuItems.BACK_MAIN_MENU)){
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(InventoryList.instance.WEAPON_MENU);
            }
            event.setCancelled(true);
        }

    }
}
