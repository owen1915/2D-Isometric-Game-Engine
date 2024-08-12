package MainConfig.TextureSplicer;

public enum Texture {
    LeftTopFace( new int[]{0, -16}, false, -65536),
    LeftBotFace( new int[]{0, -32}, true,  -196376),
    //16727553
    TopLeftFace( new int[]{0, 0}, true,  -786688),
    //-196376
    TopRightFace(new int[]{-32, 0}, false,  -16253184),
    RightTopFace(new int[]{-32, -16}, true,  -16727553),
    //-3801344
    RightBotFace(new int[]{-32, -32}, false,  -9568001);

    public final int[] translations;
    public boolean leftFacing;
    public int maskRGB;
    Texture(int[] translations, boolean leftFacing, int maskRGB){
        this.translations = translations;
        this.leftFacing = leftFacing;
        this.maskRGB = maskRGB;
    }
}
