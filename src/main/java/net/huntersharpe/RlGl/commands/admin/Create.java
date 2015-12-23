package net.huntersharpe.RlGl.commands.admin;

import net.huntersharpe.RlGl.RedLightGreenLight;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by Hunter Sharpe on 12/20/15.
 */
public class Create implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Texts.of(TextColors.RED, "Only players can run this command!"));
            return CommandResult.success();
        }
        Player p = (Player)src;
        String[] args = arguments.toString().split(" ");
        if(args.length != 2){
            sendHelp(p);
            return CommandResult.success();
        }
        if(!isNumeric(args[1])) {
            p.sendMessage(Texts.of(TextColors.RED, "ID name must be numeric."));
            return CommandResult.success();
        }
        int id = Integer.parseInt(args[1]);
        if(!arenaExists(id)){
            p.sendMessage(Texts.of("An arena with that ID already exists!"));
            return CommandResult.success();
        }
        p.sendMessage(Texts.of(TextColors.GRAY, "Creating Arena in arenas.conf..."));
        RedLightGreenLight.getInstance().config.getNode("arenas").setValue(id);
        p.sendMessage(Texts.of(TextColors.GREEN, "Created!"));
        return CommandResult.success();
    }
    public void sendHelp(CommandSource src){
        src.sendMessage(Texts.of(TextColors.RED, "Incorrect Usage! Use /rlgl help for more info."));
    }
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public boolean arenaExists(int id){
        if(RedLightGreenLight.getInstance().config.getNode("arenas").getChildrenList().contains(id)){
            return true;
        }
        return false;
    }

}