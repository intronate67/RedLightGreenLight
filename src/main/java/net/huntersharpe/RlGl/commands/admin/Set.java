package net.huntersharpe.RlGl.commands.admin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by Hunter Sharpe on 12/21/15.
 */
public class Set implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Texts.of(TextColors.RED, "Only players can run this command!"));
        }
        String[] args = arguments.toString().split(" ");
        if(args.length != 1){
            sendHelp(src);
            return CommandResult.success();
        }

        return CommandResult.success();
    }
    public void sendHelp(CommandSource src){
        src.sendMessage(Texts.of(TextColors.RED, "Incorrect Usage! Use /rlgl help for more info."));
    }
    public boolean arenaExists(int id){
        return false;
    }
}
