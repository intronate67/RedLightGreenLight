package net.huntersharpe.RlGl.commands.prompts;

import net.huntersharpe.conversationapi.ConversationContext;
import net.huntersharpe.conversationapi.NumericPrompt;
import net.huntersharpe.conversationapi.Prompt;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by intronate67 on 1/17/2016.
 */
public class MaxSizePrompt extends NumericPrompt{
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        ConfirmationPrompt.instance.maxSize = input.intValue();
        context.getForWhom().sendRawMessage(Text.of(TextColors.GREEN, "Maximum players set to: ", input));
        return new ConfirmationPrompt();
    }

    @Override
    public Text getPromptText(ConversationContext context) {
        return Text.of("Enter maximum allowed players in arena. 4-32");
    }

    @Override
    protected boolean isNumberValid(ConversationContext context, Number input) {
        int x = input.intValue();
        if(!(x >= 4 && x <= 32)) return false;
        return true;
    }
}
