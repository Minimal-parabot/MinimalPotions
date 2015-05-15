package org.parabot.minimal.minimalpotions;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.Loader;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

@ScriptManifest(author = "Minimal",
        name = "Minimal Potions",
        category = Category.HERBLORE,
        description = "Creates potions on the Ikov server.",
        servers = { "Ikov" },
        version = 1.1)

public class MinimalPotions extends Script implements Paintable, MessageListener
{
    private final ArrayList<Strategy> strategies = new ArrayList<>();

    private final Image IMG = getImage("http://i.imgur.com/pKxdbKS.png");

    private Timer timer = new Timer();

    private int potionsMade = 0;

    @Override
    public boolean onExecute()
    {
        MinimalPotionsGUI gui = new MinimalPotionsGUI();
        gui.setVisible(true);

        while (gui.isVisible())
        {
            Time.sleep(500);
        }

        Potion potion = gui.getPotion();

        strategies.add(new Relog());
        strategies.add(new MakePotions(potion));
        strategies.add(new MakeFlasks(potion));
        strategies.add(new Banker(potion));
        provide(strategies);
        return true;
    }

    @Override
    public void paint(Graphics g)
    {
        DecimalFormat commas = new DecimalFormat("#,###");

        g.setColor(new Color(31, 34, 50));

        g.drawImage(IMG, 550, 209, null);
        g.drawString("Time: " + timer.toString(), 560, 275);
        g.drawString("Potions: " + commas.format(potionsMade), 560, 331);
    }
    
    @Override
    public void messageReceived(MessageEvent m)
    {
        if (m.getType() == 0)
        {
            String message = m.getMessage().toLowerCase();

            if (message.contains("object") || message.contains("not in a")
                    || message.contains("is already on") || message.contains("exist"))
            {
                Logger.addMessage("Account is nulled", true);

                forceLogout();
            }
            else if (message.contains("you make a"))
            {
                potionsMade++;
            }
        }
    }

    private Image getImage(String str)
    {
        try
        {
            return ImageIO.read(new URL(str));
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static void forceLogout()
    {
        try
        {
            Class<?> c = Loader.getClient().getClass();

            Method m = c.getDeclaredMethod("am");
            m.setAccessible(true);

            m.invoke(Loader.getClient());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}