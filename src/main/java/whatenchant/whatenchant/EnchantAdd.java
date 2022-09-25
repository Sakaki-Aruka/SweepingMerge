package whatenchant.whatenchant;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class EnchantAdd {
    public static ArrayList<Player> openMenu = new ArrayList<>();

    public void addMain(Player player){
        Inventory inventory = Bukkit.createInventory(null,9,"Enchant Merge");
        ItemStack itemStack = new ItemStack(Material.LEVER);
        ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        //itemMeta (set displayName and lore)
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§rMerge Enchants.");
        itemMeta.setLore(Arrays.asList("§rClick and Merge enchants to a tool."));
        itemStack.setItemMeta(itemMeta);

        //itemMeta (blank)
        ItemMeta blankMeta = blank.getItemMeta();
        blankMeta.setDisplayName("-");
        blank.setItemMeta(blankMeta);

        inventory.setItem(0,itemStack);
        for(int i=3;i<=8;i++){
            inventory.setItem(i,blank);
        }
        openMenu.add(player);
        player.openInventory(inventory);
    }
}
