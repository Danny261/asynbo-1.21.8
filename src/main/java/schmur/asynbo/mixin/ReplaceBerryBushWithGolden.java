package schmur.asynbo.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import schmur.asynbo.ModItems;
import schmur.asynbo.block.ModBlocks;

import static net.minecraft.block.Block.dropStack;

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

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void modifyBushSoCoreCanDrop(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        int age = state.get(SweetBerryBushBlock.AGE);
        if (age > 1) {
            if (world.random.nextInt(50) == 0) {
                dropStack(world, pos, new ItemStack(ModItems.BERRY_CORE));
            }
            boolean bl;
            int i = state.get(SweetBerryBushBlock.AGE);
            boolean bl2 = bl = i == 3;
            if (i > 1) {

                int j = 1 + world.random.nextInt(2);
                dropStack(world, pos, new ItemStack(Items.SWEET_BERRIES, j + (bl ? 1 : 0)));
                world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
                BlockState blockState = (BlockState) state.with(SweetBerryBushBlock.AGE, 1);
                world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
                cir.setReturnValue(ActionResult.SUCCESS);

            }



        }

    }
}
