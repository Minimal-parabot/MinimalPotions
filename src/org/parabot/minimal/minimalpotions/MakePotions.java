package org.parabot.minimal.minimalpotions;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.wrappers.Item;

public class MakePotions implements Strategy
{
    private final int primaryId;
    private final int secondaryId;

    public MakePotions(Potion potion)
    {
        this.primaryId = potion.getPrimaryId();
        this.secondaryId = potion.getSecondaryId();
    }

    @Override
    public boolean activate()
    {
        return Inventory.contains(primaryId) && Inventory.contains(secondaryId);
    }

    @Override
    public void execute()
    {
        if (Bank.isOpen())
        {
            Logger.addMessage("Closing the bank", true);

            Menu.sendAction(200, -1, -1, 5384);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return !Bank.isOpen();
                }
            }, 1000);
        }

        if (!Bank.isOpen())
        {
            Logger.addMessage("Making potions", true);

            Item[] primaries = Inventory.getItems(primaryId);
            Item[] secondaries = Inventory.getItems(secondaryId);

            // I don't like using Inventory.combine() for this
            for (int i = 0; i < secondaries.length; i++)
            {
                Menu.sendAction(447, primaries[i].getId() - 1, primaries[i].getSlot(), 3214);

                Time.sleep(50, 75);

                Menu.sendAction(870, secondaries[i].getId() - 1, secondaries[i].getSlot(), 3214);

                Time.sleep(50, 75);
            }

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return !(Inventory.contains(primaryId, secondaryId));
                }
            }, 500);
        }
    }
}