/*package schmur.asynbo.block.entity;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import schmur.asynbo.ASYNBO;
import schmur.asynbo.block.custom.MarmeladeOven;
//TODO put item into moditemgroups
public class ModBlocks {

    public static final Block MARMELADE_OVEN = registerBlock("marmelade_oven",
            new MarmeladeOven(AbstractBlock.Settings.create().nonOpaque()));


    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(ASYNBO.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ASYNBO.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(ASYNBO.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        ASYNBO.LOGGER.info("Registering Mod Blocks for " + ASYNBO.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            //entries.add(ModBlocks.PINK_GARNET_BLOCK);
            //entries.add(ModBlocks.RAW_PINK_GARNET_BLOCK);
        });
    }
}
*/
