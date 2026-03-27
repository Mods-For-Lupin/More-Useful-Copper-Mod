package io.github.jason13official.more_useful_copper.impl.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.item.CopperStatueItem;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public class CopperStatueItemRenderer extends BlockEntityWithoutLevelRenderer {

  public static final CopperStatueItemRenderer INSTANCE = new CopperStatueItemRenderer();

  public CopperStatueItemRenderer() {
    super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
  }

  @Override
  public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
    if (pStack.getItem() instanceof CopperStatueItem) {
      ItemRenderer irenderer = Minecraft.getInstance().getItemRenderer();
      // BakedModel base = irenderer.getItemModelShaper().getModelManager().getModel(CREATURE_CATCHER_BASE);
      pPoseStack.pushPose();
      if (pDisplayContext != ItemDisplayContext.GUI) { //Ground/Hand/etc?
        if (pDisplayContext == ItemDisplayContext.FIXED) {
          pPoseStack.translate(0.0, 0.0, 0.5);
          float scale = 1F;
          pPoseStack.scale(scale, scale, scale);
          //pPoseStack.translate(-0.2F, -0.5F, 0F);
          pPoseStack.mulPose(Axis.XN.rotationDegrees(90));
        } else {
          pPoseStack.translate(0.2, 0.5, 0.5);
          float scale = 0.4F;
          pPoseStack.scale(scale, scale, scale);
          pPoseStack.translate(0F, 0F, 0F);
          pPoseStack.mulPose(Axis.YP.rotationDegrees(45));
        }
      } else { //GUI?
        pPoseStack.translate(0.95F, 0.25F, 0);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(30));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(225));
        float scale = 0.625F;
        pPoseStack.scale(scale, scale, scale);
        pPoseStack.translate(0, 0, 0);
      }
//      for (var model : base.getRenderPasses(pStack, true)) {
//        for (var rendertype : model.getRenderTypes(pStack, true)) {
//          VertexConsumer vertexconsumer = getFoilBufferDirect(pBuffer, rendertype, true, pStack.hasFoil());
//          irenderer.renderModelLists(base, pStack, pPackedLight, pPackedOverlay, pPoseStack, vertexconsumer);
//        }
//      }
      pPoseStack.popPose();

      LivingEntity livingEntity = getEntityFromItemStack(pStack, Minecraft.getInstance().level);
      if (livingEntity != null) {
        renderEntityInInventory(pPoseStack, pDisplayContext, livingEntity, pBuffer);
      }
    }
  }

  private LivingEntity getEntityFromItemStack(ItemStack pStack, ClientLevel level) {

    CopperStatue entity = ModEntities.COPPER_STATUE.create(level);

    if (entity != null && pStack.getItem() instanceof CopperStatueItem statueItem) {
      entity.setVariant(statueItem.type);
    }

    return entity;
  }

  public void renderEntityInInventory(PoseStack matrix, ItemDisplayContext type, LivingEntity pLivingEntity, MultiBufferSource pBuffer) {
    matrix.pushPose();
    matrix.translate(0.5, 0.5, 0.5);

    // Get entity dimensions
    AABB boundingBox = pLivingEntity.getBoundingBox();
    double entityHeight = boundingBox.maxY - boundingBox.minY;
    double entityWidth = Math.max(boundingBox.maxX - boundingBox.minX, boundingBox.maxZ - boundingBox.minZ);

    // Determine scaling factor
    double maxDimension = Math.max(entityWidth, entityHeight);
    // float scale = 0.25F / (float) maxDimension; // Adjust this factor based on your UI needs
    float scale = 0.5F / (float) maxDimension; // Adjust this factor based on your UI needs

    if (type == ItemDisplayContext.FIXED) {
      matrix.translate(0.0, 0.0, -0.125);
      //matrix.translate(0, -0.5, 0);
      //matrix.translate(0, 1.45, 0);
      matrix.mulPose(Axis.XN.rotationDegrees(90));
      matrix.mulPose(Axis.YN.rotationDegrees(180));
      matrix.scale(scale * 2, scale * 2, scale * 2);
    } else if (type == ItemDisplayContext.GUI) {
      //matrix.translate(0, -0.25, 0);
      matrix.translate(0, -entityHeight / 2 * scale * 2, 0); // Centering entity vertically
      matrix.scale(scale * 2, scale * 2, scale * 2);
    } else { //In hand / On ground
      matrix.translate(0, 0.02, 0);
      matrix.scale(scale, scale, scale);
    }

    float rotation = -30;
    if (type == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || type == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
      rotation = 30;
    }
    if (type == ItemDisplayContext.FIXED) {
      rotation = 180;
    }
    matrix.mulPose(Axis.YP.rotationDegrees(rotation));
    pLivingEntity.setYRot(0);
    pLivingEntity.yBodyRot = pLivingEntity.getYRot();
    pLivingEntity.yHeadRot = pLivingEntity.getYRot();
    pLivingEntity.yHeadRotO = pLivingEntity.getYRot();
    EntityRenderDispatcher entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
    entityrenderermanager.setRenderShadow(false);
    RenderSystem.runAsFancy(() -> {
      entityrenderermanager.render(pLivingEntity, 0, 0, 0, 0.0F, Minecraft.getInstance().getFrameTimeNs(), matrix, pBuffer, LightTexture.FULL_BRIGHT);
    });
    entityrenderermanager.setRenderShadow(true);
    matrix.popPose();
  }
}
