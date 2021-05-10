package matrix;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A generic 2D-matrix.
 * @param <E> the cell type.
 */
public class Matrix implements Iterable {
    private int[][] data;

    private int rows() { return data.length; }
    private int columns() { return data[0].length; }

    private void checkBounds(int row, int column) {
        if (row < 0 || row >= rows())
            throw new ArrayIndexOutOfBoundsException("row is out of bounds");
        if (column < 0 || column >= columns())
            throw new ArrayIndexOutOfBoundsException("column is out of bounds");
    }

    /**
     * Constructs a Matrix.
     *
     * @param rows - the number of rows.
     * @param columns - the number of columns.
     */
    public Matrix(int rows, int columns) {
        // arrays of arrays
        // data[row][col] (row major)
        // data[col][row] (column major)

        // allocate all the columns
        data = new int[columns][];
        for (int i = 0; i < columns; i++) {
            // allocate all the rows
            data[i] = new int[rows];
        }
    }

    /**
     * Assigns a value to a given cell, specified by its row, column coordinates.
     *
     * @param row - the row index with 0-based indexing.
     * @param column - the column index with 0-based indexing.
     * @param value - the value to be assigned to the given cell.
     */
    public void insert(int row, int column, int value) {
        checkBounds(row, column);
        data[column][row] = value;
    }

    /**
     * Gets the value at a given cell, specified by its row, column coordinates.
     *
     * @param row - the row index with 0-based indexing.
     * @param column - the column index with 0-based indexing.
     * @return the value located at the given cell.
     */
    public int get(int row, int column) {
        checkBounds(row, column);
        return data[column][row];
    }

    /**
     * Gets the total number of cells in the matrix.
     *
     * @return an int equal to the total number of cells in the matrix.
     */
    public int size() {
        return rows() * columns();
    }

    /**
     * Converts the matrix to String format.
     *
     * @return a String representation of the matrix.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int row = 0; row < rows(); row++) {
            if (row > 0)
                result.append('\n');

            for (int col = 0; col < columns(); col++) {
                // if we're not the first column, don't put a tab in
                if (col > 0)
                    result.append('\t');

                result.append(get(row, col));
            }
        }

        return result.toString();
    }

    /**
     * Gets an iterator for the matrix. The iterator follows column-major order.
     *
     * @return an iterator for the matrix.
     */
    public Iterator iterator() {
        return new Iterator() {
            int row = 0;
            int col = 0;

            @Override
            public boolean hasNext() {
                return row < rows() && col < columns();
            }

            @Override
            public Object next() {
                // retrieve the current element
                final int element = get(row, col);

                // move to the next element
                row++;
                if (row == rows()) {
                    // we're at the of a column, so move to the next column
                    row = 0;
                    col++;
                }

                return element;
            }
        };
    }

}