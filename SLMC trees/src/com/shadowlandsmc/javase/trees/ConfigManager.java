package com.shadowlandsmc.javase.trees;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

public class ConfigManager {
	private static Plugin plugin;
	//This will be the list returned from the config after it is varified. Will contain lowercase strings. 
	private List materialList;
	//returnable items
	private boolean suppressInfo = false;
	private String fileLocation;

	
	public ConfigManager(Plugin plugin) {
		this.plugin	= plugin;
	    plugin.getConfig().options().copyDefaults(true);
	    plugin.saveDefaultConfig();
	    this.materialList = new ArrayList();
	    this.suppressInfo = plugin.getConfig().getBoolean("SuppressInfoLogger");
	    this.fileLocation = plugin.getConfig().getString("Location");
	}
	

	public void setupConfig() {
		List materials = getListFromConfig();
		boolean broken = false;
		try {
		for(int i = 0; i < materials.size(); i++) {
			List schems = plugin.getConfig().getList("" + materials.get(i).toString().toLowerCase());
			double percentage = 0;
			for(int j = 0; j < schems.size(); j++) {
				String[] last = schems.get(j).toString().split(":");
				percentage += Double.parseDouble(last[last.length -1]);
			}
			if(percentage == 100) {
				this.materialList.add(materials.get(i).toString().toLowerCase());
			} else {
				broken = true;
				plugin.getLogger().severe("Config Error!");
				plugin.getLogger().severe("" + materials.get(i).toString().toLowerCase() + " percentages does not add up to 100%. Plugin will not work correctly!");
				
			}
		}	
		} catch(NullPointerException e) {
			broken = true;

			plugin.getLogger().severe("Config Error!");
			plugin.getLogger().severe("There is a missing value from the config!");
		}
		
		if(!broken) {
			plugin.getLogger().info("Config varified!");
		}
	}
	
	private List getListFromConfig() {
		
		return plugin.getConfig().getList("Materials");
	}
	
	public List getMaterialsList() {
		
		return this.materialList;
	}
	
	public String getFileFolderLocation() {
		return this.fileLocation;
	}
	
	public boolean suppressInfoLogger() {
		return this.suppressInfo;
	}
	
	public void printDebug() {
		System.out.println("@TESTING varified list + " + this.materialList);
		System.out.println("@TESTING raw list+ " + getListFromConfig());
	}
	
}
