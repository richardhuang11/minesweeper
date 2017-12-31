package minesweeper;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;


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
public class TitleScreen extends JPanel implements ActionListener
{
    private String title = "Welcome!";

    private Container cont;

    private JFrame menuScreen;

    private JButton easyButton;

    private JButton mediumButton;

    private JButton hardButton;

    private JButton quitButton;

    private JLabel player1;

    private JTextField firstText;

    private JLabel player2;

    private JTextField secondText;

    private String player1Name;

    private String player2Name;


    /**
     * Constructor.
     * 
     * @throws IOException
     *             file IO
     */
    public TitleScreen() throws IOException
    {

        menuScreen = new JFrame( "Menu Screen" );
        BufferedImage img = ImageIO.read( new File( "/Users/HarrisonWang/Dropbox/game/img/minesweeperbackground.png" ) );//
        JLabel background = new JLabel( new ImageIcon( img ) );
        background.setBounds( -400, -500, 1500, 1500 );
        player1 = new JLabel( "Player 1:  " );
        firstText = new JTextField( 15 );
        player2 = new JLabel( "Player 2:  " );
        secondText = new JTextField( 15 );
        quitButton = new JButton( "Quit" );
        easyButton = new JButton( "Easy" );
        mediumButton = new JButton( "Medium" );
        hardButton = new JButton( "Hard" );
        easyButton.setBounds( 90, 170, 100, 25 );
        easyButton.addActionListener( this );
        mediumButton.setBounds( 90, 200, 100, 25 );
        mediumButton.addActionListener( this );
        hardButton.setBounds( 90, 230, 100, 25 );
        hardButton.addActionListener( this );
        quitButton.setBounds( 90, 260, 100, 25 );
        quitButton.addActionListener( this );
        easyButton.setBackground( Color.WHITE );
        mediumButton.setBackground( Color.WHITE );
        hardButton.setBackground( Color.WHITE );
        quitButton.setBackground( Color.WHITE );
        menuScreen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        cont = menuScreen.getContentPane();
        this.setLayout( null );
        this.add( quitButton );
        this.add( mediumButton );
        this.add( hardButton );
        this.add( easyButton );
        this.add( background );
        player1.setBounds( 220, 20, 100, 25 );
        player2.setBounds( 400, 20, 100, 25 );
        firstText.setBounds( 280, 20, 100, 25 );
        secondText.setBounds( 460, 20, 100, 25 );
        player1.setBackground( Color.WHITE );
        player2.setBackground( Color.WHITE );
        this.add( player2 );
        this.add( firstText );
        this.add( secondText );
        this.add( player1 );
        firstText.addActionListener( this );
        secondText.addActionListener( this );
        cont.add( this );
        this.setPreferredSize( new Dimension( 700, 500 ) );
        menuScreen.pack();
        menuScreen.setVisible( true );
        menuScreen.setLocationRelativeTo( null );
        menuScreen.setVisible( true );
    }


    /**
     * Sets fonts/colors.
     * 
     * @param g
     *            Graphics class
     */
    public void paintComponent( Graphics g )
    {
        g.setFont( new Font( "Times New Roman", Font.BOLD, 30 ) );
        g.setColor( Color.RED );
        g.drawString( title, 90, 40 );
    }


    /**
     * Sets the player names.
     */
    public void checkText()
    {
        player1Name = firstText.getText();
        player2Name = secondText.getText();
        if ( player1Name.length() == 0 )
        {
            player1Name = "Guest";
        }
        if ( player2Name.length() == 0 )
        {
            player2Name = player1Name;
        }
    }


    /**
     * Performs certain actions when a button is clicked.
     * 
     * @param e
     *            ActionEvent class
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        checkText();
        Object obj = e.getSource();
        if ( obj == easyButton )
        {
            this.menuScreen.dispose();
            new Mines( 1, player1Name, player2Name );
        }
        if ( obj == mediumButton )
        {
            this.menuScreen.dispose();
            new Mines( 2, player1Name, player2Name );
        }
        if ( obj == hardButton )
        {
            this.menuScreen.dispose();
            new Mines( 3, player1Name, player2Name );
        }
        if ( obj == quitButton )
        {
            this.menuScreen.dispose();
        }
    }


    /**
     * Calls constructor.
     * 
     * @param args
     *            N/A
     * @throws IOException
     *             file IO
     */
    public static void main( String[] args ) throws IOException
    {
        new TitleScreen();
    }
}