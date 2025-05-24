package wbe.tartarusRiches.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import wbe.tartarusRiches.config.Ore;

public class PlayerReceiveGemEvent extends Event implements Cancellable {

    private Player player;

    private Block block;

    private Ore ore;

    private boolean isCancelled = false;

    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveGemEvent(Player player, Ore ore, Block block) {
        this.player = player;
        this.ore = ore;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Ore getOre() {
        return ore;
    }

    public void setOre(Ore ore) {
        this.ore = ore;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
