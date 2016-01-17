package net.huntersharpe.RlGl.commands.prompts;

import com.flowpowered.math.vector.Vector3d;
import net.huntersharpe.RlGl.arena.ArenaManager;
import net.huntersharpe.conversationapi.BooleanPrompt;
import net.huntersharpe.conversationapi.ConversationContext;
import net.huntersharpe.conversationapi.Prompt;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by intronate67 on 1/17/2016.
 */
public class ConfirmationPrompt extends BooleanPrompt{

    public String name;
    public UUID id;
    public Vector3d corner1;
    public Vector3d corner2;
    public Vector3d start1;
    public Vector3d start2;
    public Vector3d finish1;
    public Vector3d finish2;
    public int maxSize;
    public final List<UUID> players = new ArrayList<UUID>();

    public static ConfirmationPrompt instance = new ConfirmationPrompt();

    public ConfirmationPrompt getInstance(){
        return instance;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, boolean input) {
        try{
            ArenaManager.getArenaManager().createArena(name, id, corner1, corner2, start1, start2, finish1, finish2, maxSize);
        }catch (Exception e){
            context.getForWhom().sendRawMessage(Text.of(TextColors.RED, "Error in creating your arena in config. Check console."));
            e.printStackTrace();
        }
        context.getForWhom().sendRawMessage(Text.of(TextColors.GREEN, "Arena: ", name, " was created and saved in config."));
        return null;
    }

    @Override
    public Text getPromptText(ConversationContext context) {
        return information;
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        if(input != "confirm"
                || input != "cancel") return false;
        return true;
    }

    private Text information = Text.of(TextColors.GRAY,
            "--------",
            TextColors.BLUE,
            "Arena Information",
            TextColors.GRAY,
            "--------\n",
            TextColors.YELLOW,
            "Name: ",
            TextColors.GREEN,
            name,
            TextColors.YELLOW,
            "id: ",
            TextColors.GREEN,
            id,
            TextColors.YELLOW,
            "First Corner: ",
            TextColors.GREEN,
            corner1,
            TextColors.YELLOW,
            "Second Corner: ",
            TextColors.GREEN,
            corner2,
            TextColors.YELLOW,
            "Start1: ",
            TextColors.GREEN,
            start1,
            TextColors.YELLOW,
            "Start2: ",
            TextColors.GREEN,
            start2,
            TextColors.YELLOW,
            "Finish1: ",
            TextColors.GREEN,
            finish1,
            TextColors.YELLOW,
            "Finish2: ",
            TextColors.GREEN,
            finish2,
            TextColors.YELLOW,
            "MaxSize: ",
            TextColors.GREEN,
            maxSize);
}
