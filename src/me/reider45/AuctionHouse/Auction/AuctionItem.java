package me.reider45.AuctionHouse.Auction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.reider45.AuctionHouse.Utilities.ItemUtil;

public class AuctionItem {

	private ItemStack auctionItem;// for display in the auction house
	private ItemStack physicalItem; // the physical item for purchase
	private String itemURL, item_owner;
	private double item_price;
	private Timestamp item_endListing;

	public AuctionItem(String itemURL, String owner, double price, Timestamp endListing) {
		this.itemURL = itemURL;
		this.item_owner = owner;
		this.item_price = price;
		this.item_endListing = endListing;

		this.buildAuctionItem();
		this.buildPhysicalItem();
	}

	/**
	 * Build the display item
	 */
	private void buildAuctionItem() {
		this.auctionItem = ItemUtil.fromString(itemURL);
		ItemMeta meta = auctionItem.getItemMeta();

		List<String> lore = meta.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.add("$" + item_price);
		lore.add(ItemUtil.formatTime(item_endListing) + " hours left");
		lore.add("");
		lore.add("Seller: " + item_owner);
		meta.setLore(lore);
		auctionItem.setItemMeta(meta);
	}

	private void buildPhysicalItem() {
		this.physicalItem = ItemUtil.fromString(itemURL);
	}

	/**
	 * 
	 * @return
	 */
	public ItemStack getAuctionItem() {
		return auctionItem;
	}

	public ItemStack getPhysicalItem() {
		return physicalItem;
	}
	
	public String getItemURL() {
		return itemURL;
	}
	
	public String getOwner() {
		return item_owner;
	}
	
	public double getPrice() {
		return item_price;
	}

}
