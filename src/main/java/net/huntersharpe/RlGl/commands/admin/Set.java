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

import java.io.IOException;

public class Set implements CommandExecutor{

    //Move to conversation style of arena building.
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of(TextColors.RED, "Only players can run this command!"));
            return CommandResult.success();
        }
        Player player = (Player)src;
        CommentedConfigurationNode config = RedLightGreenLight.getInstance().rootNode();
        if(!config.getNode(args.<String>getOne("id").get()).isVirtual()){
            int position = args.<Integer>getOne("position").get();
            String id = args.<String>getOne("id").get();
            switch(args.<String>getOne("type").get()){
                case "corner":
                    if(position == 1){
                        config.getNode(id, "corner1", "world").setValue(player.getWorld().getName());
                        config.getNode(id, "corner1", "x").setValue(player.getLocation().getX());
                        config.getNode(id, "corner1", "y").setValue(player.getLocation().getY());
                        config.getNode(id, "corner1", "z").setValue(player.getLocation().getZ());
                        saveConfig(config);
                        sendMessage(player, "corner1");
                    }else{
                        config.getNode(id, "corner2", "world").setValue(player.getWorld().getName());
                        config.getNode(id, "corner2", "x").setValue(player.getLocation().getX());
                        config.getNode(id, "corner2", "y").setValue(player.getLocation().getY());
                        config.getNode(id, "corner2", "z").setValue(player.getLocation().getZ());
                        saveConfig(config);
                        sendMessage(player, "corner2");
                    }
                    break;
                case "start":
                    if(position == 1){
                        config.getNode(id, "start1", "world").setValue(player.getWorld().getName());
                        config.getNode(id, "start1", "x").setValue(player.getLocation().getX());
                        config.getNode(id, "start1", "y").setValue(player.getLocation().getY());
                        config.getNode(id, "start1", "z").setValue(player.getLocation().getZ());
                        saveConfig(config);
                        sendMessage(player, "start1");
                    }else{
                        config.getNode(id, "start2", "world").setValue(player.getWorld().getName());
                        config.getNode(id, "start2", "x").setValue(player.getLocation().getX());
                        config.getNode(id, "start2", "y").setValue(player.getLocation().getY());
                        config.getNode(id, "start2", "z").setValue(player.getLocation().getZ());
                        saveConfig(config);
                        sendMessage(player, "start2");
                    }
                    break;
                case "finish":
                    if(position == 1){
                        config.getNode(id, "finish1", "world").setValue(player.getWorld().getName());
                        config.getNode(id, "finish1", "x").setValue(player.getLocation().getX());
                        config.getNode(id, "finish1", "y").setValue(player.getLocation().getY());
                        config.getNode(id, "finish1", "z").setValue(player.getLocation().getZ());
                        saveConfig(config);
                        sendMessage(player, "finish1");
                    }else{
                        config.getNode(id, "finish2", "world").setValue(player.getWorld().getName());
                        config.getNode(id, "finish2", "x").setValue(player.getLocation().getX());
                        config.getNode(id, "finish2", "y").setValue(player.getLocation().getY());
                        config.getNode(id, "finish2", "z").setValue(player.getLocation().getZ());
                        saveConfig(config);
                        sendMessage(player, "finish2");
                    }
                    break;
                default:
                    player.sendMessage(Text.of(TextColors.RED, "Not a valid option. Must be Corner/Start/Finish."));
                    break;
            }
            return CommandResult.success();
        }
        player.sendMessage(Text.of(TextColors.RED, "No arena with that ID exists!"));
        return CommandResult.success();
    }

    public void saveConfig(CommentedConfigurationNode node){
        try {
            RedLightGreenLight.getInstance().getConfigurationLoader().save(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Player player, String option){
        player.sendMessage(Text.of(TextColors.GREEN, "Set ", option, " successfully."));
    }

}
