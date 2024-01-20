
package modernsudokusolver;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ModernSudokuSolver extends Application {

    private TextField[][] gridFields;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Modern Sudoku Solver");

        BorderPane root = new BorderPane();
        root.setCenter(createSudokuGrid());

        Button solveButton = new Button("Solve Sudoku");
        solveButton.setOnAction(e -> solveSudoku());

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(solveButton);

        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createSudokuGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        gridFields = new TextField[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField field = new TextField();
                field.setPrefSize(50, 50);
                field.setAlignment(Pos.CENTER);
                field.setStyle("-fx-font-size: 20;");
                gridFields[row][col] = field;
                gridPane.add(field, col, row);
            }
        }

        return gridPane;
    }

    private void solveSudoku() {
        int[][] unsolvedSudoku = getGridValues();

        if (solveSudoku(unsolvedSudoku)) {
            setGridValues(unsolvedSudoku);
        } else {
            
            System.out.println("No solution exists.");
        }
    }

    private int[][] getGridValues() {
        int[][] gridValues = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = gridFields[row][col].getText();
                gridValues[row][col] = value.isEmpty() ? 0 : Integer.parseInt(value);
            }
        }

        return gridValues;
    }

    private void setGridValues(int[][] gridValues) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                gridFields[row][col].setText(Integer.toString(gridValues[row][col]));
            }
        }
    }

    private boolean solveSudoku(int[][] grid) {
        int[] emptyCell = findEmptyCell(grid);

        if (emptyCell == null) {
            return true; 
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num;

                if (solveSudoku(grid)) {
                    return true; 
                }

                grid[row][col] = 0; 
            }
        }

        return false; 
    }

    private int[] findEmptyCell(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return null; 
    }

    private boolean isSafe(int[][] grid, int row, int col, int num) {
        return !usedInRow(grid, row, num) &&
               !usedInCol(grid, col, num) &&
               !usedInBox(grid, row - row % 3, col - col % 3, num);
    }

    private boolean usedInRow(int[][] grid, int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInCol(int[][] grid, int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInBox(int[][] grid, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (grid[row + startRow][col + startCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}
