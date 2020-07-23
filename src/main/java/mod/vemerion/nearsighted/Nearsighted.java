package mod.vemerion.nearsighted;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Nearsighted.MODID)
public class Nearsighted {
	
	@ObjectHolder(Nearsighted.MODID + ":glasses")
	public static final Item GLASSES = null;
	
	@ObjectHolder(Nearsighted.MODID + ":vision_enchantment")
	public static final Enchantment VISION_ENCHANTMENT = null;
	
	@ObjectHolder(Nearsighted.MODID + ":perception_enchantment")
	public static final Enchantment PERCEPTION_ENCHANTMENT = null;
	
	public static final String MODID = "nearsighted";
}
