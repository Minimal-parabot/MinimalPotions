package org.parabot.minimal.minimalpotions;

import org.parabot.core.Context;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.SceneObject;

public class Banker implements Strategy
{
    private final int PRIMARY_ID;
    private final int SECONDARY_ID;

    public Banker(final Potion POTION)
    {
        this.PRIMARY_ID = POTION.getPrimaryId();
        this.SECONDARY_ID = POTION.getSecondaryId();
    }

    private final int BANK_BOOTH_ID = 2213;

    @Override
    public boolean activate()
    {
        return SceneObjects.getNearest(BANK_BOOTH_ID).length > 0
                && !Inventory.contains(PRIMARY_ID, SECONDARY_ID);
    }
    
    @Override
    public void execute()
    {
        if (!Bank.isOpen())
        {
            SceneObject bankBooth = SceneObjects.getClosest(BANK_BOOTH_ID);

            if (bankBooth != null)
            {
                bankBooth.interact(0);

                Time.sleep(new SleepCondition()
                {
                    @Override
                    public boolean isValid()
                    {
                        return Bank.isOpen();
                    }
                }, 2000);
            }
        }
        
        if (Bank.isOpen())
        {
            if (!Inventory.isEmpty())
            {
                Menu.clickButton(21012);

                Time.sleep(new SleepCondition()
                {
                    @Override
                    public boolean isValid()
                    {
                        return Inventory.isEmpty();
                    }
                }, 2000);
            }
            
            if (Inventory.isEmpty())
            {
                Item primary = null;
                Item secondary = null;
                
                if (Bank.getItem(PRIMARY_ID) != null)
                    primary = Bank.getItem(PRIMARY_ID);
                
                if (Bank.getItem(SECONDARY_ID) != null)
                    secondary = Bank.getItem(SECONDARY_ID);
                
                if (primary != null && secondary != null)
                {
                    Menu.sendAction(776, primary.getId() - 1, primary.getSlot(), 5382);

                    Time.sleep(250);
                    
                    Menu.sendAction(776, secondary.getId() - 1, secondary.getSlot(), 5382);

                    Time.sleep(250);
                }
                else
                {
                    MinimalPotions.forceLogout();

                    Time.sleep(new SleepCondition()
                    {
                        @Override
                        public boolean isValid()
                        {
                            return !Game.isLoggedIn();
                        }
                    }, 2000);

                    if (!Game.isLoggedIn())
                    {
                        Context.getInstance().getRunningScript().setState(Script.STATE_STOPPED);
                    }
                    else
                    {
                        System.out.println("Ask Minimal to update the hooks");

                        System.exit(0);
                    }
                }
            }
        }
    }
}