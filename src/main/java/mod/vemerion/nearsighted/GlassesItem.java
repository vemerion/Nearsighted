package mod.vemerion.nearsighted;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class GlassesItem extends ArmorItem {
	public GlassesItem() {
		super(new GlassMaterial(), EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT));
	}
}
