package whatenchant.whatenchant;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static whatenchant.whatenchant.EnchantAdd.openMenu;

public class MenuClick{
    public void clickMain(InventoryClickEvent event){
        UUID uuid = event.getWhoClicked().getUniqueId();
        Player player = Bukkit.getPlayer(uuid);
        if(openMenu.contains(player)){
            if(event.getRawSlot()==0){
                event.setCancelled(true);
                ItemStack slot1 = event.getClickedInventory().getItem(1);
                ItemStack slot2 = event.getClickedInventory().getItem(2);

                if(slot1==null || slot2 == null){
                    return;
                }
                String str1 = slot1.getType().toString();
                String str2 = slot2.getType().toString();
                Inventory inventory = event.getClickedInventory();

                if(str1.contains("ENCHANTED_BOOK") && str2.toUpperCase(Locale.ROOT).contains("SWORD")){
                    this.enchantAdd(slot1,slot2,player,inventory,1);
                }else if(str2.contains("ENCHANTED_BOOK") && str1.toUpperCase(Locale.ROOT).contains("SWORD")){
                    this.enchantAdd(slot2,slot1,player,inventory,2);
                }else if(str1.contains("ENCHANTED_BOOK") && str2.contains("ENCHANTED_BOOK")){
                    //books enchanted level up
                    Map<String,Object> map1 = slot1.serialize();
                    Map<String,Object> map2 = slot2.serialize();
                    Enchantment sweep = Enchantment.SWEEPING_EDGE;

                    int level1 = ((EnchantmentStorageMeta) map1.get("meta")).getStoredEnchantLevel(sweep);
                    int level2 = ((EnchantmentStorageMeta) map2.get("meta")).getStoredEnchantLevel(sweep);
                    if(level1 == level2 && level1 < 3){
                        inventory.setItem(2,null);
                        ((EnchantmentStorageMeta) map1.get("meta")).removeStoredEnchant(sweep);
                        ((EnchantmentStorageMeta) map1.get("meta")).addStoredEnchant(sweep,level1+1,false);
                        inventory.setItem(1,new ItemStack(ItemStack.deserialize(map1)));

                    }

                } else{
                    //
                }
                return;

            }else if(3 <=event.getRawSlot() && event.getRawSlot() <=8){
                event.setCancelled(true);
            }
        }
    }

    public void enchantAdd(ItemStack book,ItemStack sword,Player player,Inventory inventory,int slot){
        // slot -> books slot
        Enchantment sweep = Enchantment.SWEEPING_EDGE;
        Map<String,Object> map = book.serialize();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            player.sendMessage(entry.getKey()+" / "+entry.getValue()+" / "+entry.getValue().getClass());

            if(entry.getKey().equalsIgnoreCase("meta")){
                EnchantmentStorageMeta esm = (EnchantmentStorageMeta) entry.getValue();
                if(esm.hasStoredEnchant(sweep)){
                    //get sweep level from an enchanted book
                    int level = esm.getStoredEnchantLevel(sweep);
                    //add sweep to a sword
                    sword.addUnsafeEnchantment(sweep,level);

                    //remove enchant from an enchanted book (give new enchanted book)
                    esm.removeStoredEnchant(sweep);
                    ItemStack deserialized = ItemStack.deserialize(map);

                    //replace an older enchanted book with new one
                    inventory.setItem(slot,null);
                    inventory.setItem(slot,deserialized);
                }
            }
        }
    }

}
