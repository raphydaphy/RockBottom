package de.ellpeck.game.world.gen.landscape;

import de.ellpeck.game.Constants;
import de.ellpeck.game.ContentRegistry;
import de.ellpeck.game.world.Chunk;
import de.ellpeck.game.world.TileLayer;
import de.ellpeck.game.world.World;
import de.ellpeck.game.world.gen.IWorldGenerator;
import de.ellpeck.game.world.tile.Tile;

import java.util.Random;

public class WorldGenBasicUnderground implements IWorldGenerator{

    @Override
    public boolean shouldGenerate(World world, Chunk chunk){
        return chunk.gridY < 0;
    }

    @Override
    public void generate(World world, Chunk chunk, Random rand){
        for(int x = 0; x < Constants.CHUNK_SIZE; x++){
            for(int y = 0; y < Constants.CHUNK_SIZE; y++){
                for(TileLayer layer : TileLayer.LAYERS){
                    Tile tile;

                    if(chunk.gridY == -1 && rand.nextInt(y+1) >= 8){
                        tile = ContentRegistry.TILE_DIRT;
                    }
                    else{
                        tile = ContentRegistry.TILE_ROCK;
                    }

                    chunk.setTileInner(layer, x, y, tile);
                }
            }
        }
    }

    @Override
    public int getPriority(){
        return 80;
    }
}