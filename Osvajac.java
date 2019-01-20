
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Osvajac
{

    static Player players[] = new Player[10];
    
    public static void main(String[] args)
    {
        JFrame main_frame = new JFrame("Osvajač");

        main_frame.setSize(300, 110);
        main_frame.setLocationRelativeTo(null);
        main_frame.setResizable(false);
        main_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel main_panel = new JPanel();
        main_frame.add(main_panel);

        JButton load_game = new JButton("Učitaj Igru");
        main_panel.add(load_game);
        load_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            try {
                load(players);
                play(players, 0);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
            });
        
        JButton save_game = new JButton("Sačuvaj Igru");
        main_panel.add(save_game);
        save_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            try {
                save(players);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
            });
        
        JButton new_game = new JButton("Nova Igra");
        main_panel.add(new_game);
        new_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {
            // TODO: Ask for player names and number of players
            for (int i = 0; i < players.length; i++) {
                players[i] = new Player("Igrač " + i);
            }
            
            main_frame.dispose();
            play(players, 0);
        }
            });
        
        main_frame.setVisible(true);
    }
    
    static JTextField money_txtfield = new JTextField("", 28);
    static JTextField soldier_txtfield = new JTextField("", 34);
    static JTextField tank_txtfield = new JTextField("", 34);
    static JTextField mine_txtfield = new JTextField("", 34);
    static JTextField camp_txtfield = new JTextField("", 34);
    
    static void update_text_fields(Player p) 
    {
        money_txtfield.setText("Trenutno imaš " + p.money + "$.");
        
        soldier_txtfield.setText("Trenutno imaš " + p.money + "$. "
                + "Vojnika imaš " + p.soldier_count + ". Kasarni imaš " + p.soldier_factory);
        tank_txtfield.setText("Trenutno imaš " + p.money + "$. "
                + "Tenkova imaš " + p.tank_count + ". Fabrika Tenkova imaš " + p.tank_factory);
        
        mine_txtfield.setText("Trenutno imaš " + p.money + "$. "
                + "Rudnika imaš " + p.mines + ". Prihod je " + (p.mines*p.mines_profit));
        camp_txtfield.setText("Trenutno imaš " + p.money + "$. "
                + "Logora imaš " + p.prisoner_camps + ". Aktivno je " + p.prisoner_count + ". Prihod je " + (p.prisoner_count*p.prisoner_camps_profit));
    }
    
    public static void load(Player players[]) throws IOException 
    {
        BufferedReader reader = new BufferedReader(new FileReader("savegame.sav"));
        
        int i = 0;
        
        String player_name = reader.readLine();
        if (player_name != null) {
            players[i] = new Player(player_name);
            
            players[i].money = Integer.parseInt(reader.readLine());
            players[i].soldier_count = Integer.parseInt(reader.readLine());
            players[i].tank_count = Integer.parseInt(reader.readLine());
            players[i].soldier_factory = Integer.parseInt(reader.readLine());
            players[i].tank_factory = Integer.parseInt(reader.readLine());
            players[i].mines = Integer.parseInt(reader.readLine());
            players[i].mines_profit = Integer.parseInt(reader.readLine());
            players[i].prisoner_camps = Integer.parseInt(reader.readLine());
            players[i].prisoner_camps_profit = Integer.parseInt(reader.readLine());
            players[i].prisoner_count = Integer.parseInt(reader.readLine());
            players[i].institutes = Integer.parseInt(reader.readLine());
            
            i++;
        }
        
        reader.close();   
    }
    
    public static void save(Player players[]) throws IOException 
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter("savegame.sav"));
        
        for (int i = 0; i < players.length; i++) {
            writer.write(players[i].player_name + "\n");
            writer.write(players[i].money + "\n");
            writer.write(players[i].soldier_count + "\n");
            writer.write(players[i].tank_count + "\n");
            writer.write(players[i].soldier_factory + "\n");
            writer.write(players[i].tank_factory + "\n");
            writer.write(players[i].mines + "\n");
            writer.write(players[i].mines_profit + "\n");
            writer.write(players[i].prisoner_camps + "\n");
            writer.write(players[i].prisoner_camps_profit + "\n");
            writer.write(players[i].prisoner_count + "\n");
            writer.write(players[i].institutes + "\n");
        }
        
        writer.close();
    }
    
    private static void play(Player players[], int current_player) 
    {
        if (players[current_player] == null) {
            if (current_player == 0) {
                // TODO: No players... Maybe throw exception
                return;
            }
            
            play(players, (current_player + 1) % players.length);
        }
        
        JFrame player_frame = new JFrame("Osvajač " + players[current_player].player_name);
        
        player_frame.setSize(380, 300);
        player_frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        player_frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                player_frame.dispose();
                main(null);
            }
        });
        player_frame.setLocationRelativeTo(null);
        player_frame.setResizable(false);

        JPanel player_panel = new JPanel();
        player_frame.add(player_panel);
        
        SoldierButton(players[current_player], player_panel);
        TankButton(players[current_player], player_panel);
        
        MineButton(players[current_player], player_panel);
        CampButton(players[current_player], player_panel);
        
        InstituteButton(players[current_player], player_panel);

        JButton next = new JButton("Sledeći igrač");
        player_panel.add(next);
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
        {            
            player_frame.dispose();
            
            play(players, (current_player + 1) % players.length);
        }
            });

        players[current_player].money += players[current_player].prisoner_count * players[current_player].prisoner_camps_profit;
        players[current_player].money += players[current_player].mines * players[current_player].mines_profit;

        update_text_fields(players[current_player]);
        player_panel.add(money_txtfield);

        player_frame.setVisible(true);
    }
    
    public static void SoldierButton(Player p, JPanel panel)
    {
        JButton v = new JButton("Vojnici");
        panel.add(v);
        v.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
        {
            JFrame vf = new JFrame("Koliko?");
            vf.setSize(400, 100);
            vf.setLocationRelativeTo(panel);
            vf.setResizable(false);

            JPanel vp = new JPanel();
            vf.add(vp);

            update_text_fields(p);
            soldier_txtfield.setEditable(false);
            vp.add(soldier_txtfield);

            JButton vkg = new JButton("Kupi Kasarnu");
            vp.add(vkg);
            vkg.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.money >= 300) {
                    vkg.setBackground(UIManager.getColor("Button.background"));
                    
                    p.money -= 300;
                    p.soldier_factory += 1;
                    
                    update_text_fields(p);
                } else {
                    vkg.setEnabled(false);
                }

            }
                });
            
            JButton vk1 = new JButton("1");
            vp.add(vk1);
            vk1.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.soldier_factory > 0) {
                    if (p.money >= 100) {
                        p.money -= 100;
                        p.soldier_count += 1;
                        
                        update_text_fields(p);
                    } else {
                        vk1.setEnabled(false);
                    }

                } else {
                    vkg.setBackground(Color.RED);
                }

            }
                });
            JButton vk2 = new JButton("2");
            vp.add(vk2);
            vk2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.soldier_factory > 0) {
                    if (p.money >= 200) {
                        p.soldier_count += 2;
                        p.money -= 200;
                        
                        update_text_fields(p);
                    } else {
                        vk2.setEnabled(false);
                    }

                } else {
                    vkg.setBackground(Color.RED);
                }

            }

                });
            final JButton vk3 = new JButton("3");
            vp.add(vk3);
            vk3.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.soldier_factory > 0) {
                    if (p.money >= 300) {
                        p.soldier_count += 3;
                        p.money -= 300;
                        
                        update_text_fields(p);
                    } else {
                        vk3.setEnabled(false);
                    }

                } else {
                    vkg.setBackground(Color.RED);
                }

            }
                });
            
            vf.setVisible(true);
        }

            });
    }
    
    public static void TankButton(Player p, JPanel panel)
    {
        JButton t = new JButton("Tenkovi");
        panel.add(t);
        t.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
        {
            JFrame tf = new JFrame("Koliko?");
            tf.setSize(400, 100);
            tf.setLocationRelativeTo(panel);
            tf.setResizable(false);

            JPanel tp = new JPanel();
            tf.add(tp);

            update_text_fields(p);
            tank_txtfield.setEditable(false);
            tp.add(tank_txtfield);

            JButton tkg = new JButton("Kupi Fabriku Tenkova");
            tp.add(tkg);
            tkg.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.money >= 500) {
                    tkg.setBackground(UIManager.getColor("Button.background"));
                    
                    p.money -= 500;
                    p.tank_factory += 1;
                    
                    update_text_fields(p);
                } else {
                    tkg.setEnabled(false);
                }

            }
                });
            
            JButton tk1 = new JButton("1");
            tp.add(tk1);
            tk1.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.tank_factory > 0) {
                    if (p.money >= 300) {
                        p.tank_count += 1;
                        p.money -= 300;

                        update_text_fields(p);
                    } else {
                        tk1.setEnabled(false);
                    }

                } else {
                    tkg.setBackground(Color.RED);
                }

            }
                });
            final JButton tk2 = new JButton("2");
            tp.add(tk2);
            tk2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.tank_factory > 0) {
                    if (p.money >= 600) {
                        p.tank_count += 2;
                        p.money -= 600;
                        
                        update_text_fields(p);
                    } else {
                        tk2.setEnabled(false);
                    }

                } else {
                    tkg.setBackground(Color.RED);
                }

            }
                });
            final JButton tk3 = new JButton("3");
            tp.add(tk3);
            tk3.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.tank_factory > 0) {
                    if (p.money >= 900) {
                        p.tank_count += 3;
                        p.money -= 900;
                        
                        update_text_fields(p);
                    } else {
                        tk3.setEnabled(false);
                    }

                } else {
                    tkg.setBackground(Color.RED);
                }

            }
                });
            
            tf.setVisible(true);
        }


            });
    }

    public static void MineButton(Player p, JPanel panel)
    {
        JButton r = new JButton("Rudnik");
        panel.add(r);
        r.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
        {
            JFrame rf = new JFrame("Rudnik");
            rf.setVisible(true);
            rf.setSize(400, 100);
            rf.setResizable(false);
            rf.setLocationRelativeTo(panel);

            JPanel rp = new JPanel();
            rf.add(rp);

            update_text_fields(p);
            mine_txtfield.setEditable(false);
            rp.add(mine_txtfield);

            final JButton rk = new JButton("Kupi");
            rp.add(rk);
            rk.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.money >= 300) {
                    p.mines += 1;
                    p.money -= 300;
                    
                    update_text_fields(p);
                } else {
                    rk.setEnabled(false);
                }

            }
                });
        }
            });
    }

    public static void CampButton(Player p, JPanel panel)
    {
        JButton l = new JButton("Logor");
        panel.add(l);
        l.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
        {
            JFrame lf = new JFrame("Logor");
            lf.setVisible(true);
            lf.setSize(400, 100);
            lf.setLocationRelativeTo(panel);
            lf.setResizable(false);

            JPanel lp = new JPanel();
            lf.add(lp);

            update_text_fields(p);
            camp_txtfield.setEditable(false);
            lp.add(camp_txtfield);

            final JButton lk = new JButton("Kupi");
            lp.add(lk);
            lk.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.money >= 500) {
                    p.prisoner_camps += 1;
                    p.money -= 500;
                    
                    update_text_fields(p);
                } else {
                    lk.setEnabled(false);
                }

            }
                });
            final JButton lz = new JButton("Zarobljen");
            lp.add(lz);
            lz.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.prisoner_camps > 0) {
                    p.prisoner_camps -= 1;
                    p.prisoner_count += 1;

                    update_text_fields(p);
                } else {
                    lz.setEnabled(false);
                }

            }
                });
        }
            });
    }
 
    public static void InstituteButton(Player p, JPanel panel)
    {
        JButton inst = new JButton("Tajno Oruzje");
        panel.add(inst);
        inst.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
        {
            JFrame fi = new JFrame();
            fi.setVisible(true);
            fi.setResizable(false);
            fi.setSize(180, 100);
            fi.setLocationRelativeTo(panel);

            JPanel ip = new JPanel();
            fi.add(ip);

            final JButton ik = new JButton("Kupi Institut");
            ip.add(ik);
            ik.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.money >= 2000) {
                    ik.setBackground(UIManager.getColor("Button.background"));
                    
                    p.money -= 2000;
                    p.institutes += 1;
                    
                    update_text_fields(p);
                } else {
                    ik.setEnabled(false);
                }

            }
                });
            final JButton kk = new JButton("Kupi Karticu");
            ip.add(kk);
            kk.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
            {
                if (p.institutes > 0) {
                    if (p.money >= 1000) {
                        p.money -= 1000;
                        
                        update_text_fields(p);
                    } else {
                        kk.setEnabled(false);
                    }

                } else {
                    ik.setBackground(Color.RED);
                }
            }
                });
        }
            });
    }
    
}
