package net.huntersharpe.RlGl;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

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
            config = configManager.load();
        } catch (IOException e) {
            logger.warning("Default Config could not be loaded/created!");
        }
    }


}
