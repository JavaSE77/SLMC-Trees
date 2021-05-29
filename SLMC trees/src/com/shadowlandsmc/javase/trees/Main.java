package com.shadowlandsmc.javase.trees;


import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin {
	
	private static Plugin plugin;
	  
	  public void onEnable() {
		  //Register plugin
		plugin = getPlugin(Main.class);
		
		//setup the config
		ConfigManager configManager = new ConfigManager(plugin);
		configManager.setupConfig();
		
		//register new events:
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new TreePlantedListener (plugin, configManager), this);
		
		//register commands
		this.getCommand("slmctrees").setExecutor(new CommandExecuter(plugin, configManager));
//		this.getCommand("deop").setExecutor(this);
//		this.getCommand("setoppassword").setExecutor(this);
		
		
	    plugin.getLogger().info("SLMC trees has enabled.");
	  }
}
