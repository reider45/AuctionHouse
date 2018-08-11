package me.reider45.AuctionHouse.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.reider45.AuctionHouse.AuctionHouse;
import net.milkbowl.vault.economy.Economy;

public class VaultM {

	public static Economy economy = null;

	private static AuctionHouse au;

	public VaultM(AuctionHouse au) {
		this.au = au;
		if (!setupEconomy())
			System.out.println("CANT SETUP ECONOMY");
	}

	public static boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = au.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return (economy != null);

	}

	public static boolean canBuy(Player player, double price) {
		return economy.getBalance(player.getName()) >= price;
	}

	public static void takeMoney(Player player, double amount) {
		economy.withdrawPlayer(player, amount);
	}

	public static void giveMoney(String player, double amount) {
		economy.depositPlayer(Bukkit.getOfflinePlayer(player), amount);
	}
}
