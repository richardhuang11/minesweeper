package minesweeper;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.lang.model.element.Element;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;


/**
 * The Board class contains the methods for creating the board with the proper
 * images, assigning values to each square, and planting the mines randomly. It
 * also determines the size of the board depending on the level of difficulty
 * chosen by the two players.
 *
 * @author Harrison Wang, Richard Huang, Aryan Singh
 * @version May 21, 2016
 * @author Period: 4
 * @author Assignment: game
 *
 * @author Sources: N/A
 */
public class Board extends JPanel
{
    // Constants
    private final int CELL_SIZE = 15;

    private final int NUM_IMAGES = 13;

    private final int MINE_IMAGE = 9; // mine

    private final int COVER_IMAGE = 10; // blank square, not clicked on yet

    private final int FLAG_IMAGE = 11; // flag

    private final int WRONG_MARK_IMAGE = 12; // wrongly flagged

    private JLabel statusBar;

    private int totalMines; // can change total mines

    private int remainderMines;

    private int rows;

    private int columns; // change num of rows and cols depending on
                         // difficulty

    private Cell[][] cells;

    private Image[] image;

    private boolean inGame; // determines whether or not the player is done

    Scanner scan; // scans for user input

    private String player1;

    private String player2;

    private boolean player1Turn;

    int gameTurn = 0;

    private int gameType = 0;


    /**
     * Constructor.
     * 
     * @param status
     *            lists the player's turn and if the game is won/lost
     * @param x
     *            gameType
     * @param firstPlayer
     *            first player's name
     * 
     * @param secondPlayer
     *            second player's name
     */
    public Board( JLabel status, int x, String firstPlayer, String secondPlayer )
    {
        statusBar = status;
        player1 = firstPlayer;
        player2 = secondPlayer;
        image = new Image[NUM_IMAGES]; // scans in an array of images
        for ( int i = 0; i < NUM_IMAGES; i++ )
        {
            String path = "img/j" + i + ".gif"; // format of img with # plus gif
            image[i] = new ImageIcon( path ).getImage(); // sets it as an image
        }
        setDoubleBuffered( true );
        addMouseListener( new MinesAdapter() );
        newGame( x ); // starts new game
    }


    /**
     * Starts a new game by setting instance variables, calling
     * initializeCells() and placeBombs().
     * 
     * @param x
     *            set to gameType
     */
    public void newGame( int x )
    {
        gameType = x;
        inGame = true; // sets status of game to true
        initializeCells(); // creates the board
        remainderMines = totalMines; // starts off with a set amount of
        // mines
        statusBar.setText( "Remaining mines: "
            + Integer.toString( remainderMines + 1 ) + " Turn: " + player1 );
        player1Turn = true;
        // tells how many flags you have left
        placeBombs();
    }


    /**
     * Initializes the board and calls the right level difficulty method.
     */
    private void initializeCells()
    {
        if ( gameType == 1 )
        {
            initializeCellsE();
        }
        if ( gameType == 2 )
        {
            initializeCellsM();
        }
        if ( gameType == 3 )
        {
            initializeCellsH();
        }
    }


    /**
     * Sets up easy difficulty board.
     */
    private void initializeCellsE()
    {
        rows = 9;
        columns = 9; // easy mode, initializes the cells in that array length
        totalMines = 9;
        cells = new Cell[rows][columns];
        for ( int i = 0; i < rows; ++i ) // creates the board
        {
            for ( int j = 0; j < columns; ++j )
            {
                cells[i][j] = new Cell();
            }
        }
    }


    /**
     * Sets up medium difficulty board.
     */
    private void initializeCellsM()
    {
        rows = 16; // medium mode, initializes the cells in that array length
        columns = 16;
        totalMines = 39;
        cells = new Cell[rows][columns];
        for ( int i = 0; i < rows; ++i ) // creates the board
        {
            for ( int j = 0; j < columns; ++j )
            {
                cells[i][j] = new Cell();
            }
        }
    }


    /**
     * Sets up hard difficulty board.
     */
    private void initializeCellsH()
    {
        rows = 20; // hard mode, initializes it in that mode.
        columns = 20;
        totalMines = 89;
        cells = new Cell[rows][columns];
        for ( int i = 0; i < rows; ++i ) // creates the board
        {
            for ( int j = 0; j < columns; ++j )
            {
                cells[i][j] = new Cell();
            }
        }
    }


