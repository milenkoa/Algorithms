/*----------------------------------------------------------------
 *  Author:        Milenko Adamovic
 *  Written:       13/10/2013
 *  Last updated:  13/10/2013
 *
 *  Compilation:   javac Board.java
 *  Execution:     
 *  
 *  Model a NxN puzzle
 *
 *
 *----------------------------------------------------------------*/
public class Board {
    private int N; //board is NxN size
    private int[][] tiles;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)  {
        ///tiles = blocks;       
        N = blocks.length;
        tiles = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }    
    }
    
    private int at(int i, int j) {
        return this.tiles[i][j];
    }
      
    // board dimension N
    public int dimension() {
        return N;
    }     
    
    // number of blocks out of place
    public int hamming()  {
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != (i*N + j + 1)) {
                        hamming++;
                    }
                }
            }
        }
        return hamming;
    }   
    
    // sum of Manhattan distances between blocks and goal   
       public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0) {
                    int shouldI = (tiles[i][j] - 1) / N; /* + 1; */
                    int shouldJ = (tiles[i][j] - 1) % N; /* + 1; */
                    int distance = Math.abs(shouldI - i) + Math.abs(shouldJ - j); // + numberOfMovesMadeSoFar; //where is it
                    manhattan += distance;
                }
            }
        }
        return manhattan;
    }    

      
    // is this board the goal board?
    public boolean isGoal() {        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != (i*N + j + 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin()  {
        Board newBoard;
        if (tiles[0][0] != 0 && tiles[0][1] != 0) {
            int temp = tiles[0][0];
            tiles[0][0] = tiles[0][1];
            tiles[0][1] = temp;

            newBoard = new Board(tiles);

            temp = tiles[0][0];
            tiles[0][0] = tiles[0][1];
            tiles[0][1] = temp;
        }
        else {
            int temp = tiles[1][0];
            tiles[1][0] = tiles[1][1];
            tiles[1][1] = temp;

            newBoard = new Board(tiles);

            temp = tiles[1][0];
            tiles[1][0] = tiles[1][1];
            tiles[1][1] = temp;
        }
        return newBoard;
    }                  
    
    // does this board equal y?
    public boolean equals(Object y) { 
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }       
        if (y.getClass() != this.getClass()) {
            return false;
        }
        if (N != ((Board) y).dimension()) {
            return false;
        }
       
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != ((Board) y).at(i, j)) {
                        return false;
                    }
                }
            }
        }    
        return true;
    }    
    
     //all neighboring boards
    public Iterable<Board> neighbors()  {
        Bag<Board> tmp = new Bag<Board>();
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    //we found zero tile; create Board by exchange it with any
                    // neighboring tile
                    
                    //switch (i,j) with (i-1, j)
                    if (i > 0) {
                        int temp = tiles[i][j];
                        tiles[i][j] = tiles[i-1][j];
                        tiles[i-1][j] = temp;

                        Board newBoard = new Board(tiles);

                        temp = tiles[i][j];
                        tiles[i][j] = tiles[i-1][j];
                        tiles[i-1][j] = temp;
                        
                        tmp.add(newBoard);      
                    }
                    
                     //switch (i,j) with (i, j-1)
                    if (j > 0) {
                        int temp = tiles[i][j];
                        tiles[i][j] = tiles[i][j-1];
                        tiles[i][j-1] = temp;

                        Board newBoard = new Board(tiles);

                        temp = tiles[i][j];
                        tiles[i][j] = tiles[i][j-1];
                        tiles[i][j-1] = temp;
                        
                        tmp.add(newBoard);      
                    }
                    
                    //switch (i,j) with (i+1, j)
                    if (i < N-1) {
                        int temp = tiles[i][j];
                        tiles[i][j] = tiles[i+1][j];
                        tiles[i+1][j] = temp;

                        Board newBoard = new Board(tiles);

                        temp = tiles[i][j];
                        tiles[i][j] = tiles[i+1][j];
                        tiles[i+1][j] = temp;
                        
                        tmp.add(newBoard);      
                    }
                    
                    //switch (i,j) with (i, j+1)
                    if (j < N-1) {
                        int temp = tiles[i][j];
                        tiles[i][j] = tiles[i][j+1];
                        tiles[i][j+1] = temp;

                        Board newBoard = new Board(tiles);

                        temp = tiles[i][j];
                        tiles[i][j] = tiles[i][j+1];
                        tiles[i][j+1] = temp;
                        
                        tmp.add(newBoard);      
                    }
                }
            }
        }    
        
        return tmp;
        
    } 
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
        
    } 
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        StdOut.println(initial.toString());
        StdOut.println("\n");
        StdOut.println("Hamming: " + initial.hamming());
        StdOut.println("Manhattan: " + initial.manhattan());
        StdOut.println("Is goal: " + initial.isGoal());
        
//         Board twin = initial.twin();
//         
//         StdOut.println(twin.toString());
//         StdOut.println(initial.toString());
         
         for (Board b: initial.neighbors()) {
             StdOut.println(b.toString());
         }
         
         
    }
}
