package schmur.asynbo;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import schmur.asynbo.item.Harvester; // add import for custom item
import schmur.asynbo.item.Marmelade;

import javax.swing.text.html.parser.Entity;
import java.util.function.Function;

import static net.minecraft.item.Items.GLASS_BOTTLE;

public class ModItems {
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ASYNBO.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static final Item GOLDEN_BERRY = register(
            "golden_berry",
            Item::new,
            new Item.Settings().food(new FoodComponent.Builder().nutrition(7).saturationModifier(1.4F).build())
    );
    public static final Item BERRY_CORE = register(
            "berry_core",
            Item::new,
            new Item.Settings().maxCount(1).rarity(Rarity.EPIC)
    );
    public static final Item HARVESTER = register(
            "harvester",
            Harvester::new, // use the custom Harvester constructor so its use method is called
            new Item.Settings().tool(ToolMaterial.NETHERITE, BlockTags.CROPS, 6f, 0,0f).rarity(Rarity.EPIC)
    );
    public static final Item MARMELADE_GLASS = register(
            "marmelade_glass",
            Item::new,
            new Item.Settings().maxCount(16).equippable(EquipmentSlot.HEAD)
    );
    public static final Item MARMELADE_GLASS_FULL = register(
            "marmelade_glass_full",
            PotionItem::new,
            new Item.Settings().maxCount(1).component(DataComponentTypes.CONSUMABLE,
            ConsumableComponents.DRINK).useRemainder(MARMELADE_GLASS)
    );



    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.GOLDEN_BERRY));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.HARVESTER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.BERRY_CORE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.MARMELADE_GLASS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.MARMELADE_GLASS_FULL));


    }
}
