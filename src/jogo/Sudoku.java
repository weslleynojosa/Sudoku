package jogo;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.TimeoutException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.reader.ParseFormatException;

import org.sat4j.reader.Reader;
import org.sat4j.reader.DimacsReader;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
import java.io.PrintWriter;
import javax.swing.*;


/**
 * The Sudoku game.
 * To solve the number puzzle, each row, each column, and each of the
 * nine 3×3 sub-grids shall contain all of the digits from 1 to 9
 */
public class Sudoku extends JFrame implements ActionListener {
    // Name-constants for the game properties
    public static final int GRID_SIZE = 9;    // Size of the board
    public static final int SUBGRID_SIZE = 3; // Size of the sub-grid

    // Name-constants for UI control (sizes, colors and fonts)
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE;
    public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
    // Board width/height in pixels
    public static final Color OPEN_CELL_BGCOLOR = Color.WHITE;
    public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB
    public static final Color OPEN_CELL_TEXT_NO = Color.RED;
    public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    public static final Color CLOSED_CELL_TEXT = Color.BLACK;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
    private static JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];

    // The game board composes of 9x9 JTextFields,
    // each containing String "1" to "9", or empty String
    //private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];

    private JButton gerarButton = new JButton("Gerar");
    private JButton resolverButton = new JButton("Resolver");
    private JButton limparButton = new JButton("Limpar");

    // Puzzle to be solved and the mask (which can be used to control the
    //  difficulty level).
    // Hardcoded here. Extra credit for automatic puzzle generation
    //  with various difficulty levels.
    private int[][] puzzle =
            {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                    {6, 7, 2, 1, 9, 5, 3, 4, 8},
                    {1, 9, 8, 3, 4, 2, 5, 6, 7},
                    {8, 5, 9, 7, 6, 1, 4, 2, 3},
                    {4, 2, 6, 8, 5, 3, 7, 9, 1},
                    {7, 1, 3, 9, 2, 4, 8, 5, 6},
                    {9, 6, 1, 5, 3, 7, 2, 8, 4},
                    {2, 8, 7, 4, 1, 9, 6, 3, 5},
                    {3, 4, 5, 2, 8, 6, 1, 7, 9}};
    // For testing, open only 2 cells.
    private boolean[][] masks =
            {{true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true}};

    /**
     * Constructor to setup the game and the UI Components
     */
    public Sudoku() {
       // Container cp = getContentPane();
        //cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        //cp.setLayout(new GridLayout(3,1));// 9x9 GridLayout
        JPanel sudokuMain = new JPanel(new GridLayout(1,2));

        JPanel sudokuField = new JPanel();
        InputListener listener = new InputListener();
        sudokuField.setLayout(new GridLayout(9,9));
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                tfCells[row][col] = new JTextField(); // Allocate element of array
                sudokuField.add(tfCells[row][col]);            // ContentPane adds JTextField
                if (masks[row][col]) {
                    tfCells[row][col].setText("");     // set to empty string
                    tfCells[row][col].setEditable(true);
                    tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
                    tfCells[row][col].addActionListener(listener);

                } else {
                    tfCells[row][col].setText(puzzle[row][col] + "");
                    tfCells[row][col].setEditable(false);
                    tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                    tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
                }
                // Beautify all the cells
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                tfCells[row][col].setFont(FONT_NUMBERS);
            }
        }
        sudokuMain.add(sudokuField);

        JPanel buttonField = new JPanel();
        Box box = Box.createVerticalBox();
        resolverButton.setPreferredSize(new Dimension(100,30));
        box.add(resolverButton);
        box.add(Box.createVerticalStrut(10));
        resolverButton.addActionListener(e -> {   //usando lambda
            try {
                PrintWriter writer = new PrintWriter("C:\\Users\\Weslley\\Documents\\sudoku.txt");
                writer.println("c FILE: sudoku.txt");
                writer.println("c");
                writer.println("c SOURCE: Amauri Aires B. Filho and Weslley Nojosa Costa.");
                for(int row = 1; row <= 9; row ++){
                    for(int n = 1; n <= 9; n++){
                        for (int col = 1; col <= 9; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }


                        System.out.println("0\n");
                    }
                    //printf("\n");
                }

                //printf("\nColumns constraints:\n");
                for(int row = 1; row <= 9; row ++){
                    for(int n = 1; n <= 9; n++){
                        for (int col = 1; col <= 9; col ++) {
                            System.out.print(n);
                            System.out.print(col);
                            System.out.print(row);
                            System.out.print(" ");
                        }
                        System.out.println("0\n");
                    }
                    //printf("\n\n");
                }

                //printf("Cells constraints:\n");
                for (int N = 1; N <=9; N++) {
                    for(int row = 1; row <= 9; row ++){
                        for(int col = 1; col <= 9; col++){
                            for (int n = 1; n <= 9; n++) {
                                if(N != n){
                                    System.out.print(-N);
                                    System.out.print(row);
                                    System.out.print(col);
                                    System.out.print(-n);
                                    System.out.print(row);
                                    System.out.print(col);
                                    System.out.print(" ");
                                    System.out.println("0\n");
                                }
                            }
                            //printf("\n");
                        }
                        //printf("\n\n");
                    }
                }

                //printf("\nSubgrid constraints:\n"); // 11
                for(int n = 1; n <= 9; n ++){
                    for(int row = 1; row <= 3; row++){
                        for (int col = 1; col <= 3; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }
                for(int n = 1; n <= 9; n ++){ // 41
                    for(int row = 4; row <= 6; row++){
                        for (int col = 1; col <= 3; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }
                for(int n = 1; n <= 9; n ++){ // 71
                    for(int row = 7; row <= 9; row++){
                        for (int col = 1; col <= 3; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }

                for(int n = 1; n <= 9; n ++){ //14
                    for(int row = 1; row <= 3; row++){
                        for (int col = 4; col <= 6; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }
                for(int n = 1; n <= 9; n ++){ //44
                    for(int row = 4; row <= 6; row++){
                        for (int col = 4; col <= 6; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }
                for(int n = 1; n <= 9; n ++){ // 74
                    for(int row = 7; row <= 9; row++){
                        for (int col = 4; col <= 6; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print("");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }

                for(int n = 1; n <= 9; n ++){ //17
                    for(int row = 1; row <= 3; row++){
                        for (int col = 7; col <= 9; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }
                for(int n = 1; n <= 9; n ++){ //47
                    for(int row = 4; row <= 6; row++){
                        for (int col = 7; col <= 9; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }
                for(int n = 1; n <= 9; n ++){ // 77
                    for(int row = 7; row <= 9; row++){
                        for (int col = 7; col <= 9; col++) {
                            System.out.print(n);
                            System.out.print(row);
                            System.out.print(col);
                            System.out.print(" ");
                        }
                        //printf("0\n");
                    }
                    System.out.println(" ");
                }
                for (int row = 0; row < GRID_SIZE; ++row) {
                    for (int col = 0; col < GRID_SIZE; ++col) {
                        if (tfCells[row][col].getText().isEmpty()== false) {
                            writer.print(tfCells[row][col].getText());
                            writer.print(row + 1);
                            writer.print(col + 1);
                            writer.println(" " + 0);
                        }

                    }
                }
                writer.close();
            }
            catch (FileNotFoundException b){
                System.out.println("Error writing to file!");
            }
            //printf("Row constraints:\n");

        });
        limparButton.setPreferredSize(new Dimension(100,30));
        box.add(limparButton);
        limparButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for (int row = 0; row < GRID_SIZE; ++row) {
                    for (int col = 0; col < GRID_SIZE; ++col) {
                        tfCells[row][col].setText("");
                    }
                }
            }
        });
        buttonField.add(box);

        sudokuMain.add(buttonField);



        JFrame main = new JFrame("Sudoku");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.add(sudokuMain);
        main.setPreferredSize(new Dimension(940,460));
        main.pack();
        main.setLocationRelativeTo(null);
        main.setVisible(true);

    }

    /** The entry main() entry method */
    public static void main(String[] args) {

        Sudoku sudoku = new Sudoku();


        // SAT4J
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new DimacsReader(solver);
        // CNF filename is given on the command line
        try {
            IProblem problem = reader.parseInstance("C:\\Users\\Weslley\\Documents\\sudoku.txt");
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable!");
                int i = 0;
                String[] splitStr = reader.decode(problem.model()).substring(i).split("\\s+");
                while(i < splitStr.length){
                    int cell = Integer.parseInt(splitStr[i]);
                    // Printing only positive numbers
                    // Weslley, basta você separar os digitos dos números positivos para saber n, l e c.
                    // Dessa forma, você irá preencher as células com a resposta.
                    if (cell > 0){
                        // Aqui mostro como separar os dígitos
                        System.out.print(cell + " "); // O número por completo
                        int N =  Integer.parseInt(splitStr[i].substring(0, 1)); // N -> Int
                        System.out.print(N + " "); // N
                        int R =  Integer.parseInt(splitStr[i].substring(1, 2)); // R -> Int
                        System.out.print(R + " "); // R
                        int C =  Integer.parseInt(splitStr[i].substring(2, 3)); // C -> Int
                        System.out.println(C + " "); // C
                        // Usa algum comando "AQUI" para inserir a resposta nas células e retira os System.out.print
                        // Exemplo: tfCells[R][C].setText(N);
                    }
                    i++;
                }
            } else {
                System.out.println("Unsatisfiable!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("1");
            // TODO Auto-generated catch block
        } catch (ParseFormatException e) {
            System.out.println("2");
            // TODO Auto-generated catch block
        } catch (IOException e) {
            System.out.println("3");
            // TODO Auto-generated catch block
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout ,sorry!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }

    private class InputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // All the 9*9 JTextFileds invoke this handler. We need to determine
            // which JTextField (which row and column) is the source for this invocation.
            int rowSelected = -1;
            int colSelected = -1;

            // Get the source object that fired the event
            JTextField source = (JTextField)e.getSource();
            // Scan JTextFileds for all rows and columns, and match with the source object
            boolean found = false;
            for (int row = 0; row < GRID_SIZE ; ++row) {
                for (int col = 0; col < GRID_SIZE ; ++col) {
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter("C:\\Users\\Weslley\\Documents\\sudoku.txt");
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    writer.println(tfCells[row][col].getText());
                    }
                }
            }

        }

}




    // Define the Listener Inner Class
    // ... [TODO 2] (Later) ...
//}