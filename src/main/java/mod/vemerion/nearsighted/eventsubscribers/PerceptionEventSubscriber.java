package mod.vemerion.nearsighted.eventsubscribers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.vemerion.nearsighted.Nearsighted;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Nearsighted.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PerceptionEventSubscriber {

	private static Random random = new Random();

	// Render stars for perception enchantment
	@SubscribeEvent
	public static void onRenderWorldLast(RenderWorldLastEvent event) {
		ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
		PlayerEntity player = Minecraft.getInstance().player;
		World world = Minecraft.getInstance().world;
		if (player != null) {
			// Return if no perception enchant
			ItemStack helmet = player.inventory.armorItemInSlot(3);
			if (helmet == null || !helmet.isItemEqualIgnoreDurability(new ItemStack(Nearsighted.GLASSES))
					|| EnchantmentHelper.getEnchantmentLevel(Nearsighted.PERCEPTION_ENCHANTMENT, helmet) == 0) {
				xrays = new HashMap<>();
				return;
			}
			
			List<Entity> closeEntities = world.getEntitiesInAABBexcluding(player,
					player.getBoundingBox().grow(5, 5, 5), (entity) -> entity instanceof LivingEntity);
			Set<Entity> keepers = new HashSet<>();
			for (Entity e : closeEntities) {
				keepers.add(e);
				MatrixStack matrixStack = event.getMatrixStack();
				matrixStack.push();
				matrixStack.translate(-renderInfo.getProjectedView().getX(), -renderInfo.getProjectedView().getY(),
						-renderInfo.getProjectedView().getZ());
				Matrix4f matrix4f = matrixStack.getLast().getMatrix();
				RenderSystem.pushMatrix();
				RenderSystem.multMatrix(matrix4f);
				RenderSystem.lineWidth(5);
				RenderSystem.disableDepthTest();
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder buffer = tessellator.getBuffer();
				buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				draw(e, buffer);
				tessellator.draw();
				RenderSystem.enableDepthTest();
				RenderSystem.popMatrix();
				matrixStack.pop();
			}
			
			// Remove
			xrays.keySet().removeIf((e) -> !keepers.contains(e));
		}
	}

	private static Map<Entity, List<Vec3d>> xrays = new HashMap<>();

	// Create/move stars
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.CLIENT) {
			for (Entity e : xrays.keySet()) {
				List<Vec3d> positions = xrays.get(e);
				for (int i = positions.size() - 1; i >= 0; i--) {
					Vec3d position = positions.get(i);
					positions.set(i, position.add(0, 0.02f, 0));
	
					if (!e.getBoundingBox().contains(position))
						positions.remove(i);
				}
				if (random.nextFloat() > 0.6f)
					positions.add(randomInBox(e.getBoundingBox()));
			}
		}
	}

	private static Vec3d randomInBox(AxisAlignedBB box) {
		return new Vec3d(MathHelper.lerp(random.nextDouble(), box.minX, box.maxX),
				MathHelper.lerp(random.nextDouble(), box.minY, box.maxY),
				MathHelper.lerp(random.nextDouble(), box.minZ, box.maxZ));
	}

	// Draw stars for specific entity
	private static void draw(Entity entity, BufferBuilder buffer) {
		AxisAlignedBB box = entity.getBoundingBox();
		if (xrays.containsKey(entity)) {
			List<Vec3d> positions = xrays.get(entity);
			for (int i = positions.size() - 1; i >= 0; i--) {
				Vec3d position = positions.get(i);
				buffer.pos(position.x, position.y, position.z).color(1, 1, 0.5f, 1).endVertex();
				buffer.pos(position.x, position.y + (box.maxY - position.y) / (box.maxY - box.minY) * 0.1f, position.z)
						.color(1, 1, 0.5f, 1).endVertex();
			}
		} else {
			List<Vec3d> positions = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				positions.add(randomInBox(box));
			}
			xrays.put(entity, positions);
		}
	}
}
