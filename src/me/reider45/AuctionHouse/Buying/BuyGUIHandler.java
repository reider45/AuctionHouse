package me.reider45.AuctionHouse.Buying;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.reider45.AuctionHouse.Auction.Auction;
import me.reider45.AuctionHouse.Buying.BuyGUI;

public class BuyGUIHandler {
	private Auction auction;
	private HashMap<Player, BuyGUI> playerBuying;

	public BuyGUIHandler(Auction auction) {
		this.auction = auction;
		BuyGUI.auction = auction;
		playerBuying = new HashMap<>();
	}
	
	public Auction getAU() {
		return auction;
	}

	/**
	 * Open the Buying GUI
	 * 
	 * @param player
	 */
	public void openBuyGUI(Player player) {
		BuyGUI gui = new BuyGUI(player, this);
		playerBuying.put(player, gui);
		gui.openBuyGUI();
	}
	
	public void remove(Player player) {
		player.closeInventory();
		cancel(player);
	}
	
	public BuyGUI isBuying(Player player) {
		return playerBuying.get(player);
	}
	
	public void cancel(Player player) {
		playerBuying.remove(player);
	}

}
