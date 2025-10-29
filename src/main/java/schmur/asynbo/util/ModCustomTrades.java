package schmur.asynbo.util;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import schmur.asynbo.enchantment.ModEnchantment;

public class ModCustomTrades {
    public static void registerModCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 3,
                factories -> {
                    factories.add((entity, random) -> {
                        //create ItemStack named berrySquezzerBook to use later which hold the Items.ENCHANTED_BOOK but unenchanted
                        // so i can later enchant it using the ModEnchantment which i get by...
                        ItemStack berrySqueezerBook = new ItemStack(Items.ENCHANTED_BOOK);

                        entity.getWorld().getRegistryManager()
                                .getOptionalEntry(ModEnchantment.BERRY_SQUEEZER)
                                .ifPresent(enchantment -> {
                                    Object2IntOpenHashMap<RegistryEntry<Enchantment>> enchantments = new Object2IntOpenHashMap<>();
                                    enchantments.put(enchantment, 1);
                                    berrySqueezerBook.set(DataComponentTypes.STORED_ENCHANTMENTS,
                                            new ItemEnchantmentsComponent(enchantments));
                                });

                        return new TradeOffer(
                                new TradedItem(Items.EMERALD, 20),
                                berrySqueezerBook,
                                1, 10, 0.02f
                        );
                    });
                });
    }
}
