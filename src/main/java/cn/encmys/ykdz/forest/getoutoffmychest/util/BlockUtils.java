package cn.encmys.ykdz.forest.getoutoffmychest.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockUtils {

    /**
     * Open or close a chest block visually
     * @param block - chest block
     * @param opened - open or close
     */
    public static void setChestOpened(Block block, boolean opened) {
        PacketContainer libPacket = new PacketContainer(PacketType.Play.Server.BLOCK_ACTION);
        libPacket.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
        libPacket.getIntegers().write(0, 1);
        libPacket.getIntegers().write(1, opened ? 1 : 0);
        libPacket.getBlocks().write(0, block.getType());
        libPacket.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
        int distanceSquared = 64 * 64;
        Location loc = block.getLocation();
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        for (Player player : block.getWorld().getPlayers()) {
            if (player.getLocation().distanceSquared(loc) < distanceSquared) {
                manager.sendServerPacket(player, libPacket);
            }
        }
    }
}
