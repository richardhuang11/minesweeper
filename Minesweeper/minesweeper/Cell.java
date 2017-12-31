package minesweeper;

/**
 * Initializes five instance variables and contains methods that return and set
 * their values.
 *
 * @author Harrison Wang, Richard Huang, Aryan Singh
 * @version May 21, 2016
 * @author Period: 4
 * @author Assignment: game
 *
 * @author Sources: N/A
 */
public class Cell
{
    // instance variables
    private boolean mine;

    private boolean flag;

    private boolean covered;

    private boolean checked;

    private int value;


    /**
     * Constructor.
     */
    public Cell()
    {
        covered = true;
        flag = false;
        mine = false;
        value = 0;
    }


    /**
     * Returns instance variable value.
     * 
     * @return instance variable value
     */
    public int getValue()
    {
        return value;
    }


    /**
     * Returns boolean cover.
     * 
     * @return boolean cover
     */
    public boolean isCovered()
    {
        return covered;
    }


    /**
     * Returns boolean flag.
     * 
     * @return boolean flag
     */
    public boolean isFlagged()
    {
        return flag;
    }


    /**
     * Returns boolean mine.
     * 
     * @return boolean mine
     */
    public boolean isMine()
    {
        return mine;
    }


    /**
     * Returns boolean checked.
     * 
     * @return boolean checked
     */
    public boolean isChecked()
    {
        return checked;
    }


    /**
     * Sets instance variable cover to false.
     */
    public void uncover()
    {
        covered = false;
    }


    /**
     * Sets instance variable flag to the boolean taken as a parameter.
     * 
     * @param mark
     *            boolean that instance variable mark will be set to
     */
    public void setFlag( boolean mark )
    {
        flag = mark;
    }


    /**
     * Checks if value is 0.
     * 
     * @return if value equals 0
     */
    public boolean isEmpty()
    {
        return value == 0;
    }


    /**
     * Sets the instance variable mine to the boolean taken as a parameter.
     * 
     * @param mine
     *            boolean that instance variable mine will be set to.
     */
    public void setMine( boolean mine )
    {
        this.mine = mine;
    }


    /**
     * Returns whether instance variables cover and mine are both true.
     * 
     * @return if instance variables cover and mine are both true
     */
    public boolean isCoveredMine()
    {
        return covered && mine;
    }


    /**
     * Sets instance variable checked to true.
     */
    public void checked()
    {
        checked = true;
    }


    /**
     * Sets instance variable checked to false.
     */
    public void clearChecked()
    {
        checked = false;
    }


    /**
     * Sets the instance variable count to the boolean taken as a parameter.
     * 
     * @param count
     *            boolean that instance variable count will be set to.
     */
    public void setAroundMines( int count )
    {
        value = count;
    }

}