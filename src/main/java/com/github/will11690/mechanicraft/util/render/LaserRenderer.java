package com.github.will11690.mechanicraft.util.render;

import com.github.will11690.mechanicraft.blocks.machines.tier4.quarry.TileEntityQuarry;
import com.github.will11690.mechanicraft.util.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class LaserRenderer {
//TODO Create laser color config
	private final static ResourceLocation laserBeam = new ResourceLocation(Reference.MOD_ID + ":textures/misc/laser.png");
    private final static ResourceLocation laserBeam2 = new ResourceLocation(Reference.MOD_ID + ":textures/misc/laser2.png");
    private final static ResourceLocation laserBeamGlow = new ResourceLocation(Reference.MOD_ID + ":textures/misc/laser_glow.png");

    public static void renderLaser(RenderWorldLastEvent event, TileEntity entity, PlayerEntity player, float ticks) {
    	TileEntityQuarry tileQuarry;
    	if (entity instanceof TileEntityQuarry) {
    		tileQuarry = (TileEntityQuarry)entity;
    	} else {
    		return;
    	}
    	
        Vector3d laserCore = new Vector3d(tileQuarry.getLaserCoreX(), tileQuarry.getLaserCoreY(), tileQuarry.getLaserCoreZ());
        Vector3d miningBlock = new Vector3d(tileQuarry.getMiningX(), tileQuarry.getMiningY(), tileQuarry.getMiningZ());

        // parse data from item
        float speedModifier = getSpeedModifier(tileQuarry);
        drawLasers(entity, event, laserCore, miningBlock, 0, 0, 0, /*RED*/255f, /*GREEN*/255f, /*BLUE*/255f, 0.02f, player, ticks, speedModifier);
    }

    private static float getSpeedModifier(TileEntity entity) {
    	//TODO Calculate laser animations speed from speed upgrades
            return -0.02f;
    }

    private static void drawLasers(TileEntity entity, RenderWorldLastEvent event, Vector3d from, Vector3d to, double xOffset, double yOffset, double zOffset, float r, float g, float b, float thickness, PlayerEntity player, float ticks, float speedModifier) {

    	TileEntityQuarry tileQuarry; 
    	
    	if(entity instanceof TileEntityQuarry) {
    		
    		tileQuarry = (TileEntityQuarry)entity;
    		
    	} else {
    		
    		return;
    	}
    	
        IVertexBuilder builder;
        double distance = Math.max(1, from.distanceTo(to));
        long gameTime = player.level.getGameTime();
        double v = gameTime * speedModifier;
        float additiveThickness = (thickness * 3.5f) * calculateLaserFlickerModifier(gameTime);

        float beam2r = 255f/*RED*/;
        float beam2g = 0f/*GREEN*/;
        float beam2b = 0f/*BLUE*/;

        Vector3d view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();

        MatrixStack matrix = event.getMatrixStack();

        matrix.pushPose();

        matrix.translate(-view.x(), -view.y(), -view.z());
        matrix.translate(from.x, from.y, from.z);
        matrix.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(ticks, -player.yRot, -player.yRotO)));
        matrix.mulPose(Vector3f.XP.rotationDegrees(MathHelper.lerp(ticks, player.xRot, player.xRotO)));

        MatrixStack.Entry matrixstack$entry = matrix.last();
        Matrix3f matrixNormal = matrixstack$entry.normal();
        Matrix4f positionMatrix = matrixstack$entry.pose();

        //additive laser beam
        builder = buffer.getBuffer(RenderTypeLaser.LASER_MAIN_ADDITIVE);
        drawBeam(entity, xOffset, yOffset, zOffset, builder, positionMatrix, matrixNormal, additiveThickness, distance, 0.5, 1, ticks, r,g,b,0.7f);

        //main laser, colored part
        builder = buffer.getBuffer(RenderTypeLaser.LASER_MAIN_BEAM);
        drawBeam(entity, xOffset, yOffset, zOffset, builder, positionMatrix, matrixNormal, thickness, distance, v, v + distance * 1.5, ticks, r,g,b,1f);

        //core
        builder = buffer.getBuffer(RenderTypeLaser.LASER_MAIN_CORE);
        drawBeam(entity, xOffset, yOffset, zOffset, builder, positionMatrix, matrixNormal, thickness/2, distance, v, v + distance * 1.5, ticks, beam2r,beam2g,beam2b,1f);
        matrix.popPose();
        buffer.endBatch();
    }

    private static float calculateLaserFlickerModifier(long gameTime) {
        return 0.9f + 0.1f * MathHelper.sin(gameTime * 0.99f) * MathHelper.sin(gameTime * 0.3f) * MathHelper.sin(gameTime * 0.1f);
    }

    private static void drawBeam(TileEntity entity, double xOffset, double yOffset, double zOffset, IVertexBuilder builder, Matrix4f positionMatrix, Matrix3f matrixNormalIn, float thickness, double distance, double v1, double v2, float ticks, float r, float g, float b, float alpha) {

    	TileEntityQuarry tileQuarry;
        
        if (entity instanceof TileEntityQuarry) {
			tileQuarry = (TileEntityQuarry)entity;
		} else {
			return;
		}
        Vector3f vector3f = new Vector3f(0.0f, 1.0f, 0.0f);
        vector3f.transform(matrixNormalIn);
        ClientPlayerEntity player = Minecraft.getInstance().player;
        float startXOffset = -0.20f;
        float startYOffset = -.110f;
        float startZOffset = 0.60f;
        
        // Adjust for fov changing
        startZOffset += (1 - player.getFieldOfViewModifier());

        float f = (MathHelper.lerp(ticks, player.xRotO, player.xRot) - MathHelper.lerp(ticks, player.xBobO, player.xBob));
        float f1 = (MathHelper.lerp(ticks, player.yRotO, player.yRot) - MathHelper.lerp(ticks, player.yBobO, player.yBob));
        startXOffset = startXOffset + (f1 / 750);
        startYOffset = startYOffset + (f / 750);

        Vector4f vec1 = new Vector4f(startXOffset, startYOffset, -thickness + startZOffset, 1.0F);
        vec1.transform(positionMatrix);
        Vector4f vec2 = new Vector4f((float) xOffset, (float) distance + (float) yOffset, (float) -thickness + (float) zOffset, 1.0F);
        vec2.transform(positionMatrix);
        Vector4f vec3 = new Vector4f((float) xOffset, (float) distance + (float) yOffset, thickness + (float) zOffset, 1.0F);
        vec3.transform(positionMatrix);
        Vector4f vec4 = new Vector4f(startXOffset, startYOffset, thickness + startZOffset, 1.0F);
        vec4.transform(positionMatrix);

        //TODO Possibly rearrange vertexts based on rotation of Quarry
        
        builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        //Rendering a 2nd time to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are....
        builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, (float) v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, (float) v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
    }
}