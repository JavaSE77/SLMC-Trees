package com.shadowlandsmc.javase.trees;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockState;

import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

public class TreePlantedListener implements Listener {

	private static Plugin plugin;
	private ConfigManager config;
	
	public TreePlantedListener(Plugin plugin, ConfigManager config) {
		this.plugin = plugin;
		this.config = config;
	}
	
	@EventHandler
	public void treeListener(StructureGrowEvent event) {
		
		//If world guard or another plugin cancels the event, we don't want to touch it
		if(event.isCancelled() == true) {
			return;
		}
		
		if(!(config.getEnabledStatus())) {
			return;
		}
		
		//this is the parsed list from the config manager
		List materials = config.getMaterialsList();
		
		//Check to see if the structure is included in the arraylist. If it is not, then we are not touching it.
		if(!(materials.contains(event.getLocation().getBlock().getType().toString().toLowerCase()))) {
			return;
		}
		
		//Now we pick the schem file
		File file = getSchemFile(event.getLocation().getBlock().getType().toString().toLowerCase());
		
		event.getLocation().getBlock().setType(Material.AIR);
		
		//Now we place the generated schem file into the world
			if(placeSchemFile(file, event.getLocation())) {
				event.setCancelled(true);
			}
			

	}

	
	private boolean placeSchemFile(File file, Location location) {
		//read in the file and place it.
		
		if(file == null) {
			plugin.getLogger().severe("Null value placing schem file" );
			return false;
		}
		if(location == null) {
			plugin.getLogger().severe("Null value placing schem at location");
			return false;
		}

		 
		//copy pasta from: https://www.spigotmc.org/threads/1-13-load-paste-schematics-with-the-worldedit-api-simplified.357335/
	       com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(location.getWorld());

	        ClipboardFormat format = ClipboardFormats.findByFile(file);

	        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {

	            Clipboard clipboard = reader.read();
	            
	            
	            //Set the center of the schem
	            clipboard.setOrigin(BlockVector3.at(clipboard.getOrigin().getX(), clipboard.getMinimumPoint().getBlockY(), clipboard.getOrigin().getZ()));	            
	            
	            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1)) {

	            	//Schem will be placed at the center. 
	                Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
	                        .to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(true).build();

	                try {
	                    Operations.complete(operation);
	                    editSession.flushSession();

	                } catch (WorldEditException e) {
	                    plugin.getLogger().severe("Error placing schem file" + file.getAbsolutePath() +" at: " + location.toString());
	                    e.printStackTrace();
	                }
	            }


	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	            return false;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	        if(!config.suppressInfoLogger()) {
	        plugin.getLogger().info("Placed tree " + file.getName() + " at: " + location.toString());
	        }
	        return true;
		
	}
	
	

	private File getSchemFile(String key) {
		
		List schemFiles = plugin.getConfig().getList(key);
		
	      Random rand = new Random(); //instance of random class
	      double double_random=rand.nextDouble() * 100;
		
	      double currentPercentage = 0;
	      for(int i = 0; i < schemFiles.size(); i++) {
	    	  String[] split = schemFiles.get(i).toString().split(":");
	    	  currentPercentage = currentPercentage + Double.parseDouble(split[split.length -1]);
	    	  if(double_random <= currentPercentage) {
	    		  File file = new File(plugin.getServer().getWorldContainer() + config.getFileFolderLocation() + split[0]);
	    		  return file;
	    	  }
	      }
	      //We should never get to this spot
		plugin.getLogger().severe("Error finding schem file. Relative information: " + "currentPercentage: " + currentPercentage + "double_rand: " + double_random);
		return null;
	}
	
}
