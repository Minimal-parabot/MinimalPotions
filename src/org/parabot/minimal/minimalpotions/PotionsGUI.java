package org.parabot.minimal.minimalpotions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PotionsGUI extends JFrame
{
    private JComboBox<Potion> potionComboBox;

    private final int WINDOW_WIDTH = 250;
    private final int WINDOW_HEIGHT = 125;

    public PotionsGUI()
    {
        setLayout(new BorderLayout());

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        setVisible(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new MainPanel();
        JPanel buttonPanel = new ButtonPanel();

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public class MainPanel extends JPanel
    {
        public MainPanel()
        {
            potionComboBox = new JComboBox<>();
            potionComboBox.setFont(new Font("Helvetica", Font.PLAIN, 16));

            for (Potion p : Potion.values())
            {
                potionComboBox.addItem(p);
            }

            add(potionComboBox);
        }
    }

    public class ButtonPanel extends JPanel
    {
        public ButtonPanel()
        {
            JButton startButton = new JButton("Start");
            startButton.setFont(new Font("Helvetica", Font.BOLD, 20));
            startButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    dispose();
                }
            });

            add(startButton);
        }
    }

    public Potion getPotion()
    {
        return (Potion) potionComboBox.getSelectedItem();
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                PotionsGUI gui = new PotionsGUI();
            }
        });
    }
}