package net.huntersharpe.RlGl.arena;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;

import java.util.*;

/**
 * Created by Hunter Sharpe on 12/20/15.
 */
public class ArenaManager {

    public static ArenaManager arenaManager = new ArenaManager();

    private final Map<UUID, Vector3d> loc = new HashMap<>();
    private final Map<UUID, Inventory> inv = new HashMap<>();

    private final List<Arena> arenas = new ArrayList<>();

    private int arenaSize = 0;

    public static ArenaManager getArenaManager(){
        return arenaManager;
    }

    public Arena getArena(UUID i){
        for(Arena a : this.arenas){
            if(a.getId() == i){
                return a;
            }
        }
        return null;
    }

    public void addPlayer(Player p, UUID i) {
        Arena a = this.getArena(i);
        if (a == null) {
            p.sendMessage(Text.of("Invalid Arena"));
            return;
        }

        if (this.isInGame(p)) {
            p.sendMessage(Text.of("Cannot join more than 1 game!"));
            return;
        }
        if(a.getPlayers().size() >= a.getSize()){
            p.sendMessage(Text.of("Arena is already full"));
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
            p.sendMessage(Text.of("Invalid operation!"));
            return;
        }

        a.getPlayers().remove(p.getName());

        //Teleport to previous location here

    }

   public Arena createArena(String name, UUID id, Vector3d corner1, Vector3d corner2, Vector3d start1, Vector3d start2, Vector3d finish1, Vector3d finish2, int size) {
        this.arenaSize++;

        Arena a = new Arena(name, id, corner1, corner2, start1, start2, finish1, finish2, size);
        this.arenas.add(a);
        //TODO: Write config values here.
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