    /**
     * Places mines randomly on the board using the Random class.
     */
    public void placeBombs()
    {
        Random random = new Random(); // new random for the bombs
        int remainder = totalMines;
        while ( remainder >= 0 ) // randomly places mines on the board
        {
            int x = random.nextInt( rows ); // random place
            int y = random.nextInt( columns );
            Cell cell = cells[x][y];
            if ( !cell.isMine() ) // checks if there's already a mine
            { // makes sure its not a mine, then puts a mine there and
              // decrements
                cell.setMine( true );
                remainder--;
            }
        }
        setMineCounts(); // all the bombs are in place, assigns number now
    }


    /**
     * Sets the numbers of mines around a given location (the number that is
     * displayed to the user).
     */
    private void setMineCounts()
    {
        for ( int i = 0; i < columns; ++i )
        {
            for ( int j = 0; j < rows; ++j )
            {
                Cell cell = cells[i][j];
                if ( !cell.isMine() )
                { // if cell is not a mine, then it will determine the cell's
                  // number by counting/checking the cells around it
                    int count = countMinesAround( i, j );
                    cell.setAroundMines( count );
                }
            }
        }
    }


    /**
     * Counts the number of mines around a location, determines the number for
     * setMineCounts().
     * 
     * @param x
     *            x index in array
     * @param y
     *            y index in array
     * @return number of mines around a location
     */
    private int countMinesAround( int x, int y )
    {
        int count = 0;
        for ( int i = -1; i <= 1; i++ ) // 3 by 3, counts the area around it
        {
            int row = x + i;
            if ( row < 0 || row >= rows )
            {
                continue;
            }
            for ( int j = -1; j <= 1; ++j )
            {
                int column = y + j;
                if ( column < 0 || column >= columns )
                {
                    continue;
                }
                if ( i == 0 && j == 0 )
                {
                    continue;
                }
                if ( cells[row][column].isMine() )
                {
                    count++; // if mine, increment count
                }
            }
        }
        return count;
    }


    /**
     * Puts the proper picture in the square depending on if it is a mine or
     * not.
     * 
     * @param g
     *            Graphics class
     */
    public void paint( Graphics g )
    {
        int coveredCells = 0;
        for ( int i = 0; i < rows; i++ ) // draws images
        {
            for ( int j = 0; j < columns; j++ )
            {
                Cell cell = cells[i][j];
                int imageType;
                int x;
                int y;
                if ( cell.isCovered() )
                {
                    coveredCells++;
                }
                imageType = decideImageType( cell );
                x = j * CELL_SIZE;
                y = i * CELL_SIZE;
                if ( inGame )
                {
                    if ( cell.isMine() && !cell.isCovered() )
                    {
                        g.drawImage( image[9], x, y, this );
                        inGame = false;
                    }
                }
                g.drawImage( image[imageType], x, y, this );
            }
        }
        if ( coveredCells == totalMines + 1 && inGame ) // shows who won/lost
        {
            inGame = false;
            if ( player1Turn )
            {
                statusBar.setText( "Game Won by " + player2 );
            }
            else
            {
                statusBar.setText( "Game Won by " + player1 );
            }
        }
        else if ( !inGame )
        {
            if ( player1Turn )
            {
                statusBar.setText( "Game Lost by " + player2 );
            }
            else
            {
                statusBar.setText( "Game Lost by " + player1 );
            }
        }
    }


    /**
     * Determines what image to set in the cell location.
     * 
     * @param cell
     *            the cell being looked at
     * @return type of image to print
     */
    private int decideImageType( Cell cell )
    {
        int imageType = cell.getValue();
        if ( inGame )
        {
            if ( cell.isCovered() )
            {
                imageType = COVER_IMAGE;
            }
            if ( cell.isFlagged() ) // in game, cell marked, show flag
            {
                imageType = FLAG_IMAGE; // flag
            }
        }
        else
        {
            // reveal the bomb if you are not in game
            if ( cell.isCovered() && cell.isMine() ) // unclicked and is a mine
            {
                cell.uncover(); //
                imageType = MINE_IMAGE; // mine
            }
            if ( cell.isFlagged() ) // flagged
            {
                if ( cell.isMine() )
                {
                    imageType = FLAG_IMAGE; // flag
                }
                else
                {
                    imageType = WRONG_MARK_IMAGE; // wrongly flagged
                }
            }
        }
        return imageType; // type of image
    }


