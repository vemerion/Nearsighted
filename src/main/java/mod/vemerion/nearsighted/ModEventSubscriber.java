package mod.vemerion.nearsighted;

import mod.vemerion.nearsighted.enchants.PerceptionEnchantment;
import mod.vemerion.nearsighted.enchants.VisionEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = Nearsighted.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[] { EquipmentSlotType.HEAD,
			EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET };

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(setup(new GlassesItem(), "glasses"));
	}

	@SubscribeEvent
	public static void onRegisterEnchantments(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().register(setup(new VisionEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS), "vision_enchantment"));
		event.getRegistry().register(setup(new PerceptionEnchantment(Enchantment.Rarity.RARE, ARMOR_SLOTS), "perception_enchantment"));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(Nearsighted.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}
