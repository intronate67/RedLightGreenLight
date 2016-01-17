package net.huntersharpe.RlGl.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by Hunter Sharpe on 12/21/15.
 */
public class Help implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
        if(!arguments.hasAny("command")){
            src.sendMessage(Text.of(TextColors.RED, "Correct usage: /rlgl help <command>"));
            return CommandResult.success();
        }
        switch(arguments.getOne("command").get().toString()){
            case "create":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Create",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl create <id> <name>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Create arena with specified ID."));
                src.sendMessage(Text.of(TextColors.GREEN, "- <id> = ID# of intended arena to create."));
                src.sendMessage(Text.of(TextColors.GREEN, "- <name> = Name specified for the arena."));
                break;
            case "delete":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Delete",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl <del|delete> <id>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Deletes specified arena."));
                src.sendMessage(Text.of(TextColors.GREEN, "- <id> = ID# of intended arena to delete."));
                break;
            case "set":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Set",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl set <id>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Sets corner positions for arenas"));
                src.sendMessage(Text.of(TextColors.GREEN, "- 1st corner set by 1st run of command. 2nd corner set by 2nd run."));
                src.sendMessage(Text.of(TextColors.GREEN, "- If any 3rd or 4th runs of the command are done then the positions is overridden"));
                break;
            case "setend":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Set End",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl setend <start|end>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Sets finish line of the race."));
                src.sendMessage(Text.of(TextColors.GREEN, "- 'Start' is one end of the line and 'End' is the other end"));
                break;
            case "setstart":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Set Start",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl setstart <start|end>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Sets starting line of the race."));
                src.sendMessage(Text.of(TextColors.GREEN, "- 'Start' is one end of the line and 'End' is the other end"));
                break;
            case "start":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Start",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl start <id>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Force starts a specified arena."));
                src.sendMessage(Text.of(TextColors.GREEN, "- ID = ID# of intended arena to start."));
                break;
            case "stop":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Stop",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl start <id>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Force ends a specified arena."));
                src.sendMessage(Text.of(TextColors.GREEN, "- ID = ID# of intended arena to end."));
                break;
            case "help":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Help",
                        TextColors.DARK_GRAY, "]----------"
                ));
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
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "Leave",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl leave"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Leave your current arena/race."));
                break;
            case "rlgl":
                src.sendMessage(Text.of(TextColors.DARK_GRAY,
                        "----------[",
                        TextColors.AQUA,
                        "RlGl",
                        TextColors.DARK_GRAY, "]----------"
                ));
                src.sendMessage(Text.of(TextColors.BLUE, "Usage: /rlgl <create|del|set|setend|setstart|start|stop|join|leave|help>"));
                src.sendMessage(Text.of(TextColors.GREEN, "- Basic Red Light Green Light command"));
                src.sendMessage(Text.of(TextColors.GREEN, "- 2nd argument is specified subcommand."));
                break;
            default:
                src.sendMessage(Text.of(TextColors.RED, arguments.getAll("command"), " is not a valid rlgl command."));
                break;
        }
        return CommandResult.success();
    }
}
