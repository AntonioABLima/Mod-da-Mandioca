package net.antoniolima.mandiocamod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.antoniolima.mandiocamod.block.entity.PlantedMandiocaBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlantedMandiocaBlockEntityRenderer implements BlockEntityRenderer<PlantedMandiocaBlockEntity> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public PlantedMandiocaBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(PlantedMandiocaBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        int stage = blockEntity.getGrowthStage();

        BlockState state = Blocks.OAK_SAPLING.defaultBlockState(); // Use o bloco de sapling desejado aqui

        poseStack.pushPose();
        poseStack.translate(0.5 * (1 - stage), 1, 0.5 * (1 - stage)); // Move a sapling para cima do bloco
        poseStack.scale(stage, stage, stage); // Ajuste a escala se necessário

        blockRenderDispatcher.renderSingleBlock(state, poseStack, bufferSource, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()), combinedOverlay);

        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);

        return LightTexture.pack(blockLight, skyLight);
    }
}
