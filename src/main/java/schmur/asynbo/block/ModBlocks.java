package schmur.asynbo.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import schmur.asynbo.ASYNBO;
import schmur.asynbo.block.custom.BerryVine;
import schmur.asynbo.block.custom.GoldenSweetBerryBush;

import java.util.function.Function;

import static net.minecraft.block.Blocks.SWEET_BERRY_BUSH;

public class ModBlocks {

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ASYNBO.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ASYNBO.MOD_ID, name));
    }

    public static final Block GOLDEN_SWEET_BERRY_BUSH = register(
            "golden_sweet_berry_bush",
            GoldenSweetBerryBush::new,
            AbstractBlock.Settings.create().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH),
            true
    );
    public static final Block BERRY_BLOCK = register(
            "berry_block",
            Block::new,
            AbstractBlock.Settings.create().burnable().hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque(),
            true
    );


    public static final Block BERRY_VINES = register(
            "berry_vines",
            BerryVine::new,
            AbstractBlock.Settings.create()
                    .burnable()
                    .nonOpaque()
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.VINE)
                    .pistonBehavior(PistonBehavior.DESTROY),
            true
    );
    public static final Block GOLDEN_BERRY_BLOCK = register(
            "golden_berry_block",
            Block::new,
            AbstractBlock.Settings.create().burnable().hardness(0.2f).resistance(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque(),
            true
    );


    public static final Block GOLDEN_BERRY_VINES = register(
            "golden_berry_vines",
            BerryVine::new,
            AbstractBlock.Settings.create()
                    .burnable()
                    .nonOpaque()
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.VINE)
                    .pistonBehavior(PistonBehavior.DESTROY),
            true
    );

    public static final Block POTTED_SWEET_BERRY;
    static {
        AbstractBlock.Settings bSettings = AbstractBlock.Settings.create()
                .breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY);

        POTTED_SWEET_BERRY = register(
                "potted_sweet_berry",
                (settings) -> new FlowerPotBlock(SWEET_BERRY_BUSH, settings),
                bSettings,
                false
        );
    }


    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
                .register((itemGroup) -> itemGroup.add(ModBlocks.BERRY_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
                .register((itemGroup) -> itemGroup.add(ModBlocks.BERRY_VINES));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
                .register((itemGroup) -> itemGroup.add(ModBlocks.GOLDEN_BERRY_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
                .register((itemGroup) -> itemGroup.add(ModBlocks.GOLDEN_BERRY_VINES));

        // Register the potted block with the empty flower pot



    }

}



//TODO add more from the tut like translations and actual registering