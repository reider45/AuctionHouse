package me.reider45.AuctionHouse.Selling;

import java.sql.Timestamp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.reider45.AuctionHouse.Auction.Auction;
import me.reider45.AuctionHouse.Utilities.Config;
import me.reider45.AuctionHouse.Utilities.ItemUtil;

public class SellGUI {

	// Global Variables
	public static Auction auction;
	public static String inventory_name = "Auction House - Sell";

	private SellGUIHandler handler;
	private Player player;
	private Inventory gui;
	private double price = 0.0;
	private Timestamp expireListing;

	// Controls
	// Price
	private ItemStack add1, add5, add10, rem1, rem5, rem10;
	private ItemStack increaseTime, decreaseTime;

	public SellGUI(Player player, SellGUIHandler handler) {
		this.player = player;
		this.handler = handler;
		price = Config.sellPrice;
		expireListing = new Timestamp(System.currentTimeMillis());
		for (int i = 0; i < Config.expireTime; i++) {
			expireListing = ItemUtil.increaseTime(expireListing);
		}
		// Add
		// default
		// time
	}

	public void updateControls() {
		add1 = ItemUtil.buildItemWithLore("Add $1", Material.PAPER, "$" + (price));
		add5 = ItemUtil.buildItemWithLore("Add $5", Material.PAPER, "$" + (price));
		add10 = ItemUtil.buildItemWithLore("Add $10", Material.PAPER, "$" + (price));

		rem1 = ItemUtil.buildItemWithLore("Remove $1", Material.PAPER, "$" + (price));
		rem5 = ItemUtil.buildItemWithLore("Remove $5", Material.PAPER, "$" + (price));
		rem10 = ItemUtil.buildItemWithLore("Remove $10", Material.PAPER, "$" + (price));

		increaseTime = ItemUtil.buildItemWithLore("Add 1 Hour", Material.ARROW,
				ItemUtil.formatTime(expireListing) + " hours left");
		decreaseTime = ItemUtil.buildItemWithLore("Remove 1 Hour", Material.ARROW,
				ItemUtil.formatTime(expireListing) + " hours left");

		gui.setItem(30, increaseTime);
		gui.setItem(32, decreaseTime);

		gui.setItem(19, this.add1);
		gui.setItem(20, this.add5);
		gui.setItem(21, this.add10);

		gui.setItem(23, this.rem1);
		gui.setItem(24, this.rem5);
		gui.setItem(25, this.rem10);

	}

	/**
	 * Open the selling gui
	 */
	protected void openSellGUI() {
		gui = Bukkit.createInventory(null, 54, inventory_name);
		designSellGUI();
		player.openInventory(gui);
		updateControls();
	}

	/**
	 * Display the selling GUI
	 */
	private void designSellGUI() {

		// Borders
		for (int i = 0; i < 54; i += 9) {
			gui.setItem(i, Auction.blank);
		}
		for (int i = 1; i < 9; i++) {
			gui.setItem(i, Auction.blank);
		}
		for (int i = 45; i < 54; i++) {
			gui.setItem(i, Auction.blank);
		}
		for (int i = 8; i < 54; i += 9) {
			gui.setItem(i, Auction.blank);
		}

		gui.setItem(22, Auction.placeItemHere);
		gui.setItem(43, Auction.closeButton);
		updateControls();
	}

	/**
	 * Close / sell item in the selling gui
	 */
	public void doneButton() {
		// Take the items from the sell GUI
		handler.remove(player);
		ItemStack item = gui.getItem(22);
		if (item != null && !item.equals(Auction.placeItemHere)) {
			auction.addItem(ItemUtil.toString(item), player.getName(), price, expireListing, true);
		}
		handler.getAU().getBuyHandler().openBuyGUI(player);
	}

	public void cancel() {
		handler.cancel(player);
	}

	// Controls
	public void increaseTime() {
		expireListing = ItemUtil.increaseTime(expireListing);
		updateControls();
	}

	public void decreaseTime() {
		if (ItemUtil.timeDifference(ItemUtil.decreaseTime(expireListing),
				new Timestamp(System.currentTimeMillis())) >= Config.expireTime) {
			expireListing = ItemUtil.decreaseTime(expireListing);
			updateControls();
		}
	}

	public void increasePrice(String name) {
		name = name.replaceAll("\\D+","");
		int amount = Integer.parseInt(name);
		price += amount;
		updateControls();
	}

	public void decreasePrice(String name) {
		name = name.replaceAll("\\D+","");
		int amount = Integer.parseInt(name);
		if (price - amount >= Config.sellPrice) {
			price -= amount;
			updateControls();
		}
	}
}
