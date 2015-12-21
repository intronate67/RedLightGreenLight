package net.huntersharpe.RlGl;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import net.huntersharpe.RlGl.commands.Join;
import net.huntersharpe.RlGl.commands.Leave;
import net.huntersharpe.RlGl.commands.RlGl;
import net.huntersharpe.RlGl.commands.admin.*;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;

import java.io.File;
import java.io.IOException;
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
    public File arenas;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    private Game game;

    public ConfigurationNode config = null;

    private static RedLightGreenLight instance = new RedLightGreenLight();

    public static RedLightGreenLight getInstance(){
        return instance;
    }

    @Subscribe
    public void onPreInit(GamePreInitializationEvent e){
        setupConfig();
    }

    @Subscribe
    public void onInit(GameInitializationEvent e){
        //Load commands here
        //Create Arena
        CommandSpec createCmd = CommandSpec.builder()
                .permission("rlgl.admin.create")
                .description(Texts.of("Create an arena."))
                .executor(new Create())
                .build();
        //Delete Arena
        CommandSpec deleteCmd = CommandSpec.builder()
                .permission("rlgl.admin.delete")
                .description(Texts.of("Delete an arena"))
                .executor(new Delete())
                .build();
        //Set start line command
        CommandSpec setStartCmd = CommandSpec.builder()
                .permission("rlgl.admin.start")
                .description(Texts.of("Set the start line of an arena."))
                .executor(new SetStart())
                .build();
        //Set end line command
        CommandSpec setEndCmd = CommandSpec.builder()
                .permission("rlgl.admin.end")
                .description(Texts.of("Set the finish line of an arena."))
                .executor(new SetEnd())
                .build();
        //Force Start Game Command
        CommandSpec startCmd = CommandSpec.builder()
                .permission("rlgl.admin.start")
                .description(Texts.of("Force start a race"))
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
        //Msin Command
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
                .build();
        game.getCommandManager().register(this, rlglCmd, "rlgl");
    }
    private void setupConfig() {
        try {
            if (!defaultConf.getParentFile().exists()) {
                defaultConf.getParentFile().mkdir();
            }
            if (!defaultConf.exists()) {
                defaultConf.createNewFile();
                config = configManager.load();

                //TODO: Add config values
                configManager.save(config);
            }
            if(!arenas.exists()){
                arenas.createNewFile();
            }
            config = configManager.load();
        } catch (IOException e) {
            logger.warning("Default Config could not be loaded/created!");
        }
    }


}
