/*This file is part of RedLightGreenLight, licensed under the MIT License (MIT).
*
* Copyright (c) 2016 Hunter Sharpe
* Copyright (c) contributors

* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
package net.huntersharpe.RlGl;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import net.caseif.flint.FlintCore;
import net.caseif.flint.config.ConfigNode;
import net.caseif.flint.minigame.Minigame;
import net.caseif.flint.round.LifecycleStage;
import net.huntersharpe.RlGl.commands.Help;
import net.huntersharpe.RlGl.commands.Join;
import net.huntersharpe.RlGl.commands.Leave;
import net.huntersharpe.RlGl.commands.RlGl;
import net.huntersharpe.RlGl.commands.admin.*;
import net.huntersharpe.RlGl.events.ArenaListener;
import net.huntersharpe.RlGl.events.PlayerListener;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.File;
import java.io.IOException;

@Plugin(id="redlightgreenlight", name="RedLightGreenLight", version="0.0.1")
public class RedLightGreenLight {

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File configuration = null;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader = null;

    private CommentedConfigurationNode configurationNode = null;

    @Inject
    private static Game game = Sponge.getGame();

    private static RedLightGreenLight instance;

    public static RedLightGreenLight getInstance(){
        return instance;
    }

    private Minigame minigame = FlintCore.registerPlugin("redlightgreenlight");

    @Listener
    public void onPreInit(GamePreInitializationEvent event){
        instance = this;
        try {
            checkConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(FlintCore.getApiRevision() < 5){
            throw new RuntimeException("Flint API version is out of date!");
        }else if (FlintCore.getMajorVersion() != 1){
            throw new RuntimeException("Incompatible Flint!");
        }
        registerCommands();
        setupMinigame();
        minigame.getEventBus().register(new ArenaListener());
        game.getEventManager().registerListeners(this, new PlayerListener());
        //
    }

    private void checkConfig() throws IOException{
        if(!configuration.exists()){
            configuration.createNewFile();
            configurationNode = configurationLoader.load();
            configurationNode.getNode("rlgl", "arenas").setComment("Do not edit these values, All information stored about arenas is saved below.");
            configurationNode.getNode("introTime").setValue(30);
            configurationNode.getNode("countdownTime").setValue(10);
            configurationNode.getNode("inGameTime").setValue(180);
            configurationNode.getNode("endgame").setValue(10);
            configurationLoader.save(configurationNode);
        }
        configurationNode = configurationLoader.load();
    }

    private void setupMinigame(){
        int introTime = configurationNode.getNode("introTime").getInt();
        int countdownTime = configurationNode.getNode("countdownTime").getInt();
        int inGameTime = configurationNode.getNode("inGameTime").getInt();
        int endgameTime = configurationNode.getNode("endgame").getInt();
        final LifecycleStage intro = new LifecycleStage("intro", introTime);
        final LifecycleStage countdown = new LifecycleStage("countdown", countdownTime);
        final LifecycleStage inGame = new LifecycleStage("ingame", inGameTime);
        final LifecycleStage endGame = new LifecycleStage("endgame", endgameTime);
        ImmutableSet<LifecycleStage> stages = ImmutableSet.<LifecycleStage>builder()
                .add(intro)
                .add(countdown)
                .add(inGame)
                .add(endGame)
                .build();
        minigame.setConfigValue(ConfigNode.DEFAULT_LIFECYCLE_STAGES, stages);
    }

    public void registerCommands(){
        //Create Arena
        CommandSpec createCmd = CommandSpec.builder()
                .permission("rlgl.admin.create")
                .description(Text.of("Create an arena."))
                .arguments(
                        GenericArguments.string(Text.of("id")),
                        GenericArguments.integer(Text.of("maxPlayers"))
                )
                .executor(new Create(this))
                .build();
        CommandSpec setCmd = CommandSpec.builder()
                .permission("rlgl.admin.set")
                .description(Text.of("Set corners/start line/finish line of an arena."))
                .arguments(
                        GenericArguments.string(Text.of("id")),
                        GenericArguments.string(Text.of("type")),
                        GenericArguments.integer(Text.of("position"))
                )
                .executor(new Set())
                .build();
        CommandSpec buildCmd = CommandSpec.builder()
                .permission("rlgl.admin.build")
                .description(Text.of("Last step in creating an arena."))
                .arguments(GenericArguments.string(Text.of("id")))
                .executor(new Build())
                .build();
        //Delete Arena
        CommandSpec deleteCmd = CommandSpec.builder()
                .permission("rlgl.admin.delete")
                .description(Text.of("Delete an arena"))
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))))
                .executor(new Delete())
                .build();
        //Force Start Game Command
        CommandSpec startCmd = CommandSpec.builder()
                .permission("rlgl.admin.start")
                .description(Text.of("Force start a race"))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("id"))))
                .executor(new Start())
                .build();
        //Force end game command
        CommandSpec endCmd = CommandSpec.builder()
                .permission("rlgl.admin.end")
                .description(Text.of("Force end a race"))
                .executor(new Stop())
                .build();
        //Join race command
        CommandSpec joinCmd = CommandSpec.builder()
                .permission("rlgl.join")
                .description(Text.of("Join a race"))
                .arguments(GenericArguments.string(Text.of("id")))
                .executor(new Join())
                .build();
        //Leave race command
        CommandSpec leaveCmd = CommandSpec.builder()
                .permission("rlgl.leave")
                .description(Text.of("Leave a race"))
                .executor(new Leave())
                .build();
        CommandSpec helpCmd = CommandSpec.builder()
                .description(Text.of("RlGl Help command"))
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("command"))))
                .executor(new Help())
                .build();
        //Main Command
        //Switch over to .children() soon.
        CommandSpec rlglCmd = CommandSpec.builder()
                .permission("rlgl.use")
                .description(Text.of("Red Light Green Light command."))
                .executor(new RlGl())
                .child(createCmd, "create", "add")
                .child(deleteCmd, "del", "del")
                .child(startCmd, "start")
                .child(endCmd, "end", "stop")
                .child(joinCmd, "join")
                .child(leaveCmd, "leave", "quit")
                .child(helpCmd, "help")
                .child(setCmd, "set")
                .child(buildCmd, "build")
                .build();
        game.getCommandManager().register(this, rlglCmd, "rlgl");
    }

    public CommentedConfigurationNode rootNode(){
        return configurationNode;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getConfigurationLoader(){
        return configurationLoader;
    }

    public Minigame getMinigame(){
        return minigame;
    }
}
