package net.huntersharpe.RlGl;

import com.google.inject.Inject;
import net.huntersharpe.RlGl.commands.Help;
import net.huntersharpe.RlGl.commands.Join;
import net.huntersharpe.RlGl.commands.Leave;
import net.huntersharpe.RlGl.commands.RlGl;
import net.huntersharpe.RlGl.commands.admin.*;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Hunter Sharpe on 12/20/15.
 */
@Plugin(id="redlightgreenlight", name="RedLightGreenLight", version="")
public class RedLightGreenLight {

    @Inject
    Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;

    @Inject
    @DefaultConfig(sharedRoot = false)
    public File defaultConf;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    private Game game = Sponge.getGame();

    public ConfigurationNode config = null;

    private static RedLightGreenLight instance = new RedLightGreenLight();

    public static RedLightGreenLight getInstance(){
        return instance;
    }



    @Listener
    public void onPreInit(GamePreInitializationEvent e){
        setupConfig();
    }

    @Listener
    public void onInit(GameInitializationEvent e){
        registerCommands();
    }
    private void setupConfig() {
        try {
            if (!defaultConf.getParentFile().exists()) {
                defaultConf.getParentFile().mkdir();
            }
            if (!defaultConf.exists()) {
                defaultConf.createNewFile();

                //TODO: Add config values
                configManager.save(config);
            }
            config = configManager.load();
        } catch (IOException e) {
            logger.warning("Default Config could not be loaded/created!");
        }
    }


    public void registerCommands(){
        //Create Arena
        CommandSpec createCmd = CommandSpec.builder()
                .permission("rlgl.admin.create")
                .description(Texts.of("Create an arena."))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("id"))))
                .executor(new Create())
                .build();
        //Delete Arena
        CommandSpec deleteCmd = CommandSpec.builder()
                .permission("rlgl.admin.delete")
                .description(Texts.of("Delete an arena"))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("id"))))
                .executor(new Delete())
                .build();
        //Set start line command
        Map<String, String> pos = new HashMap<>();
        pos.put("start", "end");
        CommandSpec setStartCmd = CommandSpec.builder()
                .permission("rlgl.admin.start")
                .description(Texts.of("Set the start line of an arena."))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("id"))), GenericArguments.choices(Texts.of("pos"), pos))
                .executor(new SetStart())
                .build();
        //Set end line command
        CommandSpec setEndCmd = CommandSpec.builder()
                .permission("rlgl.admin.end")
                .description(Texts.of("Set the finish line of an arena."))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("id"))), GenericArguments.choices(Texts.of("pos"), pos))
                .executor(new SetEnd())
                .build();
        //Force Start Game Command
        CommandSpec startCmd = CommandSpec.builder()
                .permission("rlgl.admin.start")
                .description(Texts.of("Force start a race"))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("id"))))
                .executor(new Start())
                .build();
        //Force end game command
        CommandSpec endCmd = CommandSpec.builder()
                .permission("rlgl.admin.end")
                .description(Texts.of("Force end a race"))
                .executor(new Stop())
                .build();
        //Join race command
        CommandSpec joinCmd = CommandSpec.builder()
                .permission("rlgl.join")
                .description(Texts.of("Join a race"))
                .executor(new Join())
                .build();
        //Leave race command
        CommandSpec leaveCmd = CommandSpec.builder()
                .permission("rlgl.leave")
                .description(Texts.of("Leave a race"))
                .executor(new Leave())
                .build();
        CommandSpec helpCmd = CommandSpec.builder()
                .description(Texts.of("RlGl Help command"))
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("command"))))
                .executor(new Help())
                .build();
        //Main Command
        CommandSpec rlglCmd = CommandSpec.builder()
                .permission("rlgl.use")
                .description(Texts.of("Red Light Green Light command."))
                .executor(new RlGl())
                .child(createCmd, "create", "add")
                .child(deleteCmd, "del", "del")
                .child(setEndCmd, "setEnd")
                .child(setStartCmd, "setStart")
                .child(startCmd, "start")
                .child(endCmd, "end", "stop")
                .child(joinCmd, "join")
                .child(leaveCmd, "leave", "quit")
                .child(helpCmd, "help")
                .build();
        game.getCommandManager().register(this, rlglCmd, "rlgl");
    }

}
