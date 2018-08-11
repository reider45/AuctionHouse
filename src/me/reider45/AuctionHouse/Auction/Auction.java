package me.reider45.AuctionHouse.Auction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.reider45.AuctionHouse.AuctionHouse;
import me.reider45.AuctionHouse.Buying.BuyGUIHandler;
import me.reider45.AuctionHouse.Selling.SellGUIHandler;
import me.reider45.AuctionHouse.Utilities.ItemUtil;

public class Auction {

	// Global ItemStacks
	public static ItemStack closeButton;
	public static ItemStack addItem;
	public static ItemStack backButton;
	public static ItemStack nextButton;
	public static ItemStack blank;
	public static ItemStack placeItemHere;

	private AuctionHouse au;

	private BuyGUIHandler buyGUIHandler;
	private SellGUIHandler sellGUIHandler;
	private List<AuctionItem> auctionInventory;

	public Auction(AuctionHouse au) {
		this.au = au;
		this.auctionInventory = new ArrayList<>();
		this.loadItems();
		this.addItem = ItemUtil.buildItem("Sell Item", Material.APPLE, (short) 0);
		this.closeButton = ItemUtil.buildItem("Done", Material.WOOL, (short) 5);
		this.backButton = ItemUtil.buildItem("Back", Material.YELLOW_FLOWER, (short) 0);
		this.nextButton = ItemUtil.buildItem("Next", Material.YELLOW_FLOWER, (short) 0);
		this.blank = ItemUtil.buildItem("-", Material.STAINED_GLASS_PANE, (short) 15);
		this.placeItemHere = ItemUtil.buildItem("Item Goes Here", Material.STONE, (short) 3);
		this.buyGUIHandler = new BuyGUIHandler(this);
		this.sellGUIHandler = new SellGUIHandler(this);
	}

	public List<AuctionItem> getItems() {
		return auctionInventory;
	}

	/**
	 * Load Items from database
	 */
	public void loadItems() {
		// TODO load items

		ResultSet rs;
		try {
			rs = au.getData().querySQL("SELECT * FROM AUCTION");
			while (rs.next()) {
				String itemURL = rs.getString("item");
				String owner = rs.getString("owner");
				double price = rs.getDouble("price");
				Timestamp expireDate = rs.getTimestamp("expiredate");
				addItem(itemURL, owner, price, expireDate, false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Add itemstack with information to the auction house
	 * 
	 * @param item
	 * @param player
	 * @param amount
	 * @param price
	 * @param expireTime
	 */
	public void addItem(String itemURL, String player, double price, Timestamp expireTime, boolean updateToDB) {
		AuctionItem auctionItem = new AuctionItem(itemURL, player, price, expireTime);
		auctionInventory.add(auctionItem);

		if (updateToDB) {
			au.getData().updateSQL(
					"INSERT INTO `auctionhouse`.`AUCTION` (`id`, `item`, `owner`, `price`, `expiredate`) VALUES ('0', '"
							+ itemURL + "', '" + player + "', '" + price + "', '" + expireTime + "');");
		}
	}

	public BuyGUIHandler getBuyHandler() {
		return buyGUIHandler;
	}

	public SellGUIHandler getSellHandler() {
		return sellGUIHandler;
	}

	public AuctionHouse getAU() {
		return au;
	}

}
