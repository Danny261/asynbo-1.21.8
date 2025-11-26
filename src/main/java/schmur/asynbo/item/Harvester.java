package schmur.asynbo.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import schmur.asynbo.ModItems;

import static net.minecraft.block.Block.dropStack;

public class Harvester extends Item {
    public Harvester(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity user = context.getPlayer();

        // Only run server-side
        if (world.isClient()) return ActionResult.PASS;

        // Null check for player
        if (user == null) return ActionResult.PASS;

        // Try to find the block the player is targeting (better than using feet pos)
        BlockPos center;
        HitResult hit = user.raycast(6.0D, 1.0F, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult bhr = (BlockHitResult) hit;
            center = bhr.getBlockPos();
        } else {
            // Fallback
            center = user.getBlockPos().down();
        }

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos pos = center.add(dx, 0, dz);
                BlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                //alle blöcke drum

                // geyoinked aus sweetberry
                if (block instanceof SweetBerryBushBlock) {
                    IntProperty Age = SweetBerryBushBlock.AGE;
                    int age = state.get(SweetBerryBushBlock.AGE);
                    if (age > 1) { //für berry core die drop chance
                        if (world.random.nextInt(1500) == 0) {
                            dropStack(world, pos, new ItemStack(ModItems.BERRY_CORE));
                        }
                        else {
                            int i = state.get(Age);
                            boolean bl = i == 3;
                            if (i > 1) {
                                int j = 1 + world.random.nextInt(2);
                                dropStack(world, pos, new ItemStack(Items.SWEET_BERRIES, j + (bl ? 1 : 0)));
                                world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
                                BlockState blockState = state.with(Age, 1);
                                world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
                                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(user, blockState));
                            }
                        }
                    }
                }
            }
        }

        return ActionResult.SUCCESS_SERVER;
    }
}