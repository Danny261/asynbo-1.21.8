package schmur.asynbo.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import schmur.asynbo.ASYNBO;

public class ModEnchantment {
    public static final RegistryKey<Enchantment> BERRY_SQUEEZER = of("berry_squeezer");

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(ASYNBO.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    public static void registerModEnchantmentEffects() {
    }
}