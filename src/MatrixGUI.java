import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixGUI extends JFrame {
    private JTextField dimensionField;
    private JTextArea matrixArea;
    private JLabel resultLabel;

    public MatrixGUI() {
        setTitle("Matrix Manipulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel dimensionLabel = new JLabel("Enter matrix dimension (n):");
        dimensionField = new JTextField(5);

        JButton loadButton = new JButton("Load Matrix from File");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMatrixFromFile();
            }
        });

        inputPanel.add(dimensionLabel);
        inputPanel.add(dimensionField);
        inputPanel.add(loadButton);

        matrixArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(matrixArea);

        resultLabel = new JLabel();

        JButton processButton = new JButton("Process Matrix");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processMatrix();
            }
        });

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);
        mainPanel.add(processButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadMatrixFromFile() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                Scanner scanner = new Scanner(selectedFile);

                StringBuilder matrixText = new StringBuilder();
                while (scanner.hasNextLine()) {
                    matrixText.append(scanner.nextLine()).append("\n");
                }

                matrixArea.setText(matrixText.toString());
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processMatrix() {
        try {
            int n = Integer.parseInt(dimensionField.getText());
            Scanner scanner = new Scanner(matrixArea.getText());

            int[][] matrix = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }

            // Example matrix processing logic: reversing rows and columns
            reverseRowsAndColumns(matrix);

            // Update the matrixArea with the processed matrix
            updateMatrixArea(matrix);

            resultLabel.setText("Matrix processed successfully.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid dimension format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred during matrix processing.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Example method to reverse rows and columns of the matrix
    private void reverseRowsAndColumns(int[][] matrix) {
        for (int i = 0; i < matrix.length / 2; i++) {
            int[] tempRow = matrix[i];
            matrix[i] = matrix[matrix.length - 1 - i];
            matrix[matrix.length - 1 - i] = tempRow;

            for (int j = 0; j < matrix[i].length / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix[i].length - 1 - j];
                matrix[i][matrix[i].length - 1 - j] = temp;
            }
        }
    }

    // Example method to update the matrixArea with the processed matrix
    private void updateMatrixArea(int[][] matrix) {
        StringBuilder matrixText = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrixText.append(matrix[i][j]).append(" ");
            }
            matrixText.append("\n");
        }
        matrixArea.setText(matrixText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MatrixGUI().setVisible(true);
            }
        });
    }
}
