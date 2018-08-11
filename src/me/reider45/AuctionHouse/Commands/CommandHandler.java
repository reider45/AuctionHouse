package me.reider45.AuctionHouse.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.reider45.AuctionHouse.AuctionHouse;

public class CommandHandler implements CommandExecutor {

	private AuctionHouse au;

	public CommandHandler(AuctionHouse au) {
		this.au = au;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("auction")) {
				au.getAU().getBuyHandler().openBuyGUI((Player) sender);
			}
		}
		return true;
	}

}
