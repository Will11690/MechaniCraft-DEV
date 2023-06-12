package com.github.will11690.mechanicraft.util.render;

import com.github.will11690.mechanicraft.blocks.machines.tier4.quarry.TileEntityQuarry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class NewLaserRenderer {
	
	//WILL TOUCH THE BLOCK BEING MINED AND MOVE THROUGH EACH ONE BUT START POINT IS COMPLETELY WRONG FOR SOME REASON(appears to be a couple hundred blocks away from the true start point)
	//START POS AND END POS FOR LASER ARE CORRECT AS FAR AS I CAN TELL IN THE CONSOLE
	
	public static void renderLaser(RenderWorldLastEvent event, TileEntity te, MatrixStack matrixStackIn, IRenderTypeBuffer.Impl bufferIn, boolean reverse) {
		
		TileEntityQuarry tileQuarry;
		
		if(te instanceof TileEntityQuarry) {
			tileQuarry = (TileEntityQuarry)te;
		
			Vector3f offset = new Vector3f(0.50f/*amount to offset the X value of the beam*/, (float)0.50f/*amount to offset the Y value of the beam*/, (float)0.50f/*amount to offset the Z value of the beam*/);
		
			BlockPos startBlock = new BlockPos(tileQuarry.getLaserCoreX(), tileQuarry.getLaserCoreY(), tileQuarry.getLaserCoreZ());
			BlockPos endBlock = new BlockPos(tileQuarry.getMiningX(), tileQuarry.getMiningY(), tileQuarry.getMiningZ());
		
			drawLaser(te, startBlock, endBlock, matrixStackIn, bufferIn, offset, 0.50f, 0.50f, 0.50f, 1.0f, 0.10f, 255.00f, 0.00f, 0.00f, 0.70f, 0.05f, reverse);
		}
	}
    
    public static void drawLaser(TileEntity te, BlockPos startBlock, BlockPos endBlock, MatrixStack matrixStackIn, IRenderTypeBuffer.Impl bufferIn, Vector3f offset, float r, float g, float b, float alpha, float thickness, float r2, float g2, float b2, float alpha2, float thickness2, boolean reverse) {
        World level = te.getLevel();
        long gameTime = level.getGameTime();
        double v = gameTime * 0.04;

        float diffX = endBlock.getX() + offset.x() - startBlock.getX();
        float diffY = endBlock.getY() + offset.y() - startBlock.getY();
        float diffZ = endBlock.getZ() + offset.z() - startBlock.getZ();

        IVertexBuilder builder;
        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStackIn.pushPose();
        Matrix4f positionMatrix = matrixStackIn.last().pose();

        matrixStackIn.translate(startBlock.getX() - projectedView.x, startBlock.getY() - projectedView.y, startBlock.getZ() - projectedView.z);

        //Vector3f startLaser = new Vector3f((endBlock.getX() + offset.x() - startBlock.getX()), (startBlock.getY()) + offset.y(), (endBlock.getZ() + offset.z()) - startBlock.getZ());
        Vector3f startLaser = new Vector3f(offset.x(), offset.y(), offset.z());
        Vector3f endLaser = new Vector3f(diffX, diffY, diffZ);
        
        builder = bufferIn.getBuffer(RenderTypeLaser.LASER_MAIN_ADDITIVE);
        drawBeams(builder, positionMatrix, startLaser, endLaser, r2, g2, b2, alpha2, thickness2, v, v + diffY * 2.0, te);
        bufferIn.endBatch(RenderTypeLaser.LASER_MAIN_ADDITIVE); //This apparently is needed in RenderWorldLast
        
        builder = bufferIn.getBuffer(RenderTypeLaser.LASER_MAIN_BEAM);
        drawBeams(builder, positionMatrix, startLaser, endLaser, r, g, b, alpha, thickness, v, v + diffY * 4.5, te);
        bufferIn.endBatch(RenderTypeLaser.LASER_MAIN_BEAM); //This apparently is needed in RenderWorldLast

        builder = bufferIn.getBuffer(RenderTypeLaser.LASER_MAIN_CORE);
        drawBeams(builder, positionMatrix, startLaser, endLaser, r2, g2, b2, alpha2, thickness2/2, v, v + diffY * 1.5, te);
        bufferIn.endBatch(RenderTypeLaser.LASER_MAIN_CORE); //This apparently is needed in RenderWorldLast

        matrixStackIn.popPose();
    }
    
    public static Vector3f adjustBeamToEyes(Vector3f from, Vector3f to, TileEntity te) {
    	TileEntityQuarry tileQuarry;
        PlayerEntity player = Minecraft.getInstance().player;
        
        if(te instanceof TileEntityQuarry) {
        	
        	tileQuarry = (TileEntityQuarry)te;
        } else {
        	
        	return null;
        }
        
        Vector3f P = new Vector3f((float)player.getX() - (float)tileQuarry.getLaserCoreX(), (float)player.getEyeY() - (float)tileQuarry.getLaserCoreY(), (float)player.getZ() - (float)tileQuarry.getLaserCoreZ());

        Vector3f PS = from.copy();
        PS.sub(P);
        Vector3f SE = to.copy();
        SE.sub(from.copy());

        Vector3f adjustedVec = PS.copy();
        adjustedVec.cross(SE.copy());
        adjustedVec.normalize();
        
        return adjustedVec;
    }

    public static void drawBeams(IVertexBuilder builder, Matrix4f positionMatrix, Vector3f from, Vector3f to, float r, float g, float b, float alpha, float thickness, double v1, double v2, TileEntity te) {
        Vector3f adjustedVec = adjustBeamToEyes(from, to, te);
        adjustedVec.mul(thickness); //Determines how thick the beam is

        Vector3f p1 = from.copy();
        p1.add(adjustedVec);
        Vector3f p2 = from.copy();
        p2.sub(adjustedVec);
        Vector3f p3 = to.copy();
        p3.add(adjustedVec);
        Vector3f p4 = to.copy();
        p4.sub(adjustedVec);

        builder.vertex(positionMatrix, p1.x(), p1.y(), p1.z()).color(r, g, b, alpha).uv(1, (float) v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        builder.vertex(positionMatrix, p3.x(), p3.y(), p3.z()).color(r, g, b, alpha).uv(1, (float) v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        builder.vertex(positionMatrix, p4.x(), p4.y(), p4.z()).color(r, g, b, alpha).uv(0, (float) v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        builder.vertex(positionMatrix, p2.x(), p2.y(), p2.z()).color(r, g, b, alpha).uv(0, (float) v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
    }
}