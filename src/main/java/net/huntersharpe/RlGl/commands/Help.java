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

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Help implements CommandExecutor {

    private Text prefix = Text.of(TextColors.DARK_GRAY,
            "----------[",
            TextColors.AQUA,
            "RlGl",
            TextColors.DARK_GRAY, "]----------"
    );

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!args.hasAny("command")){
            src.sendMessage(Text.of(TextColors.RED, "Correct usage: /rlgl help <command>"));
            return CommandResult.success();
        }
        switch(args.getOne("command").get().toString()){
            case "create":
                src.sendMessage(prefix);
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl create"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Create arena with specified ID."));
                src.sendMessage(Text.of(TextColors.GREEN, "- Starts conversation to create arena."));
                break;
            case "delete":
                src.sendMessage(prefix);
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl <del|delete> <id>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Deletes specified arena."));
                src.sendMessage(Text.of(TextColors.GREEN, "- <id> = ID# of intended arena to delete."));
                break;
            case "start":
                src.sendMessage(prefix);
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl start <id>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Force starts a specified arena."));
                src.sendMessage(Text.of(TextColors.GREEN, "- ID = ID# of intended arena to start."));
                break;
            case "stop":
                src.sendMessage(prefix);
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl start <id>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Force ends a specified arena."));
                src.sendMessage(Text.of(TextColors.GREEN, "- ID = ID# of intended arena to end."));
                break;
            case "help":
                src.sendMessage(prefix);
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl help <command>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Lists this help menu."));
                src.sendMessage(Text.of(TextColors.GREEN, "- <command> = intended command to retrieve help for."));
                break;
            case "join":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Join",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl join"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Joins the most full race."));
                break;
            case "leave":
                src.sendMessage(prefix);
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl leave"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Leave your current arena/race."));
                break;
            case "rlgl":
                src.sendMessage(prefix);
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl <create|del|set|setend|setstart|start|stop|join|leave|help>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Basic Red Light Green Light command"));
                src.sendMessage(Text.of(TextColors.GREEN, "- 2nd argument is specified subcommand."));
                break;
            default:
                src.sendMessage(Text.of(TextColors.RED, args.getAll("command"), " is not a valid rlgl command."));
                break;
        }
        return CommandResult.success();
    }
}
