package me.reider45.AuctionHouse.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import me.reider45.AuctionHouse.AuctionHouse;

public class Database {

	private AuctionHouse instance;
	private Connection con;
	private String host, user, pass, DBName;
	private int port;

	/**
	 * @param host
	 * @param user
	 * @param pass
	 * @param port
	 * @param DBname
	 * @throws SQLException
	 */
	public Database(AuctionHouse instance, String host, String user, String pass, int port, String DBName)
			throws SQLException {
		this.instance = instance;
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.port = port;
		this.DBName = DBName;

		con = createConnection();
	}

	public void close() {
		try {
			if (!con.isClosed())
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Connection createConnection() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + DBName,
					user, pass);
			con = connection;
			System.out.println("[AuctionHouse] Connected to MySQL - reider45!");
			if (!tableExists("AUCTION"))
				createTables();
			return con;
		} catch (Exception e) {
			System.out.println("[AuctionHouse] Couldn't connect to MySQL!");
			e.printStackTrace();
		}
		return null;
	}

	// ITEMSTRING, owner, price, timestamp
	protected void createTables() {
		// TODO make dynamic
		String query = "CREATE TABLE `auctionhouse`.`AUCTION` ( `id` INT NOT NULL AUTO_INCREMENT , `item` TEXT NOT NULL , `owner` TEXT NOT NULL , `price` DOUBLE NOT NULL , `expiredate` TIMESTAMP NOT NULL , PRIMARY KEY (`id`));";
		updateSQL(query);
	}

	protected boolean tableExists(String table) throws SQLException {
		String query = "SHOW TABLES LIKE '" + table + "'";
		ResultSet rs = querySQL(query);
		return rs.next();
	}

	protected boolean checkConnection() throws SQLException {
		return con != null && !con.isClosed();
	}

	protected Boolean closeConnection() throws SQLException {
		if (checkConnection()) {
			con.close();
			return true;
		}
		return false;
	}

	public ResultSet querySQL(final String query) throws SQLException {
		return con.createStatement().executeQuery(query);
	}

	public void updateSQL(final String query) {
		new BukkitRunnable() {
			public void run() {
				try {
					if (!checkConnection())
						createConnection();

					con.createStatement().executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(instance);
	}

	public Connection getConnection() {
		return con;
	}

}
