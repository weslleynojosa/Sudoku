package jogo;

import java.io.FileNotFoundException;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import javax.swing.*;

import static java.lang.System.*;

/**
 * The Sudoku game.
 * To solve the number puzzle, each row, each column, and each of the
 * nine 3×3 sub-grids shall contain all of the digits from 1 to 9
 */
class Sudoku extends JFrame implements ActionListener {
    private static final int GRID_SIZE = 9;
    private static final Color OPEN_CELL_BGCOLOR = Color.WHITE;
    private static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    private static JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];

    /**
     * Constructor to setup the game and the UI Components
     */
    Sudoku() {
        JPanel sudokuMain = new JPanel(new GridLayout(1,2));
        JPanel sudokuField = new JPanel();
        sudokuField.setLayout(new GridLayout(9,9));

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                tfCells[row][col] = new JTextField();
                sudokuField.add(tfCells[row][col]);
                tfCells[row][col].setText("");
                tfCells[row][col].setEditable(true);
                tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                tfCells[row][col].setFont(FONT_NUMBERS);
            }
        }

        sudokuMain.add(sudokuField);

        JButton resolverButton = new JButton("Resolver");
        resolverButton.setPreferredSize(new Dimension(100,30));
        Box box = Box.createVerticalBox();
        box.add(resolverButton);
        box.add(Box.createVerticalStrut(10));
        resolverButton.addActionListener(this);

        JButton clear = new JButton("Limpar");
        clear.setPreferredSize(new Dimension(100,30));
        box.add(clear);

        clear.addActionListener(e -> {
            for (int row = 0; row < GRID_SIZE; ++row) {
                for (int col = 0; col < GRID_SIZE; ++col) {
                    tfCells[row][col].setText("");
                }
            }
        });

        JPanel buttonField = new JPanel();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            PrintWriter writer = new PrintWriter("C:\\Users\\Weslley\\Documents\\sudoku.txt");
            writer.println("c FILE: sudoku.txt");
            writer.println("c");
            writer.println("c SOURCE: Amauri Aires B. Filho and Weslley Nojosa Costa.");

            // Lines constraints
            for(int row = 1; row <= 9; row ++){
                for(int n = 1; n <= 9; n++){
                    for (int col = 1; col <= 9; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                    writer.println("0");
                }
            }

            // Columns constraints
            for(int row = 1; row <= 9; row ++){
                for(int n = 1; n <= 9; n++){
                    for (int col = 1; col <= 9; col ++) {
                        writer.print(n);
                        writer.print(col);
                        writer.print(row);
                        writer.print(" ");
                    }
                    writer.println("0");
                }
            }

            // Cells constraints
            for (int N = 1; N <=9; N++) {
                for(int row = 1; row <= 9; row ++){
                    for(int col = 1; col <= 9; col++){
                        for (int n = 1; n <= 9; n++) {
                            if(N != n){
                                writer.print(-N);
                                writer.print(row);
                                writer.print(col);
                                writer.print(" ");
                                writer.print(-n);
                                writer.print(row);
                                writer.print(col);
                                writer.print(" ");
                                writer.println("0");
                            }
                        }
                    }
                }
            }

            // Subgrid constraints
            for(int n = 1; n <= 9; n ++){
                for(int row = 1; row <= 3; row++){
                    for (int col = 1; col <= 3; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }
            for(int n = 1; n <= 9; n ++){ // 41
                for(int row = 4; row <= 6; row++){
                    for (int col = 1; col <= 3; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }
            for(int n = 1; n <= 9; n ++){ // 71
                for(int row = 7; row <= 9; row++){
                    for (int col = 1; col <= 3; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }

            for(int n = 1; n <= 9; n ++){ //14
                for(int row = 1; row <= 3; row++){
                    for (int col = 4; col <= 6; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }
            for(int n = 1; n <= 9; n ++){ //44
                for(int row = 4; row <= 6; row++){
                    for (int col = 4; col <= 6; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }
            for(int n = 1; n <= 9; n ++){ // 74
                for(int row = 7; row <= 9; row++){
                    for (int col = 4; col <= 6; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }

            for(int n = 1; n <= 9; n ++){ //17
                for(int row = 1; row <= 3; row++){
                    for (int col = 7; col <= 9; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }
            for(int n = 1; n <= 9; n ++){ //47
                for(int row = 4; row <= 6; row++){
                    for (int col = 7; col <= 9; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }
            for(int n = 1; n <= 9; n ++){ // 77
                for(int row = 7; row <= 9; row++){
                    for (int col = 7; col <= 9; col++) {
                        writer.print(n);
                        writer.print(row);
                        writer.print(col);
                        writer.print(" ");
                    }
                }
                writer.println("0");
            }

            for (int row = 0; row < GRID_SIZE; ++row) {
                for (int col = 0; col < GRID_SIZE; ++col) {
                    if (!tfCells[row][col].getText().isEmpty()) {
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
            out.println("Error writing to file!");
        }
    }
}