package net.huntersharpe.RlGl.commands.admin;

import net.huntersharpe.RlGl.RedLightGreenLight;
import net.huntersharpe.RlGl.commands.prompts.DeletePrompt;
import net.huntersharpe.conversationapi.Conversable;
import net.huntersharpe.conversationapi.Conversation;
import net.huntersharpe.conversationapi.ConversationFactory;
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

    private RedLightGreenLight plugin;

    private ConversationFactory factory = new ConversationFactory(plugin.getGame());

    private Conversation conv;

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
        DeletePrompt.getInstance().name = args.getOne("name").get().toString();
        conv = factory.withFirstPrompt(new DeletePrompt()).withLocalEcho(false).buildConversation((Conversable) p);
        conv.begin();
        return CommandResult.success();
    }
}