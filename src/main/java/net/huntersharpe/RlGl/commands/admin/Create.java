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

    public RedLightGreenLight plugin;

    public Create(RedLightGreenLight pl){
        this.plugin = pl;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Texts.of(TextColors.RED, "Only players can run this command!"));
            return CommandResult.success();
        }
        Player p = (Player)src;
        if(!isNumeric(arguments.getOne("id").get().toString())) {
            p.sendMessage(Texts.of(TextColors.RED, "ID name must be numeric."));
            p.sendMessage(Texts.of(arguments.getOne("id").get().toString()));
            return CommandResult.success();
        }
        int id = Integer.parseInt(arguments.getOne("id").get().toString());
        if(arenaExists(id)){
            p.sendMessage(Texts.of("An arena with that ID already exists!"));
            return CommandResult.success();
        }
        if(!isString(arguments.getOne("name").get().toString())){
            p.sendMessage(Texts.of(TextColors.RED, arguments.getOne("name").get(), " is not a valid name. Name must be a string(s)."));
            return CommandResult.success();
        }
        String name = arguments.getOne("name").get().toString();
        p.sendMessage(Texts.of(TextColors.GRAY, "Creating Arena in config..."));
        plugin.configurationNode.getNode("rlgl","arenas", id).setValue(name);
        p.sendMessage(Texts.of(TextColors.GREEN, "Created!"));
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
        if(!plugin.configurationNode.getNode("arenas").getChildrenList().contains(id)){
            return false;
        }
        return true;
    }

}