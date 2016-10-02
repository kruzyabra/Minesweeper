import java.awt.*;

public class Cell {

    final static int SIZE = 30;

    final String SIGN_OF_FLAG = "P";
    final int[] COLOR_OF_NUMBERS = {0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0000FF, 0x0000FF, 0x0000FF, 0x0000FF, 0x0000FF, 0x0000FF};

    private boolean isBanged;
    public static int numOfOpen;
    public int minesCounter;

    private boolean isOpen;
    private boolean isFlag;
    private boolean isMined;
    private boolean bangedMine;


    public void open(boolean isBanged) {
        isOpen = true;
        this.isBanged = isBanged;
    }

    public void mine() {
        isMined = true;
    }

    public void setMinesCounter(int minesCounter) {
        this.minesCounter = minesCounter;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setFlag() {
        isFlag = !isFlag;
    }

    public boolean isMined() {
        return isMined;
    }

    void paintMine(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * SIZE + 7, y * SIZE + 10, 18, 10);
        g.fillRect(x * SIZE + 11, y * SIZE + 6, 10, 18);
        g.fillRect(x * SIZE + 9, y * SIZE + 8, 14, 14);
        g.setColor(Color.white);
        g.fillRect(x * SIZE + 11, y * SIZE + 10, 4, 4);
    }

    void paintString(Graphics g, String str, int x, int y, Color color) {
        g.setColor(color);
        g.setFont(new Font("", Font.BOLD, SIZE));
        g.drawString(str, x * SIZE + 8, y * SIZE + 26);
    }

    void paint(Graphics g, int x, int y) {
        g.setColor(Color.lightGray);
        g.drawRect(x * SIZE, y * SIZE, SIZE, SIZE);

        if (isOpen) {
            if (isMined && !isBanged) {
                paintMine(g, x, y, Color.black);
            }
            if (isMined && isBanged) {
                paintMine(g, x, y, Color.red);
            }
            if (!isMined && minesCounter > 0) {
                paintString(g, Integer.toString(minesCounter), x, y, new Color(COLOR_OF_NUMBERS[minesCounter - 1]));
            }
        }

        if (!isOpen) {
            g.setColor(Color.lightGray);
            g.fill3DRect(x * SIZE, y * SIZE, SIZE, SIZE, true);
            if (isFlag) paintString(g, SIGN_OF_FLAG, x, y, Color.red);
        }
    }
}
