package me.reider45.AuctionHouse.Buying;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.reider45.AuctionHouse.Auction.Auction;
import me.reider45.AuctionHouse.Auction.AuctionItem;
import me.reider45.AuctionHouse.Economy.VaultM;

public class BuyGUI {

	private BuyGUIHandler handler;
	public static Auction auction;
	public static String inventory_name = "Auction House - Buy";
	private Player player;
	private Inventory gui;

	private int page = 1;

	public BuyGUI(Player player, BuyGUIHandler handler) {
		this.player = player;
		this.handler = handler;
	}

	/**
	 * Open the Buying gui
	 */
	protected void openBuyGUI() {
		gui = Bukkit.createInventory(null, 54, inventory_name);
		designBuyGUI();
		player.openInventory(gui);
	}

	/**
	 * Display the Buying GUI
	 */
	private void designBuyGUI() {
		int auctionSize = auction.getItems().size();
		int startIndex = 0;
		if ((page * 45) - auctionSize < 0) {
			// Need more pages
			startIndex = 45;
		}
		// TODO calculate external pages
		for (int i = 0; i < 45 && i < auction.getItems().size() && startIndex < auction.getItems().size(); i++) {
			gui.setItem(i, auction.getItems().get(startIndex++).getAuctionItem());
		}
		gui.setItem(45, Auction.backButton);
		gui.setItem(49, Auction.addItem);
		gui.setItem(53, Auction.nextButton);
	}

	public void purchaseItem(ItemStack item) {
		AuctionItem toBuy = null;
		for (AuctionItem auItem : handler.getAU().getItems()) {
			if (item.equals(auItem.getAuctionItem())) {
				toBuy = auItem;
				break;
			}
		}
		if (toBuy == null) {
			// Shouldn't be listed there anymore
			gui.remove(item);
		} else {
			// The item is still valid and in the shop
			if (VaultM.canBuy(player, toBuy.getPrice())) {
				VaultM.takeMoney(player, toBuy.getPrice());
				VaultM.giveMoney(toBuy.getOwner(), toBuy.getPrice());
				gui.remove(item);
				auction.getAU().getData().updateSQL("DELETE FROM `auctionhouse`.`auction` WHERE item = '"
						+ toBuy.getItemURL() + "' AND owner='" + toBuy.getOwner() + "'");
				player.getInventory().addItem(toBuy.getPhysicalItem());
				handler.getAU().getItems().remove(toBuy);
			}
		}

	}

	public void flipPage() {
		if (auction.getItems().size() > (page * 45))
			page++;
		designBuyGUI();
	}

	public void backPage() {
		if (page > 1)
			page--;
		designBuyGUI();
	}

	/**
	 * Close in the Buying gui
	 */
	public void doneButton() {
		// Take the items from the Buy GUI
		handler.remove(player);
	}

	public void cancel() {
		handler.cancel(player);
	}

}
