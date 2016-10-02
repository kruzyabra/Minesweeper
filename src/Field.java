import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Field extends JFrame{

    final String TITLE_OF_PROGRAM = "MinesWeeper";
    final int FIELD_SIZE = 9;
    final int FIELD_DX = 6;
    final int FIELD_DY = 28 + 17;
    final int START_LOCATION = 200;

    final int MOUSE_BUTTON_LEFT = 1; // for mouse listener
    final int MOUSE_BUTTON_RIGHT = 3;
    final int NUMBER_OF_MINES = 10;


    public Cell[][] gameField = new Cell[FIELD_SIZE][FIELD_SIZE];
    public boolean isBanged;
    public boolean isWon;

    Field() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, FIELD_SIZE * Cell.SIZE + FIELD_DX, FIELD_SIZE * Cell.SIZE + FIELD_DY);
        setResizable(false);

        initField();
        createCanvas();
        setVisible(true);
    }

    public void createCanvas() {
        final Canvas canvas = new Canvas(gameField);
        canvas.setBackground(Color.white);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                int x = e.getX() / Cell.SIZE;
                int y = e.getY() / Cell.SIZE;

                if (e.getButton() == MOUSE_BUTTON_LEFT ){  //  && !isBanged && !isWon
                    if (!gameField[x][y].isOpen()) {
                        openCells(x, y);
                        isWon = Cell.numOfOpen == FIELD_SIZE * FIELD_SIZE - NUMBER_OF_MINES;
                    }
                }

                if (e.getButton()== MOUSE_BUTTON_RIGHT) gameField[x][y].setFlag();
                canvas.repaint();
            }
        });

        add(BorderLayout.CENTER, canvas);
    }

    /**
     * Метод открывает клетку, на которую щёлкнул игрок.
     * @param x
     * @param y
     */

    private void openCells(int x, int y) {

            if (x < 0 || x > FIELD_SIZE - 1 || y < 0 || y > FIELD_SIZE - 1) return; // Неверные координаты клетки
            if (gameField[x][y].isOpen()) return; // Клетка уже открыта

            if (gameField[x][y].isMined()) {
//                isBanged = true;
                gameField[x][y].open(true);
                openAllCells();
                return;
            }

            gameField[x][y].open(false);

            if (!gameField[x][y].isMined() && gameField[x][y].minesCounter == 0) {
                for (int dx = -1; dx < 2; dx++)
                    for (int dy = -1; dy < 2; dy++)
                        openCells(x + dx, y + dy);
            }

    }

    /**
     *  Метод открывает все клетки поля
     *
     */

    private void openAllCells() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                gameField[i][j].open(false);
            }
        }
    }

    private void initField() {
        isBanged = false;
        isWon = false;
        createCells();
        createMines();
        countMines();
    }

    /**
     * Создаются пустые клетки поля.
     */

    private void createCells() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                gameField[i][j] = new Cell();
            }
        }
    }

    /**
     * Создаются мины с рандомными координатами.
     */

    private void createMines() {
        Random random = new Random();
        int x, y, minesCounter = 0;

        while (minesCounter < NUMBER_OF_MINES) {
            do {
                x = random.nextInt(FIELD_SIZE);
                y = random.nextInt(FIELD_SIZE);
            } while (gameField[x][y].isMined());
            gameField[x][y].mine();
            minesCounter++;
        }
    }

    /**
     * Для каждой клетки, в которой нет мины, считается количество мин в смежных клетках.
     */

    private void countMines() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (!gameField[i][j].isMined()) {
                    int minesCounter = 0;

                    for (int dx = -1; dx < 2; dx++)
                        for (int dy = -1; dy < 2; dy++) {
                            int nX = i + dx;
                            int nY = j + dy;
                            if (!(nX < 0 || nY < 0 || nX > FIELD_SIZE - 1 || nY > FIELD_SIZE - 1))
                                minesCounter += (gameField[nX][nY].isMined()) ? 1 : 0;
                        }

                    gameField[i][j].setMinesCounter(minesCounter);
                }
            }
        }
    }
}
