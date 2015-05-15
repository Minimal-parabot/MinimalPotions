package org.parabot.minimal.minimalpotions;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.wrappers.Item;

public class MakeFlasks implements Strategy
{
    private final Potion potion;

    public MakeFlasks(Potion potion)
    {
        this.potion = potion;
    }

    @Override
    public boolean activate()
    {
        return potion.isFlaskable()
                && Inventory.getCount(potion.getId()) >= 2;
    }

    @Override
    public void execute()
    {
        Item[] potions = Inventory.getItems(potion.getId());

        if (potions != null)
        {
            Logger.addMessage("Making flasks", true);

            Menu.sendAction(447, potions[0].getId() - 1, potions[0].getSlot(), 3214);
            Time.sleep(50);

            Menu.sendAction(870, potions[1].getId() - 1, potions[1].getSlot(), 3214);
            Time.sleep(50);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return !Inventory.contains(potion.getId());
                }
            }, 250);
        }
    }
}