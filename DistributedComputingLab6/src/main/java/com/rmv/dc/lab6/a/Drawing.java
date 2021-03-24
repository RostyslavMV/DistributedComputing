package com.rmv.dc.lab6.a;

import javax.swing.*;
import java.awt.*;

public class Drawing extends JPanel implements Runnable {
    private final int cell_size;
    Integer[][] field;
    Buffer buffer;
    private final Color[] colors = { Color.BLACK, Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN };

    Drawing(Buffer buffer, int cell_size) {
        this.cell_size = cell_size;
        this.buffer = buffer;
    }

    @Override
    public void paint(Graphics g) {
        if (field == null)
            return;
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[0].length; ++j) {
                int index = i / Manager.TASK_SIZE + 1;
                if (index > Manager.THREAD_NUM)
                    index = Manager.THREAD_NUM;
                Color color = field[i][j] != 0 ? colors[index] : colors[0];
                g.setColor(color);
                g.fillRect(j * cell_size, i * cell_size, (j + 1) * cell_size, (i + 1) * cell_size);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            field = buffer.getFromPrimary();
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