    /**
     * Finds the locations that aren't mines or numbers.
     * 
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     * @param depth
     *            allows for recursion
     */
    public void findEmptyCells( int x, int y, int depth )
    {
        // same as countMinesAround()
        for ( int i = -1; i <= 1; ++i )
        {
            int row = x + i;
            if ( row < 0 || row >= rows )
            {
                continue;
            }
            for ( int j = -1; j <= 1; ++j )
            {
                int column = y + j;
                if ( column < 0 || column >= columns )
                {
                    continue;
                }
                if ( !( i == 0 || j == 0 ) )
                {
                    continue;
                }
                Cell cell = cells[row][column];
                if ( checkIfEmpty( cell ) ) // if that cell is empty
                {
                    cell.uncover(); // uncovers it and says its checked
                    cell.checked();
                    uncoverSurroundingCells( row, column ); // uncovers it
                    findEmptyCells( row, column, depth + 1 ); // redoes it
                    // recursively
                }
            }
        }
        // base case
        if ( depth == 0 )
        {
            clearAllCells();
        }
    }


    /**
     * Uncovers the known squares around a certain cell.
     * 
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     */
    private void uncoverSurroundingCells( int x, int y )
    {
        // same as countMinesAround()
        for ( int i = -1; i <= 1; ++i )
        {
            int row = x + i;
            if ( row < 0 || row >= rows )
            {
                continue;
            }
            for ( int j = -1; j <= 1; ++j )
            {
                int column = y + j;
                if ( column < 0 || column >= columns )
                {
                    continue;
                }
                if ( i == 0 || j == 0 )
                {
                    continue;
                }
                Cell cell = cells[row][column];
                if ( cell.isCovered() && !cell.isEmpty() )
                {
                    cell.uncover();
                }
            }
        }
    }


    /**
     * Checks to see if a cell is empty.
     * 
     * @param cell
     *            square on board
     * @return if empty
     */
    private boolean checkIfEmpty( Cell cell )
    {
        return !cell.isChecked() && cell.isEmpty();
    }


    /**
     * Clears all the cells.
     */
    private void clearAllCells()
    {
        for ( int i = 0; i < rows; ++i )
        {
            for ( int j = 0; j < columns; ++j )
            {
                cells[i][j].clearChecked();
            }
        }
    }


    /**
     * Contains the methods for what to do when the mouse is clicked.
     *
     * @author Harrison Wang, Richard Huang, Aryan Singh
     * @version May 21, 2016
     * @author Period: 4
     * @author Assignment: game
     *
     * @author Sources: N/A
     */
    class MinesAdapter extends MouseAdapter
    {
        /**
         * Performs actions when a cell is clicked on.
         * 
         * @param e
         *            MouseEvent class
         */
        public void mousePressed( MouseEvent e )
        {
            int pressedRow = e.getY() / CELL_SIZE;
            int pressedCol = e.getX() / CELL_SIZE;
            // finds the place pressed
            boolean doRepaint = false;
            Cell pressed;
            if ( !inGame )
            {
                newGame(gameType);
                repaint(); // if the game is finished and you click, restarts
                           // game
            }
            if ( ( pressedRow < 0 || pressedRow >= rows )
                || ( pressedCol < 0 || pressedCol >= columns ) )
            {
                return; // if outside the box, nothing happens
            }
            pressed = cells[pressedRow][pressedCol];
            if ( e.getButton() == MouseEvent.BUTTON3 ) // code for flagging
            {
                doRepaint = true; // if flag,
                if ( !pressed.isCovered() )
                {
                    return; // does nothing if its not cover
                }
                if ( pressed.isFlagged() )
                {
                    pressed.setFlag( false );
                    remainderMines++;
                }
                else
                {
                    pressed.setFlag( true );
                    remainderMines--;
                }
            }
            else
            {
                if ( !pressed.isCovered() || pressed.isFlagged() )
                {
                    return;
                }
                doRepaint = true;
                pressed.uncover();
                if ( pressed.isEmpty() )
                {
                    findEmptyCells( pressedRow, pressedCol, 0 );
                }
                if ( pressed.isMine() )
                {
                    inGame = false;
                }
            }
            if ( player1Turn )
            {
                statusBar.setText( "Remaining mines: "
                    + Integer.toString( remainderMines + 1 ) + " Turn: "
                    + player2 );
                player1Turn = false;
            }
            else
            {
                statusBar.setText( "Remaining mines: "
                    + Integer.toString( remainderMines + 1 ) + " Turn: "
                    + player1 );
                player1Turn = true;
            }
            if ( doRepaint )
            {
                repaint();
            }
        }
    }
}