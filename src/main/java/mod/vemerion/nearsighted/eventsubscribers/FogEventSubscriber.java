package mod.vemerion.nearsighted.eventsubscribers;

import mod.vemerion.nearsighted.Nearsighted;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Nearsighted.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FogEventSubscriber {

	@SubscribeEvent
	public static void onFogDensity(FogDensity event) {
		if (event.getInfo().getRenderViewEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getInfo().getRenderViewEntity();
			for (ItemStack is : player.getArmorInventoryList()) {
				if (is.isItemEqualIgnoreDurability(new ItemStack(Nearsighted.GLASSES))) {
					int visionLevel = EnchantmentHelper.getEnchantmentLevel(Nearsighted.VISION_ENCHANTMENT, is);
					event.setDensity(0.1f - visionLevel * 0.03f);
					event.setCanceled(true);
					return;
				}
			}
		}
		event.setDensity(0.5f);
		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onFogColor(FogColors event) {
		event.setBlue(0.8f);
		event.setRed(0.8f);
		event.setGreen(0.8f);
	}
}
