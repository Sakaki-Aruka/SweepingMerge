package whatenchant.whatenchant;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

import static whatenchant.whatenchant.EnchantAdd.openMenu;

public class MenuClose {
    public void closeMain(InventoryCloseEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        Player player = Bukkit.getPlayer(uuid);
        if(openMenu.contains(player)){
            ArrayList<ItemStack> array = new ArrayList<>();
            array.add(event.getInventory().getItem(1));
            array.add(event.getInventory().getItem(2));

            for(ItemStack loop:array){
                if(!(loop==null)){
                    player.getWorld().dropItemNaturally(player.getLocation(),loop);
                }
            }

            openMenu.remove(player);
        }
    }
}
