package MainConfig.TextureSplicer;

import java.awt.*;

/*~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class GridManager {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Conversions
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Methods to convert to iso cords
     */

    int scale = 32;

    public int[] isoToScreen(int[] cords){
        int xCor = (cords[0] - cords[1]) * scale;
        int yCor = (cords[0] + cords[1]) * (scale / 2);
        return new int[]{xCor, yCor};
    }

    public int[] isoToScreen(int[] cords, int scale){
        int xCor = (cords[0] - cords[1]) * scale;
        int yCor = (cords[0] + cords[1]) * (scale / 2);
        return new int[]{xCor, yCor};
    }

    public int[] ScreenToIso(int x, int y, int scale){
        x -= 32;

        float isoX = (float) (x + 2 * y) / (2 * scale);
        float isoY = (float) (2 * y - x) / (2 * scale);

        //needs to be corrected because it doesn't round in the right direction
        if (isoY < 0){
            isoY--;
        }

        return new int[]{(int) isoX, (int) isoY};
    }

    //Triangle Polygon Tool
    public Polygon getRightTriangle(int xCor, int yCor){
        Polygon polygon = new Polygon();
        polygon.npoints = 3;
        polygon.xpoints = new int[]{xCor, xCor + (this.scale), xCor};
        polygon.ypoints = new int[]{yCor, yCor + (this.scale / 2), yCor + (this.scale)};
        return polygon;
    }

    public Polygon getLeftTriangle(int xCor, int yCor){
        Polygon polygon = new Polygon();
        polygon.npoints = 3;
        polygon.xpoints = new int[]{xCor, xCor + (scale), xCor + (scale)};
        polygon.ypoints = new int[]{yCor + (scale / 2), yCor, yCor + (scale)};
        return polygon;
    }

    public long screenCorToKey(int x, int y){
        return 0;
    }

    public boolean isOnScreen(long key){
        return false;
    }

    public Polygon Square(int scale) {
        Polygon polygon = new Polygon();
        polygon.npoints = 4;
        polygon.xpoints = new int[]{-scale, 0, scale, 0};
        polygon.ypoints = new int[]{(scale / 2), 0, scale/2, (scale)};
        return polygon;
    }
}
