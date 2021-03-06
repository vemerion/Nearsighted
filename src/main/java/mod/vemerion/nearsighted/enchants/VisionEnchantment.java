package mod.vemerion.nearsighted.enchants;

import mod.vemerion.nearsighted.Nearsighted;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class VisionEnchantment extends Enchantment {

	public VisionEnchantment(Rarity rarityIn, EquipmentSlotType[] slots) {
		super(rarityIn, EnchantmentType.create("GLASSES", (item) -> item.equals(Nearsighted.GLASSES)), slots);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 10 + (enchantmentLevel - 1) * 7;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 3;
	}
}
