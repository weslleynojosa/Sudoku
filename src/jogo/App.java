package jogo;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
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
}
