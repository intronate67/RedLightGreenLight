package net.huntersharpe.RlGl.commands.prompts;

import net.huntersharpe.conversationapi.ConversationContext;
import net.huntersharpe.conversationapi.FixedSetPrompt;
import net.huntersharpe.conversationapi.Prompt;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by intronate67 on 1/17/2016.
 */
public class FirstEndPrompt extends FixedSetPrompt{
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        Player player = (Player)context.getForWhom();
        ConfirmationPrompt.instance.finish1 = player.getLocation().getPosition();
        context.getForWhom().sendRawMessage(Text.of(TextColors.GREEN, "First end of finish line has been set."));
        return new SecondEndPrompt();
    }

    @Override
    public Text getPromptText(ConversationContext context) {
        return Text.of("Now, Go to one end your finish line. Once you are there, type set.");
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        if(input != "set") return false;
        return true;
    }
}
