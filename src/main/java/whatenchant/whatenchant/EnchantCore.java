package whatenchant.whatenchant;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class EnchantCore implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(args.length ==1){
            if(args[0].equalsIgnoreCase("hand")){
                //display enchants (in EnchantBook in players mainHand)

                if(player.getInventory().getItemInMainHand().getType()!=null){
                    ItemStack mainHand = player.getInventory().getItemInMainHand();
                    if(mainHand.getType().equals(Material.ENCHANTED_BOOK)){
                        for(Map.Entry<Enchantment,Integer> entry:((EnchantmentStorageMeta)mainHand.serialize().get("meta")).getStoredEnchants().entrySet()){
                            player.sendMessage("Enchant:"+ Arrays.asList(entry.getKey().toString().split(",")).get(1).replace("]","") +" / Lv:"+entry.getValue());
                        }
                        return true;
                    }
                }
                Map<Enchantment,Integer> enchantMap = player.getInventory().getItemInMainHand().getEnchantments();
                for(Map.Entry<Enchantment,Integer> entry:enchantMap.entrySet()){
                    player.sendMessage("Enchant:"+ Arrays.asList(entry.getKey().toString().split(",")).get(1).replace("]","") +" / Lv:"+entry.getValue());
                }
                return true;
            }else if(args[0].equalsIgnoreCase("add")){
                //add enchant (open inventory)
                new EnchantAdd().addMain(player);
                return true;
            }

        }
        return false;
    }
}
