package net.huntersharpe.RlGl.commands.prompts;

import net.huntersharpe.RlGl.RedLightGreenLight;
import net.huntersharpe.RlGl.commands.admin.Delete;
import net.huntersharpe.conversationapi.ConversationContext;
import net.huntersharpe.conversationapi.Prompt;
import net.huntersharpe.conversationapi.ValidatingPrompt;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by intronate67 on 1/17/2016.
 */
public class DeletePrompt extends ValidatingPrompt{

    private static DeletePrompt instance = new DeletePrompt();

    public static DeletePrompt getInstance(){
        return instance;
    }

    public String name;

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        if(input == "confirm"){
            RedLightGreenLight.getInstance().rootNode().getNode("rlgl", "arenas").removeChild(name);
            context.getForWhom().sendRawMessage(Text.of(TextColors.GREEN, "Deleted Arena from config."));
        }else {
            context.getForWhom().sendRawMessage(Text.of(TextColors.RED, "Cancelled deletion..."));
        }
        return null;
    }

    @Override
    public Text getPromptText(ConversationContext context) {
        return Text.of(TextColors.RED, "Are you sure you want to delete this arena?");
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        if(input != "confirm" || input != "cancel"){
            return false;
        }
        return true;
    }

}
