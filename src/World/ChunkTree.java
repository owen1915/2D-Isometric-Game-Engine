package World;

import java.util.ArrayList;

public class ChunkTree {

    public Chunk[] chunksArr;

    public ChunkTree(ArrayList<Chunk> chunks) {
        chunksArr = new Chunk[chunks.size()];
    }
}
