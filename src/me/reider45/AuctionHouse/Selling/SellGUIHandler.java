package me.reider45.AuctionHouse.Selling;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.reider45.AuctionHouse.Auction.Auction;
import me.reider45.AuctionHouse.Utilities.ItemUtil;

public class SellGUIHandler {
	
	private Auction auction;
	private HashMap<Player, SellGUI> playerSelling;

	public SellGUIHandler(Auction auction) {
		this.auction = auction;
		SellGUI.auction = auction;
		playerSelling = new HashMap<>();
	}

	/**
	 * Open the selling GUI
	 * 
	 * @param player
	 */
	public void openSellGUI(Player player) {
		SellGUI gui = new SellGUI(player, this);
		playerSelling.put(player, gui);
		gui.openSellGUI();
	}

	public void remove(Player player) {
		player.closeInventory();
		cancel(player);
	}

	public SellGUI isSelling(Player player) {
		return playerSelling.get(player);
	}

	public void cancel(Player player) {
		playerSelling.remove(player);
	}
	
	public Auction getAU() {
		return auction;
	}
}
