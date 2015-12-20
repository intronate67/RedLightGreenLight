package net.huntersharpe.RlGl.arena;

import com.flowpowered.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Hunter Sharpe on 12/20/15.
 */
public class Arena {

    private final int id;
    private final Vector3d corner1;
    private final Vector3d corner2;
    private final Vector3d start1;
    private final Vector3d start2;
    private final Vector3d finish1;
    private final Vector3d finish2;
    private final List<UUID> players = new ArrayList<UUID>();

    public Arena(int id, Vector3d corner1, Vector3d corner2, Vector3d start1, Vector3d start2, Vector3d finish1, Vector3d finish2) {
        this.id = id;
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.start1 = start1;
        this.start2 = start2;
        this.finish1 = finish1;
        this.finish2 = finish2;
    }

    public int getId(){
        return this.id;
    }

    public List<UUID> getPlayers(){
        return this.players;
    }
}
