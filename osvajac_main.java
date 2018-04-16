
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class osvajac_main
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("OsvajaÄ�");

        frame.setSize(380, 300);
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton new_game = new JButton("New Game");
        panel.add(new_game);
        new_game.addActionListener(new_game(new Player[4]));

        JButton load_game = new JButton("Load Game");
        panel.add(load_game);
        new_game.addActionListener(load_game());

        frame.setVisible(true);
    }

    private static ActionListener new_game(Player players[])
    {



        return null;
    }

    private static ActionListener load_game()
    {

        Player players[] = new Player[4];

        // TODO: Load all player data

        new_game(players);

        return null;
    }

}
