package epicsquid.mysticalworld.entity.model;

import epicsquid.mysticalworld.entity.FrogEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

import javax.annotation.Nonnull;

/**
 * FrogModel - Elucent
 * Created using Tabula 5.1.0
 */
public class FrogModel extends EntityModel<FrogEntity> {

	private RendererModel body;
	private RendererModel backR;
	private RendererModel backL;
	private RendererModel frontR;
	private RendererModel frontL;
	private RendererModel head;

	public FrogModel() {
		this.textureWidth = 16;
		this.textureHeight = 16;
		this.head = new RendererModel(this, 4, 3);
		this.head.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.head.addBox(-1.5F, -1.0F, -3.0F, 3, 2, 3, 0.0F);
		this.setRotateAngle(head, 0.39269908169872414F, 0.0F, 0.0F);
		this.backL = new RendererModel(this, 0, 0);
		this.backL.setRotationPoint(1.0F, 0.5F, 1.5F);
		this.backL.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
		this.setRotateAngle(backL, -0.6981317007977318F, -0.5235987755982988F, -0.5235987755982988F);
		this.frontL = new RendererModel(this, 0, 5);
		this.frontL.mirror = true;
		this.frontL.setRotationPoint(1.0F, -0.25F, -2.5F);
		this.frontL.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(frontL, 0.2617993877991494F, 0.0F, -0.2617993877991494F);
		this.body = new RendererModel(this, 0, 9);
		this.body.setRotationPoint(0.0F, 22.0F, 0.0F);
		this.body.addBox(-1.5F, -1.0F, -3.0F, 3, 2, 5, 0.0F);
		this.setRotateAngle(body, -0.5235987755982988F, 0.0F, 0.0F);
		this.backR = new RendererModel(this, 0, 0);
		this.backR.mirror = true;
		this.backR.setRotationPoint(-1.0F, 0.5F, 1.5F);
		this.backR.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
		this.setRotateAngle(backR, -0.6981317007977318F, 0.5235987755982988F, 0.5235987755982988F);
		this.frontR = new RendererModel(this, 0, 5);
		this.frontR.mirror = true;
		this.frontR.setRotationPoint(-1.0F, -0.25F, -2.5F);
		this.frontR.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(frontR, 0.2617993877991494F, 0.0F, 0.2617993877991494F);
		this.body.addChild(this.head);
		this.body.addChild(this.backL);
		this.body.addChild(this.frontL);
		this.body.addChild(this.backR);
		this.body.addChild(this.frontR);
	}

	@Override
	public void render(@Nonnull FrogEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		float sin = (float) Math.sin(ageInTicks * 0.125f * (Math.PI * 2.0f));
		this.backL.rotateAngleX = -0.6981317007977318F + 1.5f * ((FrogEntity) entity).getOffGround(ageInTicks - (int) ageInTicks);
		this.backR.rotateAngleX = -0.6981317007977318F + 1.5f * ((FrogEntity) entity).getOffGround(ageInTicks - (int) ageInTicks);
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.body.render(scaleFactor);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	private void setRotateAngle(@Nonnull RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}