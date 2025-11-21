package schmur.asynbo.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import schmur.asynbo.ModItems;

@Mixin(HeldItemRenderer.class)
public class HarvesterSwingOffsetMixin {

    @Inject(method = "applySwingOffset", at = @At("HEAD"), cancellable = true)
    private void modifySwingOffset(MatrixStack matrices, Arm arm, float swingProgress, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            ItemStack stack = client.player.getStackInHand(arm == Arm.RIGHT ? Hand.MAIN_HAND : Hand.OFF_HAND);

            if (stack.isOf(ModItems.HARVESTER)) {
                int i = arm == Arm.RIGHT ? 1 : -1;

                // Speed up animation by doubling swingProgress, clamped to 1.0
                float speedMultiplier = 2.0F;
                float acceleratedProgress = Math.min(swingProgress * speedMultiplier, 1.0F);
                float sweep = MathHelper.sin(acceleratedProgress * (float)Math.PI);

                // Intensity multiplier: set to 1.0F for original potency
                float intensityMultiplier = 1.0F;

                // Translate to handle base (pivot point)
                matrices.translate(0.0F, 0.4F, 0.0F);

                // Apply rotations around the pivot
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)i * ((sweep * 60.0F - 20.0F) * intensityMultiplier)));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((-sweep * 40.0F + 20.0F) * intensityMultiplier));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)i * (sweep * 17.5F * intensityMultiplier)));

                // Translate back from pivot point
                matrices.translate(0.0F, -0.4F, 0.0F);

                ci.cancel();
            }
        }
    }









}
