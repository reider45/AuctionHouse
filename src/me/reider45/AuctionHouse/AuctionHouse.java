package me.reider45.AuctionHouse;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.reider45.AuctionHouse.Auction.Auction;
import me.reider45.AuctionHouse.Commands.CommandHandler;
import me.reider45.AuctionHouse.Database.Database;
import me.reider45.AuctionHouse.Economy.VaultM;
import me.reider45.AuctionHouse.Event.InventoryEvent;
import me.reider45.AuctionHouse.Utilities.Config;

public class AuctionHouse extends JavaPlugin {

	private Auction auction;
	private Database database;

	public void onEnable() {
		// Create database
		// TODO database Database.initialize();
		saveDefaultConfig();
		new Config(this);
		new VaultM(this);
		try {
			database = new Database(this, Config.host, Config.user, Config.pass, Config.port, Config.dbname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		auction = new Auction(this);
		getCommand("auction").setExecutor(new CommandHandler(this));

		Bukkit.getServer().getPluginManager().registerEvents(new InventoryEvent(this), this);
	}

	public Auction getAU() {
		return auction;
	}

	public Database getData() {
		return database;
	}

}
