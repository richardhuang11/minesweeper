package minesweeper;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Contains the methods which initialize the game.
 *
 * @author Harrison Wang, Richard Huang, Aryan Singh
 * @version May 21, 2016
 * @author Period: 4
 * @author Assignment: game
 *
 * @author Sources: N/A
 */
public class Mines extends JFrame
{
    private int WIDTH = 400;

    private int HEIGHT = 400;

    private JLabel statusbar;


    /**
     * Constructor.
     */
    public Mines( int x, String player1, String player2 )
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( WIDTH, HEIGHT );
        setLocationRelativeTo( null );
        setTitle( "Minesweeper" );
        statusbar = new JLabel( "" );
        add( statusbar, BorderLayout.SOUTH );
        add( new Board( statusbar, x, player1, player2 ) );
        setResizable( true );
        setVisible( true );
    }
}