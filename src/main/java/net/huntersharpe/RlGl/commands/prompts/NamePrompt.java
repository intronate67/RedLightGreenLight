package net.huntersharpe.RlGl.commands.prompts;

import net.huntersharpe.RlGl.RedLightGreenLight;
import net.huntersharpe.conversationapi.ConversationContext;
import net.huntersharpe.conversationapi.Prompt;
import net.huntersharpe.conversationapi.StringPrompt;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.UUID;

/**
 * Created by intronate67 on 1/17/2016.
 */
public class NamePrompt extends StringPrompt{
    @Override
    public Text getPromptText(ConversationContext context) {
        return Text.of("Enter Arena name");
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if(RedLightGreenLight.getInstance().rootNode().getNode("rlgl", "arenas").getChildrenList().contains(input)){
            return new NamePrompt();
        }
        UUID id = UUID.randomUUID();
        ConfirmationPrompt.instance.name = input;
        ConfirmationPrompt.instance.id = id;
        context.getForWhom().sendRawMessage(Text.of(TextColors.GREEN, "Arena name set to: " + input));
        context.getForWhom().sendRawMessage(Text.of(TextColors.GREEN, "Arena unique id set to:", id));
        return new FirstCornerPrompt();
    }
}
