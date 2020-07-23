package mod.vemerion.nearsighted.eventsubscribers;

import mod.vemerion.nearsighted.Nearsighted;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Nearsighted.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TooltipEventSubscriber {
	
	@SubscribeEvent
	public static void changeGlassesTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().isItemEqualIgnoreDurability(new ItemStack(Nearsighted.GLASSES))) {
			event.getToolTip().add(new StringTextComponent("+ Vision Aid").applyTextStyle(TextFormatting.BLUE));
		}
	}
	
	@SubscribeEvent
	public static void warnPlayerAboutNearsightedness(PlayerLoggedInEvent event) {
		if (event.getPlayer().world.getGameTime() < 1000) {
			event.getPlayer().sendStatusMessage(new StringTextComponent("You were spawned nearsighted! Quickly, make some glasses!"), true);
		}
	}

}
