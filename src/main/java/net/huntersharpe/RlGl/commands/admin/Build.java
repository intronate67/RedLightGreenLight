/*This file is part of RedLightGreenLight, licensed under the MIT License (MIT).
*
* Copyright (c) 2016 Hunter Sharpe
* Copyright (c) contributors

* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
package net.huntersharpe.RlGl.commands.admin;

import net.caseif.flint.FlintCore;
import net.caseif.flint.arena.Arena;
import net.caseif.flint.util.physical.Boundary;
import net.caseif.flint.util.physical.Location3D;
import net.huntersharpe.RlGl.RedLightGreenLight;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Build implements CommandExecutor {
    //Move to conversation style of arena building.
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Text.of(TextColors.RED, "Only players can build arenas."));
            return CommandResult.success();
        }
        Player player = (Player)src;
        CommentedConfigurationNode config = RedLightGreenLight.getInstance().rootNode();
        if(!config.getNode(args.<String>getOne("id").get()).isVirtual()){
            String id = config.getNode(args.<String>getOne("id").get()).getString();
            Location3D corner1 = new Location3D(
                    config.getNode(id, "corner1", "world").getString(),
                    config.getNode(id, "corner1", "x").getDouble(),
                    config.getNode(id, "corner1", "y").getDouble(),
                    config.getNode(id, "corner1", "z").getDouble()
                    );
            Location3D corner2 = new Location3D(
                    config.getNode(id, "corner2", "world").getString(),
                    config.getNode(id, "corner2", "x").getDouble(),
                    config.getNode(id, "corner2", "y").getDouble(),
                    config.getNode(id, "corner2", "z").getDouble()
            );
            Boundary corners = new Boundary(corner1, corner2);
            //this is going to get ugly.
            int nSp;
            String direction;
            String start;
            int maxSize;
            //Probably not the best way to do this.
            if(config.getNode(id, "start1", "x").getInt() - config.getNode(id, "start2", "x").getInt() >= 1){
                nSp = config.getNode(id, "start1", "x").getInt() - config.getNode(id, "start2", "x").getInt();
                direction = "x";
                start = "start1";
                maxSize = config.getNode(id, "start1", "x").getInt() - config.getNode(id, "start2", "x").getInt();
            }else if(config.getNode(id, "start2", "x").getInt() - config.getNode(id, "start1", "x").getInt() >= 1){
                nSp = config.getNode(id, "start2", "x").getInt() - config.getNode(id, "start1", "x").getInt();
                direction = "x";
                start = "start1";
                maxSize = config.getNode(id, "start2", "x").getInt() - config.getNode(id, "start1", "x").getInt();
            }else if(config.getNode(id, "start1", "z").getInt() - config.getNode(id, "start2", "z").getInt() >= 1){
                nSp = config.getNode(id, "start1", "z").getInt() - config.getNode(id, "start2", "z").getInt();
                direction = "z";
                start = "start1";
                maxSize = config.getNode(id, "start1", "z").getInt() - config.getNode(id, "start2", "z").getInt();
            }else{
                nSp = config.getNode(id, "start2", "z").getInt() - config.getNode(id, "start1", "z").getInt();
                direction = "z";
                start = "start1";
                maxSize = config.getNode(id, "start2", "z").getInt() - config.getNode(id, "start1", "z").getInt();
            }
            Location3D[] spawnPoints = new Location3D[nSp];
            String world = corner1.getWorld().get();
            for(int i = 0; i<nSp; i++){
                if(direction.equalsIgnoreCase("x")){
                    if(start.equalsIgnoreCase("start1")){
                        int x = config.getNode(id, "start1", "x").getInt() + i;
                        spawnPoints[i] = new Location3D(
                                world,
                                x,
                                config.getNode(id, "start1", "y").getInt(),
                                config.getNode(id, "start1", "z").getInt()
                        );
                    }else{
                        int x = config.getNode(id, "start2", "x").getInt() + i;
                        spawnPoints[i] = new Location3D(
                                world,
                                x,
                                config.getNode(id, "start2", "y").getInt(),
                                config.getNode(id, "start2", "z").getInt()
                        );
                    }

                }else{
                    if(start.equalsIgnoreCase("start1")){
                        int z = config.getNode(id, "start1", "z").getInt() + i;
                        spawnPoints[i] = new Location3D(
                                world,
                                config.getNode(id, "start2", "x").getInt(),
                                config.getNode(id, "start1", "y").getInt(),
                                z
                        );
                    }else{
                        int z = config.getNode(id, "start2", "z").getInt() + i;
                        spawnPoints[i] = new Location3D(
                                world,
                                config.getNode(id, "start2", "z").getInt(),
                                config.getNode(id, "start2", "y").getInt(),
                                z
                        );
                    }
                }
            }
            Arena.Builder builder = RedLightGreenLight.getInstance().getMinigame().createBuilder(Arena.class);
            builder
                    .id(id)
                    .displayName(id)
                    .boundary(corners)
                    .spawnPoints(spawnPoints)
                    .build();
            config.getNode(id, "maxSize").setValue(maxSize);
            player.sendMessage(Text.of(TextColors.GREEN, "Built arena ", id));
            return CommandResult.success();
        }
        src.sendMessage(Text.of("No arena with that id exists!"));
        return CommandResult.success();
    }
}
