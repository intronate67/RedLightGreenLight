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

/**
 * Created by Hunter Sharpe on 12/20/15.
 */
public class Delete implements CommandExecutor {

    private RedLightGreenLight plugin = new RedLightGreenLight();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Text.of(TextColors.RED, "Only players can run this command!"));
            return CommandResult.success();
        }
        if(!RedLightGreenLight.getInstance().rootNode().getChildrenList().contains(args.getOne("name").get())){
            src.sendMessage(Text.of(TextColors.RED, "Arena doesn't exist in config."));
            return CommandResult.success();
        }
        Player p = (Player)src;
        return CommandResult.success();
    }
}