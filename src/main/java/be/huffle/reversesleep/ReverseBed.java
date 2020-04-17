package be.huffle.reversesleep;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.server.v1_15_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntity;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static org.bukkit.inventory.RecipeChoice.*;

public class ReverseBed implements Listener
{
	private static final String REVERSE_BED = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Reverse Bed" ;
	private ReverseSleep plugin;

	public ReverseBed(ReverseSleep plugin)
	{
		this.plugin = plugin;
		ItemStack result = new ItemStack(Material.BLACK_BED);
		ItemMeta resultMeta = result.getItemMeta();
		resultMeta.setDisplayName(REVERSE_BED);
		result.setItemMeta(resultMeta);

		List<Material> woodenPlanks = new ArrayList<Material>();
		woodenPlanks.add(Material.ACACIA_PLANKS);
		woodenPlanks.add(Material.BIRCH_PLANKS);
		woodenPlanks.add(Material.DARK_OAK_PLANKS);
		woodenPlanks.add(Material.JUNGLE_PLANKS);
		woodenPlanks.add(Material.OAK_PLANKS);
		woodenPlanks.add(Material.SPRUCE_PLANKS);

		MaterialChoice plankChoice = new MaterialChoice(woodenPlanks);

		Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(plugin, "reverseBed"),
				result).shape("aaa", "bbb").setIngredient('a', Material.BLACK_WOOL)
				.setIngredient('b', plankChoice));
	}

	@EventHandler
	public void breakBed(BlockBreakEvent event)
	{
		Block item = event.getBlock();
		if (item.getType().equals(Material.BLACK_BED))
		{
			event.setDropItems(false);

			if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			{
				return;
			}

			ItemStack reverseBed = new ItemStack(Material.BLACK_BED);
			ItemMeta reverseBedMeta = reverseBed.getItemMeta();
			reverseBedMeta.setDisplayName(REVERSE_BED);
			reverseBed.setItemMeta(reverseBedMeta);

			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), reverseBed);
		}
	}

	@EventHandler
	public void clickBlackBed(PlayerBedEnterEvent event)
	{
		Block bed = event.getBed();

		if (bed.getType().equals(Material.BLACK_BED))
		{
			event.setCancelled(true);
			event.setUseBed(Event.Result.ALLOW);
		}
		else if (event.getBed().getWorld().getTime() <= 23458 && event.getBed().getWorld().getTime() >= 12541)
		{
			Bukkit.getScheduler().runTaskLater(plugin, ()-> event.getBed().getWorld().setTime(0), 40);
		}
	}

	@EventHandler
	public void sleep(PlayerBedLeaveEvent event)
	{
		Block bed = event.getBed();
		if (bed.getType().equals(Material.BLACK_BED))
		{
			event.getBed().getWorld().setTime(13000);
		}
	}
}
