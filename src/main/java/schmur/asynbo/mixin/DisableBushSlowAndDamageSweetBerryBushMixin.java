package schmur.asynbo.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import schmur.asynbo.enchantment.ModEnchantment;

@Mixin(SweetBerryBushBlock.class)
public class DisableBushSlowAndDamageSweetBerryBushMixin {
    @WrapMethod(method = "onEntityCollision")
    public void asynbo$disableDamage(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler, boolean bl, Operation<Void> original) {
        if (entity instanceof LivingEntity livingEntity) {


            if (livingEntity.getEntityWorld().getRegistryManager().getOptionalEntry(ModEnchantment.BERRY_SQUEEZER).isPresent() && EnchantmentHelper
                    .getEnchantments(livingEntity.getEquippedStack(EquipmentSlot.FEET)).getEnchantments()
                    .contains(livingEntity.getEntityWorld().getRegistryManager().getOptionalEntry(ModEnchantment.BERRY_SQUEEZER).get())) {;

            } else {
                original.call(state, world, pos, entity, handler, bl);
            }
        }
        }

}
