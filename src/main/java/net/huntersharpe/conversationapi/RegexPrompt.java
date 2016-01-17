package net.huntersharpe.conversationapi;

import java.util.regex.Pattern;

/**
 * Created by intronate67 on 1/4/2016.
 */
public abstract class RegexPrompt extends ValidatingPrompt {

    private Pattern pattern;

    public RegexPrompt(String regex) {
        this(Pattern.compile(regex));
    }

    public RegexPrompt(Pattern pattern) {
        super();
        this.pattern = pattern;
    }

    private RegexPrompt() {}

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        return pattern.matcher(input).matches();
    }
}
