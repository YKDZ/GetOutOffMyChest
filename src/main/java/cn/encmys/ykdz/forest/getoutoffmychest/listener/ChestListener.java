package cn.encmys.ykdz.forest.getoutoffmychest.listener;

import cn.encmys.ykdz.forest.getoutoffmychest.GetOutOffMyChest;
import cn.encmys.ykdz.forest.getoutoffmychest.util.BlockUtils;
import cn.encmys.ykdz.forest.getoutoffmychest.util.EntityUtils;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Iterator;

/**
 * Handle the chest open event
 */
public class ChestListener implements Listener {

    private FileConfiguration config = GetOutOffMyChest.getMainConfig();
    private float speed = config.getLong("options.speed", 1);
    private float angle = config.getLong("options.angle", 45);

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onChestOpened(PlayerInteractEvent e) {

        if(e.getAction() != Action.RIGHT_CLICK_BLOCK || e.useInteractedBlock() == Event.Result.DENY || !e.hasBlock() || !e.getClickedBlock().getType().toString().contains("CHEST")) { return; }

        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Vector vec = ((Chest) b.getBlockData()).getFacing().getOppositeFace().getDirection().normalize().multiply(speed).add(new Vector(0, speed * angle / 45, 0));
        Collection<Entity> entities = EntityUtils.getEntitiesAboveBlock(b, 1d);
        Iterator<Entity> iterator = entities.iterator();

        boolean flag = false;

        while(iterator.hasNext()) {
            Entity entity = iterator.next();
            if(entity.getType() == EntityType.CAT && ((Sittable) entity).isSitting()) {
                entity.setVelocity(vec);
                flag = true;
            }
        }

        if(flag) {
            org.bukkit.block.Chest chest = (org.bukkit.block.Chest) b.getState();
            e.setCancelled(false);
            p.openInventory(chest.getInventory());
            BlockUtils.setChestOpened(b, true);
        }

    }

}
