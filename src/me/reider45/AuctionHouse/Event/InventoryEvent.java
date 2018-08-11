package me.reider45.AuctionHouse.Event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import me.reider45.AuctionHouse.AuctionHouse;
import me.reider45.AuctionHouse.Auction.Auction;
import me.reider45.AuctionHouse.Buying.BuyGUI;
import me.reider45.AuctionHouse.Selling.SellGUI;

public class InventoryEvent implements Listener {

	private AuctionHouse au;

	public InventoryEvent(AuctionHouse au) {
		this.au = au;
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();

		BuyGUI buyGUI;
		SellGUI sellGUI;
		if ((buyGUI = au.getAU().getBuyHandler().isBuying(player)) != null) {
			buyGUI.cancel();
		} else if ((sellGUI = au.getAU().getSellHandler().isSelling(player)) != null) {
			sellGUI.cancel();
		}
	}

	@EventHandler
	public void onUse(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player player = (Player) e.getWhoClicked();
			ItemStack clicked = e.getCurrentItem();
			if (clicked == null)
				return;

			BuyGUI buyGUI;
			SellGUI sellGUI;
			if ((buyGUI = au.getAU().getBuyHandler().isBuying(player)) != null) {
				e.setCancelled(true);
				// Buying
				if (clicked.equals(Auction.closeButton)) {
					buyGUI.doneButton();
				} else if (clicked.equals(Auction.addItem)) {
					buyGUI.doneButton();
					au.getAU().getSellHandler().openSellGUI(player);
				} else if (clicked.equals(Auction.nextButton)) {
					buyGUI.flipPage();
				} else if (clicked.equals(Auction.backButton)) {
					buyGUI.backPage();
				} else {
					// Purchase item
					buyGUI.purchaseItem(clicked);
				}
			} else if ((sellGUI = au.getAU().getSellHandler().isSelling(player)) != null) {
				e.setCancelled(true);
				/// A players inventory Something in their hand
				if ((e.getSlot() != e.getRawSlot()) || e.getCursor().getType() != Material.AIR) {
					/// Something in their hand // placing it down
					if (e.getCursor().getType() != Material.AIR && clicked.equals(Auction.placeItemHere)) {
						e.getInventory().remove(Auction.placeItemHere);
						e.getInventory().setItem(22, e.getCursor());
						e.setCursor(null);
						return;
						// Allow to place in own inventory
					} else if ((e.getSlot() != e.getRawSlot())) {
						e.setCancelled(false);
					}
				} else if (e.getSlot() == 22 && !clicked.equals(Auction.placeItemHere)) {
					e.setCancelled(false);
				}
				// Selling
				else if (clicked.equals(Auction.closeButton)) {
					sellGUI.doneButton();
				} else {
					// Control Buttons
					String name;
					if (clicked.getItemMeta() != null && (name = clicked.getItemMeta().getDisplayName()) != null) {
						if (name.contains("Add") && name.contains("Hour")) {
							sellGUI.increaseTime();
						} else if (name.contains("Remove") && name.contains("Hour")) {
							sellGUI.decreaseTime();
						} else if (name.contains("Add")) {
							sellGUI.increasePrice(name);
						} else if (name.contains("Remove")) {
							sellGUI.decreasePrice(name);
						}
					}
				}
			}

		}
	}

}
