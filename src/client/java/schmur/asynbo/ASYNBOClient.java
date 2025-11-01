package schmur.asynbo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import schmur.asynbo.block.ModBlocks;

public class ASYNBOClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// Register the golden sweet berry bush to use cutout rendering for transparency
		BlockRenderLayerMap.putBlock(ModBlocks.GOLDEN_SWEET_BERRY_BUSH, BlockRenderLayer.CUTOUT);
	}
}