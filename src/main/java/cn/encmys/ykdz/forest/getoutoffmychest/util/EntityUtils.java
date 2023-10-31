package cn.encmys.ykdz.forest.getoutoffmychest.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

import java.util.Collection;

public class EntityUtils {

    /**
     * Get all entities above a block
     * @param b - The block
     * @param high - Which height range of entities will be returned
     * @return - A collection contains all entities
     */
    public static Collection<Entity> getEntitiesAboveBlock(Block b, double high) {

        Location bLoc = b.getLocation();
        double x = bLoc.getBlockX() + 0.8;
        double y = bLoc.getBlockY() - 0.5;
        double z = bLoc.getBlockZ() + 0.8;

        BoundingBox box = new BoundingBox(x, y, z, x - 1.6, y + high + 1, z - 1.6);

        return bLoc.getWorld().getNearbyEntities(box);
    }
}
