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
package net.huntersharpe.RlGl.commands;

import net.caseif.flint.arena.Arena;
import net.caseif.flint.minigame.Minigame;
import net.huntersharpe.RlGl.RedLightGreenLight;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Join implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Text.of("Only players can join races."));
            return CommandResult.success();
        }
        Player player = (Player)src;
        Minigame minigame = RedLightGreenLight.getInstance().getMinigame();
        if(minigame.getArena(args.<String>getOne("id").get()).isPresent()){
            Arena arena = minigame.getArena(args.<String>getOne("id").get()).get();
            if(!arena.getRound().get().getLifecycleStage().getId().equalsIgnoreCase("inGame")
                    || arena.getRound().get().getLifecycleStage().getId().equalsIgnoreCase("endgame")
                    || arena.getRound().get().getLifecycleStage().getId().equalsIgnoreCase("countdown")){
                if(RedLightGreenLight.getInstance().rootNode().getNode(args.<String>getOne("id").get(), "maxSize")
                        .getInt() <= arena.getRound().get().getChallengers().size()){
                    arena.getRound().get().addChallenger(player.getUniqueId());
                    player.sendMessage(Text.of(TextColors.GREEN, "Successfully joined arena."));
                    return CommandResult.success();
                }
                player.sendMessage(Text.of(TextColors.RED, "Arena is full."));
                return CommandResult.success();
            }
            player.sendMessage(Text.of(TextColors.RED, "That arena is ingame, ending, or counting down!"));
            return CommandResult.success();
        }
        player.sendMessage(Text.of(TextColors.RED, "No arena by that ID exists!"));
        return CommandResult.success();
    }
}