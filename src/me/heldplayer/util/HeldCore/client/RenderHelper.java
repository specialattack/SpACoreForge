
package me.heldplayer.util.HeldCore.client;

import me.heldplayer.util.HeldCore.MathHelper;
import me.heldplayer.util.HeldCore.Vector;
import me.heldplayer.util.HeldCore.VectorPool;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

/**
 * A helper class used for rendering in 3D-space
 * 
 * @author heldplayer
 * 
 */
public final class RenderHelper {

    public static Vector[] getBezierPlanePoints(Vector[][] points, int pointCount) {
        int actualLength = 0;
        for (int i = 0; i < points[0].length; i++) {
            if (points[0][i] == null) {
                break;
            }
            actualLength++;
        }
        Vector[][] list = new Vector[pointCount + 1][];
        for (int i = 0; i < list.length; i++) {
            list[i] = VectorPool.getFreeVectorArray(actualLength);
        }

        for (int j = 0; j <= pointCount; j++) {
            for (int i = 0; i < points.length; i++) {
                list[j][i] = MathHelper.bezier(points[i], (double) j / (double) pointCount);
            }
        }

        Vector[] result = VectorPool.getFreeVectorArray(list.length * (pointCount + 1));

        for (int j = 0; j <= pointCount; j++) {
            for (int i = 0; i < list.length; i++) {
                result[i + j * (pointCount + 1)] = MathHelper.bezier(list[i], (double) j / (double) pointCount);
            }
        }

        return result;
    }

    public static void renderBezierPlane(Vector[][] points, Icon icon, int pointCount) {
        Vector[] result = getBezierPlanePoints(points, pointCount);

        GL11.glBegin(GL11.GL_QUADS);
        for (int i = 0; i < pointCount; i++) {
            for (int j = 0; j < pointCount; j++) {
                Vector point1 = result[i + j * (pointCount + 1)];
                Vector point2 = result[i + (j + 1) * (pointCount + 1)];
                Vector point3 = result[i + 1 + j * (pointCount + 1)];
                Vector point4 = result[i + 1 + (j + 1) * (pointCount + 1)];
                GL11.glTexCoord2d(icon.getInterpolatedU((double) i / (double) pointCount * 16.0D), icon.getInterpolatedV((double) (j + 1) / (double) pointCount * 16.0D));
                GL11.glVertex3d(point2.posX, point2.posY, point2.posZ);
                GL11.glTexCoord2d(icon.getInterpolatedU((double) (i + 1) / (double) pointCount * 16.0D), icon.getInterpolatedV((double) (j + 1) / (double) pointCount * 16.0D));
                GL11.glVertex3d(point4.posX, point4.posY, point4.posZ);
                GL11.glTexCoord2d(icon.getInterpolatedU((double) (i + 1) / (double) pointCount * 16.0D), icon.getInterpolatedV((double) j / (double) pointCount * 16.0D));
                GL11.glVertex3d(point3.posX, point3.posY, point3.posZ);
                GL11.glTexCoord2d(icon.getInterpolatedU((double) i / (double) pointCount * 16.0D), icon.getInterpolatedV((double) j / (double) pointCount * 16.0D));
                GL11.glVertex3d(point1.posX, point1.posY, point1.posZ);

                GL11.glTexCoord2d(icon.getInterpolatedU((double) i / (double) pointCount * 16.0D), icon.getInterpolatedV((double) j / (double) pointCount * 16.0D));
                GL11.glVertex3d(point1.posX, point1.posY, point1.posZ);
                GL11.glTexCoord2d(icon.getInterpolatedU((double) (i + 1) / (double) pointCount * 16.0D), icon.getInterpolatedV((double) j / (double) pointCount * 16.0D));
                GL11.glVertex3d(point3.posX, point3.posY, point3.posZ);
                GL11.glTexCoord2d(icon.getInterpolatedU((double) (i + 1) / (double) pointCount * 16.0D), icon.getInterpolatedV((double) (j + 1) / (double) pointCount * 16.0D));
                GL11.glVertex3d(point4.posX, point4.posY, point4.posZ);
                GL11.glTexCoord2d(icon.getInterpolatedU((double) i / (double) pointCount * 16.0D), icon.getInterpolatedV((double) (j + 1) / (double) pointCount * 16.0D));
                GL11.glVertex3d(point2.posX, point2.posY, point2.posZ);
            }
        }
        GL11.glEnd();
    }

}
