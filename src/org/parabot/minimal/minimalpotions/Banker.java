package org.parabot.minimal.minimalpotions;

import org.parabot.core.Context;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.SceneObject;

public class Banker implements Strategy
{
    private final int primaryId;
    private final int secondaryId;

    public Banker(Potion potion)
    {
        this.primaryId = potion.getPrimaryId();
        this.secondaryId = potion.getSecondaryId();
    }

    private static final int BANK_BOOTH_ID = 2213;

    @Override
    public boolean activate()
    {
        return SceneObjects.getNearest(BANK_BOOTH_ID).length > 0
                && !Inventory.contains(primaryId, secondaryId);
    }
    
    @Override
    public void execute()
    {
        if (!Bank.isOpen())
        {
            SceneObject bankBooth = SceneObjects.getClosest(BANK_BOOTH_ID);

            if (bankBooth != null)
            {
                Logger.addMessage("Opening bank booth", true);

                bankBooth.interact(SceneObjects.Option.OPEN);

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
                Logger.addMessage("Depositing inventory", true);

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
                
                if (Bank.getItem(primaryId) != null)
                    primary = Bank.getItem(primaryId);
                
                if (Bank.getItem(secondaryId) != null)
                    secondary = Bank.getItem(secondaryId);
                
                if (primary != null && secondary != null)
                {
                    Logger.addMessage("Withdrawing recipe items");

                    Menu.sendAction(776, primary.getId() - 1, primary.getSlot(), 5382);

                    Time.sleep(250);
                    
                    Menu.sendAction(776, secondary.getId() - 1, secondary.getSlot(), 5382);

                    Time.sleep(250);
                }
                else
                {
                    Logger.addMessage("Stopping the script - there are no more supplies", true);

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
                        Logger.addMessage("Ask Minimal to update the hooks", true);

                        System.exit(0);
                    }
                }
            }
        }
    }
}