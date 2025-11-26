package schmur.asynbo.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;


public class BerryVine extends Block {
    public static final MapCodec<BerryVine> CODEC = createCodec(BerryVine::new);
    public static final Property<Direction> FACING = HorizontalFacingBlock.FACING;

    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0, 0, 15, 16, 16, 16);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 1);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15, 0, 0, 16, 16, 16);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0, 0, 0, 1, 16, 16);

    @Override
    public MapCodec<BerryVine> getCodec() {
        return CODEC;
    }

    public BerryVine(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();

        // Only allow placement on walls (horizontal faces), not floor or ceiling
        if (direction.getAxis().isVertical()) {
            return null;
        }

        // Check if there's a solid block behind where we want to place the vine
        BlockPos behindPos = ctx.getBlockPos().offset(direction.getOpposite());
        BlockState behindState = ctx.getWorld().getBlockState(behindPos);

        // Only allow placement if the block behind is solid and opaque (not air, not another vine, etc.)
        if (!behindState.isSolidBlock(ctx.getWorld(), behindPos) || behindState.isOf(this)) {
            return null;
        }

        return getDefaultState().with(FACING, direction);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
}
