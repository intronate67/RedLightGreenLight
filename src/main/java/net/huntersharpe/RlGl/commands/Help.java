package net.huntersharpe.RlGl.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;

/**
 * Created by Hunter Sharpe on 12/21/15.
 */
public class Help implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
        String[] args = arguments.toString().split(" ");
        if(args.length != 1){
            src.sendMessage(Texts.of(TextColors.RED, "Correct usage: /rlgl help <command>"));
            return CommandResult.success();
        }
        String[] commands = {"create",
                "del",
                "delete",
                "set",
                "setend",
                "setstart",
                "start",
                "end",
                "stop",
                "help",
                "join",
                "leave",
                "quit",
                "rlgl"
        };
        if(!Arrays.asList(commands).contains(args[0])){
            src.sendMessage(Texts.of(TextColors.RED, args[0] + " is not a valid command."));
            return CommandResult.success();
        }
        switch(args[0]){
            case "delete":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl <del|delete> <id>"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Deletes specified arena."));
                src.sendMessage(Texts.of(TextColors.GREEN, "<id> = ID# of intended arena to delete."));
                break;
            case "set":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl set <id>"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Sets corner positions for arenas"));
                src.sendMessage(Texts.of(TextColors.GREEN, "1st corner set by 1st run of command. 2nd corner set by 2nd run."));
                src.sendMessage(Texts.of(TextColors.GREEN, "If any 3rd or 4th runs of the command are done then the positions is overridden"));
                break;
            case "setend":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl setend <start|end>"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Sets finish line of the race."));
                src.sendMessage(Texts.of(TextColors.GREEN, "'Start' is one end of the line and 'End' is the other end"));
                break;
            case "setstart":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl setstart <start|end>"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Sets starting line of the race."));
                src.sendMessage(Texts.of(TextColors.GREEN, "'Start' is one end of the line and 'End' is the other end"));
                break;
            case "start":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl start <id>"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Force starts a specified arena."));
                src.sendMessage(Texts.of(TextColors.GREEN, "ID = ID# of intended arena to start."));
                break;
            case "stop":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl start <id>"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Force ends a specified arena."));
                src.sendMessage(Texts.of(TextColors.GREEN, "ID = ID# of intended arena to end."));
                break;
            case "help":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl help <command>"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Lists this help menu."));
                src.sendMessage(Texts.of(TextColors.GREEN, "<command> = intended command to retrieve help for."));
                break;
            case "join":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl join"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Joins the most full race."));
                break;
            case "leave":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl leave"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Leave your current arena/race."));
                break;
            case "rlgl":
                src.sendMessage(Texts.of(TextColors.GREEN, "Usage: /rlgl <create|del|set|setend|setstart|start|stop|join|leave|help>"));
                src.sendMessage(Texts.of(TextColors.GREEN, "Basic Red Light Green Light command"));
                src.sendMessage(Texts.of(TextColors.GREEN, "2nd argument is specified subcommand."));
                break;
        }
        return CommandResult.success();
    }
}
