package wbe.tartarusRiches.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import wbe.tartarusRiches.config.Gem;

public class PlayerReceiveGemEvent extends Event implements Cancellable {

    private Player player;

    private Gem gem;

    private Block block;

    private boolean isCancelled;

    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveGemEvent(Player player, Gem gem, Block block) {
        this.player = player;
        this.gem = gem;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Gem getGem() {
        return gem;
    }

    public void setGem(Gem gem) {
        this.gem = gem;
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
