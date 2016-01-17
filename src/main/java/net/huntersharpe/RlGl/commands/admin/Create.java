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
public class Create implements CommandExecutor{

    public RedLightGreenLight plugin;

    public Create(RedLightGreenLight pl){
        this.plugin = pl;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Text.of(TextColors.RED, "Only players can run this command!"));
            return CommandResult.success();
        }
        Player p = (Player)src;
        //TODO: Start conversation below

        return CommandResult.success();
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

    public static boolean isString(String str){
        if (str == null){
            return false;
        }
        int sz = str.length();
        for(int i = 0; i < sz; i++){
            if(!Character.isAlphabetic(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public boolean arenaExists(int id){
        if(plugin.configurationNode.getNode("arenas").getChildrenList().contains(id)){
            return true;
        }
        return false;
    }

}