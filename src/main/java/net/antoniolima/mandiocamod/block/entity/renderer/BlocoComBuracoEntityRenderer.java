package net.antoniolima.mandiocamod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.antoniolima.mandiocamod.block.entity.BlocoComBuracoBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlocoComBuracoEntityRenderer implements BlockEntityRenderer<BlocoComBuracoBlockEntity> {
    private final ItemRenderer itemRenderer;

    public BlocoComBuracoEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        this.itemRenderer = pContext.getItemRenderer();
    }

    @Override
    public void render(BlocoComBuracoBlockEntity pBlockEntity, float pPartialTick,  PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemStack itemStack = pBlockEntity.getRenderStack();
        if(itemStack != ItemStack.EMPTY){
            pPoseStack.pushPose();
            pPoseStack.translate(0.5f , 0.74f, 0.5f);
            pPoseStack.scale(0.25f, 0.45f, 0.25f);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

            this.itemRenderer.renderStatic(
                    itemStack,
                    ItemDisplayContext.FIXED,
                    getLightLevel(
                            pBlockEntity.getLevel(),
                            pBlockEntity.getBlockPos()
                    ),
                    OverlayTexture.NO_OVERLAY,
                    pPoseStack,
                    pBuffer,
                    pBlockEntity.getLevel(),
                    1
            );

            pPoseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);

        return LightTexture.pack(bLight, sLight);
    }

}
