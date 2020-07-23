package mod.vemerion.nearsighted;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class GlassMaterial implements IArmorMaterial {
	;

	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return 100;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return 0;
	}

	@Override
	public int getEnchantability() {
		return 15;
	}

	@Override
	public SoundEvent getSoundEvent() {
		return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return Ingredient.fromItems(Items.GLASS_PANE);
	}

	@Override
	public String getName() {
		return Nearsighted.MODID + ":glass";
	}

	@Override
	public float getToughness() {
		return 0;
	}

}
