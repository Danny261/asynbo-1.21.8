package schmur.asynbo.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import schmur.asynbo.ModItems;
import schmur.asynbo.block.ModBlocks;

@Mixin(SweetBerryBushBlock.class)
public class ReplaceBerryBushWithGolden {

    @Inject(method = "grow", at = @At("HEAD"), cancellable = true)
    private void replaceWithGoldenBush(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
        int currentAge = state.get(SweetBerryBushBlock.AGE);

        // Check if bush is about to reach age 3
        if (currentAge == 2) {
            // 1 in 500 chance
            if (random.nextInt(1000) == 0) {
                // Replace with golden bush at age 3
                world.setBlockState(pos, ModBlocks.GOLDEN_SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 3), 3);
                ci.cancel(); // Cancel the original method
            }
        }
    }
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void replaceWithGoldenBushRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        int currentAge = state.get(SweetBerryBushBlock.AGE);

        // Check if bush is about to reach age 3
        if (currentAge == 2) {
            // 1 in 500 chance
            if (random.nextInt(1000) == 0) {
                // Replace with golden bush at age 3
                world.setBlockState(pos, ModBlocks.GOLDEN_SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 3), 3);
                ci.cancel(); // Cancel the original method
            }
        }
    }
    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void skipBlockCheckForHarvesterOnUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {

        boolean bl;
        int i = state.get(SweetBerryBushBlock.AGE);
        boolean bl2 = bl = i == 3;
        if (!bl && stack.isOf(Items.BONE_MEAL)) {
            cir.setReturnValue(ActionResult.PASS);
        }
        if (stack.isOf(ModItems.HARVESTER)) {
            cir.setReturnValue(ActionResult.PASS);
        }

    }
}
