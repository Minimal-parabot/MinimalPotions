package org.parabot.minimal.minimalpotions;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Game;

import java.awt.event.KeyEvent;

public class Relog implements Strategy
{
    @Override
    public boolean activate()
    {
        return !Game.isLoggedIn();
    }

    @Override
    public void execute()
    {
        MinimalPotions.status = "Relogging";

        Keyboard.getInstance().clickKey(KeyEvent.VK_ENTER);

        Time.sleep(new SleepCondition()
        {
            @Override
            public boolean isValid()
            {
                return Game.isLoggedIn();
            }
        }, 5000);

        if (Game.isLoggedIn())
        {
            MinimalPotions.status += "..";

            Time.sleep(5000);
        }
    }
}