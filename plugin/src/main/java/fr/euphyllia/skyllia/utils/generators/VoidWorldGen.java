package fr.euphyllia.skyllia.utils.generators;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class VoidWorldGen extends ChunkGenerator {

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        int xMin = 0;
        int xMax = 16;
        int zMin = 0;
        int zMax = 16;
        Material blockMaterial = switch (worldInfo.getEnvironment()) {
            case NORMAL -> Material.STONE;
            case NETHER -> Material.NETHERRACK;
            case THE_END -> Material.END_STONE;
            default -> Material.AIR;
        };
        fillChunk(chunkData, xMin, xMax, zMin, zMax, blockMaterial);
    }

    private void fillChunk(ChunkData chunkData, int minX, int maxX, int minZ, int maxZ, Material material) {
        for (int y = chunkData.getMinHeight(); y < chunkData.getMaxHeight(); y++) {
            for (int x = minX; x < maxX; x++) {
                for (int z = minZ; z < maxZ; z++) {
                    chunkData.setBlock(x, y, z, material);
                }
            }
        }
    }
}
