package elocindev.eldritch_end.mixin;
import com.mojang.blaze3d.systems.RenderSystem;

import elocindev.eldritch_end.config.Configs;
import elocindev.eldritch_end.registry.BlockRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
	// Huge thanks to https://github.com/Steveplays28/biome-fog
	// I used it as reference to understand how fog modification works

	@Inject(method = "applyFog", at = @At("TAIL"))
	private static void eldritchFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
		if (!Configs.CLIENT_CONFIG.enable_fog) return;

		MinecraftClient client = MinecraftClient.getInstance();
		ClientWorld world = client.world;

		if (world == null || client.player == null || !camera.getSubmersionType().equals(CameraSubmersionType.NONE)) return;

		Vec3d pos = client.player.getPos();
		for (int i = 0; i < 8; i++) {
			var bp = new BlockPos(pos.add(0, -i, 0));
			if (world.getBlockState(bp).getBlock() == BlockRegistry.ABYSMAL_FRONDS || world.getBlockState(bp).getBlock() == BlockRegistry.SUSPICIOUS_FRONDS) {
				RenderSystem.setShaderFogStart(MathHelper.lerp(1.0f, vanillaFogStart(viewDistance), 0f + 0.0f));
				RenderSystem.setShaderFogEnd(MathHelper.lerp(1.0f, viewDistance, viewDistance / 3 + 0.0f));
				break;
			}
		}
	}

	private static float vanillaFogStart(float viewDistance) {
		float f = MathHelper.clamp(64.0f, viewDistance / 10.0f, 4.0f);
		return viewDistance - f;
	}
}
