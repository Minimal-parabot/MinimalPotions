package org.parabot.minimal.minimalpotions;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.wrappers.Item;

public class MakeFlasks implements Strategy
{
    private final Potion POTION;
    private final int POTION_ID;

    public MakeFlasks(final Potion POTION)
    {
        this.POTION = POTION;

        this.POTION_ID = POTION.getId();
    }

    @Override
    public boolean activate()
    {
        return POTION.isFlaskable()
                && Inventory.getCount(POTION_ID) >= 2;
    }

    @Override
    public void execute()
    {
        Item[] potions = Inventory.getItems(POTION_ID);

        if (potions != null)
        {
            MinimalPotions.status = "Making flasks";

            Menu.sendAction(447, potions[0].getId() - 1, potions[0].getSlot(), 3214);
            Time.sleep(50);

            Menu.sendAction(870, potions[1].getId() - 1, potions[1].getSlot(), 3214);
            Time.sleep(50);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return !Inventory.contains(POTION_ID);
                }
            }, 250);
        }
    }
}