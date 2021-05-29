package com.shadowlandsmc.javase.trees;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class CommandExecuter implements CommandExecutor {
	
	private Plugin plugin;
	private ConfigManager config;
	
	public CommandExecuter(Plugin plugin, ConfigManager config) {
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		// TODO Auto-generated method stub
		
		if(!(cmd.getName().equalsIgnoreCase("slmctrees"))) {
			return false;
		}
		if(!sender.hasPermission("slmctrees.admin")) {
			sender.sendMessage(ChatColor.DARK_RED + "You do not have permission for this command");
			return false;
		}
		
		if(args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Please use the command like this: /slmctrees [enable,disable,reload]");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("reload")) {
			//Reload the config through the config manager.
			config.reloadConfig();
			sender.sendMessage(ChatColor.GOLD + "SLMC-Trees has been reloaded");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("disable")) {
			//Set the status to disabled
			if(config.changeEnabledStatus(false)) {
				sender.sendMessage(ChatColor.GOLD + "SLMC-Trees enabled status has been changed to disabled");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "SLMC-Trees enabled status will be overriden by the value set in the config at every restart. "
						+ "For persistent changes, please update the config value");
			} else {
				sender.sendMessage(ChatColor.GOLD + "SLMC-Trees is already set to disabled");
			}
			return true;
		}
		

		if(args[0].equalsIgnoreCase("enable")) {
			//Set the status to disabled
			if(config.changeEnabledStatus(true)) {
				sender.sendMessage(ChatColor.GOLD + "SLMC-Trees enabled status has been changed to enabled");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "SLMC-Trees enabled status will be overriden by the value set in the config at every restart. "
						+ "For persistent changes, please update the config value");
			} else {
				sender.sendMessage(ChatColor.GOLD + "SLMC-Trees is already set to enabled");
			}
			return true;
		}
		
		sender.sendMessage(ChatColor.RED + "Error handling command. Please try: /slmctrees [enable,disable,reload]");
		
		return false;
	}

}
