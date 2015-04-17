package org.parabot.minimal.minimalpotions;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.wrappers.Item;

public class MakePotions implements Strategy
{
    private final int PRIMARY_ID;
    private final int SECONDARY_ID;

    public MakePotions(final Potion POTION)
    {
        this.PRIMARY_ID = POTION.getPrimaryId();
        this.SECONDARY_ID = POTION.getSecondaryId();
    }

    @Override
    public boolean activate()
    {
        return Inventory.contains(PRIMARY_ID) && Inventory.contains(SECONDARY_ID);
    }

    @Override
    public void execute()
    {
        if (Bank.isOpen())
        {
            // Closes the bank
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
            Item[] primaries = Inventory.getItems(PRIMARY_ID);
            Item[] secondaries = Inventory.getItems(SECONDARY_ID);

            for (int i = 0; i < secondaries.length; i++)
            {
                MinimalPotions.status = "Potion " + i;

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
                    return !(Inventory.contains(PRIMARY_ID, SECONDARY_ID));
                }
            }, 500);
        }
    }
}