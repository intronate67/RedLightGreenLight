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
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;

public class Create implements CommandExecutor{

    public RedLightGreenLight plugin = new RedLightGreenLight();
    //Move to conversation style of arena building.
    public Create(RedLightGreenLight pl){
        this.plugin = pl;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Text.of(TextColors.RED, "Only players can run this command!"));
            return CommandResult.success();
        }
        Player player = (Player) src;
        if(!arenaExists(args.<String>getOne("id").get())){
            String id = args.<String>getOne("id").get();
            plugin.rootNode().getNode(id, "name").setValue(id);
            try {
                plugin.getConfigurationLoader().save(plugin.rootNode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage(Text.of(TextColors.GREEN, "Arena created, for it to work please set boundaries with /rlgl set"));
            return CommandResult.success();
        }
        player.sendMessage(Text.of(TextColors.RED, "An arena with that id already exists."));
        return CommandResult.success();
    }

    //Remove double negative
    public boolean arenaExists(String id){
        if(!plugin.rootNode().getNode("arenas").getNode(id).isVirtual()){
            return true;
        }
        return false;
    }

}