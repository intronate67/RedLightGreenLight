package net.huntersharpe.conversationapi;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;

/**
 * Created by intronate67 on 1/4/2016.
 */
public abstract class BooleanPrompt extends ValidatingPrompt{

    public BooleanPrompt() {
        super();
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        String[] accepted = {"true", "false", "on", "off", "yes", "no" /* Sponge: */, "y", "n", "1", "0", "right", "wrong", "correct", "incorrect", "valid", "invalid"}; // Spigot
        return ArrayUtils.contains(accepted, input.toLowerCase());
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        if (input.equalsIgnoreCase("y") || input.equals("1") || input.equalsIgnoreCase("right") || input.equalsIgnoreCase("correct") || input.equalsIgnoreCase("valid")) input = "true"; // Spigot
        return acceptValidatedInput(context, BooleanUtils.toBoolean(input));
    }

    /**
     * Override this method to perform some action with the user's boolean
     * response.
     *
     * @param context Context information about the conversation.
     * @param input The user's boolean response.
     * @return The next {@link Prompt} in the prompt graph.
     */
    protected abstract Prompt acceptValidatedInput(ConversationContext context, boolean input);
}
