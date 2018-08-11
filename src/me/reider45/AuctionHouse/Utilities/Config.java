package me.reider45.AuctionHouse.Utilities;

import me.reider45.AuctionHouse.AuctionHouse;

public class Config {

	public static double sellPrice;
	public static int expireTime, port;
	public static String host, user, pass, dbname;

	public Config(AuctionHouse au) {
		sellPrice = au.getConfig().getDouble("Defaults.SellPrice");
		expireTime = au.getConfig().getInt("Defaults.ExpireTime");

		host = au.getConfig().getString("Database.Host");
		user = au.getConfig().getString("Database.User");
		pass = au.getConfig().getString("Database.Pass");
		port = au.getConfig().getInt("Database.Port");
		dbname = au.getConfig().getString("Database.DBName");
	}

}
