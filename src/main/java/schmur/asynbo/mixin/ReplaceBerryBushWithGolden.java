package schmur.asynbo.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import schmur.asynbo.block.ModBlocks;

@Mixin(SweetBerryBushBlock.class)
public class ReplaceBerryBushWithGolden {

    @Inject(method = "grow", at = @At("HEAD"), cancellable = true)
    private void replaceWithGoldenBush(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
        int currentAge = state.get(SweetBerryBushBlock.AGE);

        // Check if bush is about to reach age 3
        if (currentAge == 2) {
            // 1 in 500 chance
            if (random.nextInt(5) == 0) {
                // Replace with golden bush at age 3
                world.setBlockState(pos, ModBlocks.GOLDEN_SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 3), 3);
                ci.cancel(); // Cancel the original method
            }
        }
    }
}
