package org.parabot.minimal.minimalpotions;

public enum Potion
{
    GUAM_UNF(1, 92, "Vial of water", 228, "Clean guam", 250, false),
    ATTACK(1, 122, "Guam unf", 92, "Eye of newt", 222, false),
    MARRENTILL_UNF(5, 94, "Vial of water", 228, "Clean marrentill", 252, false),
    ANTIPOISON(5, 176, "Marrentill unf", 94, "Unicorn horn dust", 236, false),
    RECOVER_SPECIAL(84, 15302, "Super energy(3)", 3019, "Papaya fruit", 5973, true),
    SUPER_ANTIFIRE(85, 15306, "Antifire(3)", 2455, "Phoenix feather", 4622, false);

    private int level;
    private int id;
    private String primaryName;
    private int primaryId;
    private String secondaryName;
    private int secondaryId;
    private boolean isFlaskable;
    
    Potion(int level, int id, String primaryName, int primaryId, String secondaryName, int secondaryId, boolean ableToFlask)
    {
        this.level = level;
        this.id = id;
        this.primaryName = primaryName;
        this.primaryId = primaryId;
        this.secondaryName = secondaryName;
        this.secondaryId = secondaryId;
        this.isFlaskable = ableToFlask;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getPrimaryName()
    {
        return primaryName;
    }
    
    public int getPrimaryId()
    {
        return primaryId;
    }
    
    public String getSecondaryName()
    {
        return secondaryName;
    }
    
    public int getSecondaryId()
    {
        return secondaryId;
    }
    
    public boolean isFlaskable()
    {
        return isFlaskable;
    }

    @Override
    public String toString()
    {
        return name().charAt(0) + name().substring(1).toLowerCase().replace("_", " ") + " (" + level + ")";
    }
}