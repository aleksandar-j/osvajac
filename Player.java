
public class Player
{
    String player_name = "PLAYER";

    public int money = 2500;
    
    public int soldier_count = 0;
    public int tank_count = 0;
    
    public int soldier_factory = 0;
    public int tank_factory = 0;
    
    public int mines = 0;
    public int mines_profit = 100;
    
    public int prisoner_camps = 0;
    public int prisoner_camps_profit = 300;
    public int prisoner_count = 0;
    
    public int institutes = 0;
    
    public Player(String name)
    {
        this.player_name = name;
    }
    
}
