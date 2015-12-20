package net.huntersharpe.RlGl.arena;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Texts;

import java.util.*;

/**
 * Created by Hunter Sharpe on 12/20/15.
 */
public class ArenaManager {

    private static ArenaManager arenaManager;

    private final Map<UUID, Vector3d> loc = new HashMap<>();
    private final Map<UUID, Inventory> inv = new HashMap<>();

    private final List<Arena> arenas = new ArrayList<>();

    private int arenaSize = 0;

    private ArenaManager(){}

    public ArenaManager getArenaManager(){
        if(this.arenaManager == null)
            this.arenaManager = new ArenaManager();
        return this.arenaManager;
    }

    public Arena getArena(int i){
        for(Arena a : this.arenas){
            if(a.getId() == i){
                return a;
            }
        }
        return null;
    }

    public void addPlayer(Player p, int i) {
        Arena a = this.getArena(i);
        if (a == null) {
            p.sendMessage(Texts.of("Invalid Arena"));
            return;
        }

        if (this.isInGame(p)) {
            p.sendMessage(Texts.of("Cannot join more than 1 game!"));
            return;
        }

        a.getPlayers().add(p.getUniqueId());

        //Put inventory and powerups here

        //Teleport to spawn location here
    }


    public void removePlayer(Player p) {
        Arena a = null;

        for (Arena arena : this.arenas) {
            if (arena.getPlayers().contains(p.getUniqueId()))
                a = arena;
        }

        if (a == null) {
            p.sendMessage(Texts.of("Invalid operation!"));
            return;
        }

        a.getPlayers().remove(p.getName());

        //Teleport to previous location here

    }

   public Arena createArena(int id, Vector3d corner1, Vector3d corner2, Vector3d start1, Vector3d start2, Vector3d finish1, Vector3d finish2) {
        this.arenaSize++;

        Arena a = new Arena(id, corner1, corner2, start1, start2, finish1, finish2);
        this.arenas.add(a);

        return a;
    }

    public void removeArena(int id){

    }

    public boolean isInGame(Player p) {
        for (Arena a : this.arenas) {
            if (a.getPlayers().contains(p.getUniqueId()))
                return true;
        }
        return false;
    }


}
