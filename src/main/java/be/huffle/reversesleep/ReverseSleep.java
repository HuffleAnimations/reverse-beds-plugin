package be.huffle.reversesleep;

import java.io.Console;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public class ReverseSleep extends JavaPlugin implements Listener
{
	private ReverseBed reverseBed;

	public ReverseSleep()
	{
		super();
	}
	
	protected ReverseSleep(final JavaPluginLoader loader, final PluginDescriptionFile description, final File dataFolder, final File file)
	{
		super(loader, description, dataFolder, file);
	}
	
	@Override
	public void onLoad()
	{
		// TODO Stub
	}
	
	@Override
	public void onEnable()
	{
		ItemStack blackBed = new ItemStack(Material.BLACK_BED);
		Iterator<Recipe> recipes = Bukkit.recipeIterator();
		while (recipes.hasNext())
		{
			Recipe recipe = recipes.next();
			if (recipe.getResult().equals(blackBed))
			{
				recipes.remove();
			}
		}
		reverseBed = new ReverseBed(this);
		Bukkit.getPluginManager().registerEvents(reverseBed, this);
	}
	
	@Override
	public void onDisable()
	{
		// TODO Stub
	}
}
































