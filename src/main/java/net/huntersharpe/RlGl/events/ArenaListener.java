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
package net.huntersharpe.RlGl.events;

import com.google.common.eventbus.Subscribe;
import net.caseif.flint.challenger.Challenger;
import net.caseif.flint.event.round.RoundChangeLifecycleStageEvent;
import net.caseif.flint.event.round.challenger.ChallengerJoinRoundEvent;
import net.caseif.flint.event.round.challenger.ChallengerLeaveRoundEvent;

public class ArenaListener {

    @Subscribe
    public void onArenaJoin(ChallengerJoinRoundEvent event){
        if(event.getRound().getLifecycleStage().getId().equalsIgnoreCase("countdown")
                || event.getRound().getLifecycleStage().getId().equals("intro")){
            PlayerListener.getInstance().getFrozenMap().add(event.getChallenger().getUniqueId());
        }
    }

    @Subscribe
    public void onRoundChange(RoundChangeLifecycleStageEvent event){
        if(event.getStageBefore().getId().equalsIgnoreCase("countdown")){
            for(int i = 0; i<event.getRound().getChallengers().size(); i++){
                Challenger challenger = event.getRound().getChallengers().get(i);
                PlayerListener.getInstance().getFrozenMap().remove(challenger.getUniqueId());
            }
            //Handle arena stuff here.
        }
    }

    @Subscribe
    public void onLeave(ChallengerLeaveRoundEvent event){
        PlayerListener.getInstance().getFrozenMap().remove(event.getChallenger().getUniqueId());
    }

}
