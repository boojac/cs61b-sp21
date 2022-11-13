package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */

    // TODO: Modify this.board (and perhaps this.score) to account
    // for the tilt to the Side SIDE. If the board changed, set the
    // changed local variable to true.


    //修改board实例、修改分数实例、如果有变化，set changed be true
    //所以的移动---调用board的move方法、用tile访问瓷砖----只move一次，来完成整个移动
    //最难的部分是弄清楚每张牌应该在哪一行结束。即这个牌经移动后，位置会在哪里


    //什么时候该更新分数：
    //note that the board.move(c, r, t) method returns true
    // if moving the tile t
    // to column c and row r would replace
    // an existing tile
    // (i.e. you have a merge operation).

    //移动的方向：side parameter is equal to Side.NORTH，一开始都让他向上----testuponly
        /*
        3
        2
        1
        0向上移动：如果上面的元素是空的、此排元素与上排元素一致
         */
    //it is safe to iterate starting from row 3 down,
    // since there’s no way a tile will have to move again after moving once.

    //辅助方法--- that processes a single column of the board,----return a desired row value

    //Board类有一个setViewingPerspective(Side s)函数，它将改变瓦片和移动类的行为，
    //Make sure to use board.
    // setViewingPerpsective to set the perspective back to Side.
    // NORTH before you finish your call to tilt,
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        //判断board的方向
        if (side != Side.NORTH) {
            board.setViewingPerspective(side);
        }


        int board_size = board.size();

        //移动:通过遍历获取现在的位置，然后，看看移动的最终位置
        //1。能不能向上动atleasemoveExist
        //2。向上动到哪
        for (int colIndex = 0; colIndex <board_size; colIndex++)
        {
            int nullCount = 0;
            for (int rowIndex = board_size - 1; rowIndex >= 0; rowIndex --)
            {
                if (board.tile(colIndex,rowIndex) == null)
                {
                    nullCount ++;
                    continue;
                }
                Tile t = board.tile(colIndex, rowIndex);
                if (board.tile(colIndex, rowIndex + nullCount) == null)
                {
                   board.move(colIndex, rowIndex + nullCount, t);
                   changed = true;
                }
            }
        }


        //要合并吗，只能合并一次
        for (int colIndex = 0; colIndex < board_size; colIndex++)
        {
            for (int rowIndex = board_size - 1; rowIndex > 0; rowIndex--) {
                if ((board.tile(colIndex, rowIndex) != null) && (board.tile(colIndex, rowIndex - 1) != null) && (board.tile(colIndex, rowIndex).value() == board.tile(colIndex, rowIndex - 1).value()))
                {
                    Tile nextTile = board.tile(colIndex, rowIndex - 1);
                    board.move(colIndex, rowIndex, nextTile);
                    score += nextTile.value() * 2;
                    changed = true;
                }
            }
        }

        //合并后，一个砖块要向上动到合并好的砖块的下一个位置

        for (int colIndex = 0; colIndex < board_size; colIndex++)
        {
            int nullCount = 0;
            for (int rowIndex = board_size - 1;rowIndex >= 0; rowIndex --)
            {
                if (board.tile(colIndex, rowIndex) == null)
                {
                    nullCount ++;
                    continue;
                }
                Tile t = board.tile(colIndex, rowIndex);
                if (board.tile(colIndex, rowIndex + nullCount) == null)
                {
                    board.move(colIndex, rowIndex + nullCount, t);
                }
            }

        }
        //还原board方向
        if (side != Side.NORTH) {
            board.setViewingPerspective(Side.NORTH);
        }



        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        int size = b.size();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (b.tile(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int board_size = b.size();

        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (b.tile(i, j) == null) {
                }else{
                    if (b.tile(i, j).value() == MAX_PIECE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        int board_size = b.size();
        if (emptySpaceExists(b)) {
            return true;
        }
        if (noBoudary(b)) {
            return true;
        }
        if (inBoundary(b)) {
            return true;
        }
        return false;


        //查看上下/左右有没有相邻的牌，要是有，就可以移动

    }

    public static boolean noBoudary(Board b) {
        int board_size = b.size();
        //不考虑边界条件
        for (int i = 0; i < board_size - 1; i++)
        {
            for (int j = 0; j < board_size - 1; j++)
            {
                if (b.tile(i, j).value() == b.tile(i, j + 1).value())
                {
                    return true;
                }
                if (b.tile(i, j).value() == b.tile(i + 1, j).value())
                {
                    return true;
                }
            }

        }
        return false;
    }

    public static boolean inBoundary(Board b) {
        int board_size = b.size();

        int col = board_size -1;
        for (int i = 0; i < board_size - 1; ++i) {
            if (b.tile(col, i).value() == b.tile(col, i + 1).value())
                return true;
        }

        int row = board_size -1;
        for (int j = 0; j < board_size - 1; ++j) {
            if (b.tile(j, row).value() == b.tile(j + 1, row).value())
                return true;
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
