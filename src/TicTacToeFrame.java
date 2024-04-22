import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Scanner;

public class TicTacToeFrame extends JFrame {
    JPanel mainPanel;
    JPanel buttonPanel;

    JOptionPane optionPane;

    TicTacToeTile[][] displayBoard = new TicTacToeTile[3][3];

    private static final int ROW = 3;
    private static final int COL = 3;
    private static final String[][] board = new String[ROW][COL];

    JButton quitButton;

    boolean finish = false;
    boolean playing = true;

    String player = "X";
    int move = 0;
    int row = -1;
    int col = -1;
    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(750, 750);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent e) -> {System.exit(0);});
        quitButton.setFont(new Font("Arial", Font.PLAIN, 24));

        createButtonPanel();
        initializeBoard();

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(quitButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), ""));

        for (int r = 0; r < 3; r++) {
            for(int c = 0; c < 3; c++) {
                displayBoard[r][c] = new TicTacToeTile(r, c);
                displayBoard[r][c].setText(" ");
                displayBoard[r][c].setFont(new Font("Arial", Font.BOLD, 48));

                buttonPanel.add(displayBoard[r][c]);

                displayBoard[r][c].addActionListener((ActionEvent ae) -> {
                   TicTacToeTile tile = (TicTacToeTile) ae.getSource();
                   row = tile.getRow();
                   col = tile.getCol();

                   displayBoard[row][col].setEnabled(false);
                   displayBoard[row][col].setBackground(new Color(255,255,255));
                   board[row][col] = player;
                   displayBoard[row][col].setText(player);
                   move++;
                   System.out.println(move);

                   if(move >= MOVES_FOR_WIN) {
                       if(isWin(player)) {
                           disableButtons();
                           int result = JOptionPane.showConfirmDialog(optionPane, "Do you want to play again?", "Player " + player + " Wins!", JOptionPane.YES_NO_OPTION);

                           if(result == JOptionPane.YES_OPTION) {
                               initializeBoard();
                           } else if (result == JOptionPane.NO_OPTION) {
                               System.exit(0);
                           }
                       }
                   }

                   if (move >= MOVES_FOR_TIE) {
                       if(isTie()) {
                           disableButtons();
                           int result = JOptionPane.showConfirmDialog(optionPane, "Do you want to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);

                           if (result == JOptionPane.YES_OPTION) {
                               initializeBoard();
                           } else if (result == JOptionPane.NO_OPTION) {
                               System.exit(0);
                           }
                       }
                   }

                   if(player.equals("X")) {
                       player = "O";
                   } else {
                       player = "X";
                   }
                });
            }
        }
    }

    private static boolean isWin(String player) {
        if(isColWin(player) || isRowWin(player) || isDiagonalWin(player)) {
            return true;
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for(int col = 0; col < COL; col++) {
            if(board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRowWin(String player) {
        for(int row = 0; row < ROW; row++) {
            if(board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        if(board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }

        if(board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    private static boolean isTie() {
        boolean x = false;
        boolean o = false;

        for(int row = 0; row < ROW; row++) {
            if (board[row][0].equals("X") || board[row][1].equals("X") || board[row][2].equals("X")) {
                x = true;
            }

            if (board[row][0].equals("O") || board[row][1].equals("O") || board[row][2].equals("O")) {
                o = true;
            }

            if (!(x && o)) {
                return false;
            }
        }

        for(int col = 0; col < COL; col++) {
            if(board[0][col].equals("X") || board[1][col].equals("X") || board[2][col].equals("X")) {
                x = true;
            }

            if(board[0][col].equals("O") || board[1][col].equals("O") || board[2][col].equals("O")) {
                o = true;
            }

            if(!(x && o)) {
                return false;
            }
        }

        x = o = false;

        if(board[0][0].equals("X") || board[1][1].equals("X") || board[2][2].equals("X")) {
            x = true;
        }

        if(board[0][0].equals("O") || board[1][1].equals("O") || board[2][2].equals("O")) {
            o = true;
        }

        if (! (x && o)) {
            return false;
        }

        x = o = false;

        if(board[0][2].equals("X") || board[1][1].equals("X") || board[2][0].equals("X")) {
            x = true;
        }

        if(board[0][2].equals("O") || board[1][1].equals("O") || board[2][0].equals("O")) {
            o = true;
        }

        if(!(x && o)) {
            return false;
        }
        return true;
    }

    public void initializeBoard() {
        player = "X";
        playing = true;
        move = 0;
        clearBoard();
    }

    private void clearBoard() {
        for(int row = 0; row < ROW; row++) {
            for(int col = 0; col < COL; col++) {
                board[row][col] = " ";
            }
        }

        for (int row = 0; row < ROW; row++) {
            for(int col = 0; col < COL; col++) {
                displayBoard[row][col].setText(" ");
                displayBoard[row][col].setEnabled(true);
                displayBoard[row][col].setBackground(new Color(185, 245, 171));
            }
        }
    }

    private void disableButtons() {
        for(int row = 0; row < ROW; row++) {
            for(int col = 0; col < COL; col++) {
                displayBoard[row][col].setEnabled(false);
                displayBoard[row][col].setBackground(new Color(247, 187, 178));
            }
        }
    }
}
