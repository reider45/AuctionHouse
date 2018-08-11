package me.reider45.AuctionHouse.Utilities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemUtil {

	public static double formatTime(Timestamp stamp) {
		return (double) Math.round((stamp.getTime() - System.currentTimeMillis()) / 3600000);
	}

	public static Timestamp increaseTime(Timestamp stamp) {
		return new Timestamp(stamp.getTime() + (3600000));
	}

	public static Timestamp decreaseTime(Timestamp stamp) {
		return new Timestamp(stamp.getTime() - (3600000));
	}

	public static double timeDifference(Timestamp stamp, Timestamp stampb) {
		return (double) ((stamp.getTime() - stampb.getTime()) / 3600000);
	}

	public static ItemStack buildItemWithLore(String title, Material type, String lore) {
		ItemStack is = new ItemStack(type, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(title);

		List<String> loreList = new ArrayList<>();
		loreList.add(lore);
		meta.setLore(loreList);
		is.setItemMeta(meta);
		return is;
	}

	public static ItemStack buildItem(String title, Material type, short data) {
		ItemStack is = new ItemStack(type, 1, data);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(title);
		is.setItemMeta(meta);
		return is;
	}

	public static ItemStack fromString(String string) {
		// name,lore,material,enchants,amount, data
		String[] split = string.split("%");
		ItemStack item = new ItemStack(Material.STONE, 1, (byte) Integer.parseInt(split[5]));

		// Name
		ItemMeta meta = item.getItemMeta();
		if (!split[0].equals("-"))
			meta.setDisplayName(split[0]);

		// lore
		List<String> lore = new ArrayList<>();
		if (!split[1].equals("-"))
			lore.add(split[1]);
		meta.setLore(lore);

		// material
		item.setType(Material.valueOf(split[2]));

		// Enchantment
		if (!split[3].equals("-"))
			for (String ench : split[3].split(",")) {
				String[] enchantmentFull = ench.split(":");
				item.addUnsafeEnchantment(Enchantment.getByName(enchantmentFull[0]),
						Integer.valueOf(enchantmentFull[1]));
			}

		/// amount
		item.setAmount(Integer.valueOf(split[4]));

		return item;
	}

	public static String toString(ItemStack item) {
		String string;
		string = item.getItemMeta().getDisplayName();
		if (string == null)
			string = "-";
		string += "%";

		String loreString = "-";
		if (item.getItemMeta().getLore() != null)
			for (String s : item.getItemMeta().getLore())
				loreString += "\n" + s;

		string += loreString;
		string += "%";
		string += item.getType().toString();
		string += "%";

		String enchantString = "-";
		for (Entry<Enchantment, Integer> ench : item.getEnchantments().entrySet()) {
			enchantString += ench.getKey().getName() + ":" + ench.getValue();
		}

		if (!enchantString.equals("-")) {
			string += enchantString.replace("-", "");
		} else {
			string += enchantString;
		}

		string += "%";
		string += item.getAmount();
		string += "%";
		string += String.valueOf(item.getData().getData());
		// MaterialData data = item.getData();
		// data.setData ((byte) Integer.parseInt (split[4]));
		// i.setData(data);
		return string;
	}

}
