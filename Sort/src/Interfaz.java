import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class Interfaz extends JFrame {
    Semaphore timerSemaphore;
    int[] arreglo;
    Random random;
    JLabel resultDisp;
    JScrollPane resultScroll;
    JLabel arrayDisp;
    JScrollPane arrayScroll;
    JButton setArray;
    JButton mergeSort;
    JButton forkJoin;
    JButton executorService;
    JButton limpiar;
    JLabel mergeTime;
    JLabel forkTime;
    JLabel executorTime;
    JLabel labelForRes;
    JLabel labelForArray;
    Font font;
    Runnable timerTask;
    int time;
    boolean timerRunnig;

    Interfaz() {
        setSize(800, 400);
        initComponents();
        timerSemaphore = new Semaphore(1,true);
        timerTask = () -> {
            while (true) {
                try {
                    Thread.sleep(1);
                    time += 1;
                    timerSemaphore.acquire();
                    if(!timerRunnig){
                        timerSemaphore.release();
                        break;
                    }
                    timerSemaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(3);
        setLayout(null);
        random = new Random();
        font = new Font("Agency FB", Font.PLAIN, 24);

        labelForRes = new JLabel("Resultado");
        labelForRes.setFont(font);
        labelForRes.setBounds(50, 25, 675, 20);
        add(labelForRes);


        resultDisp = new JLabel("[]");
        resultDisp.setFont(font);
        resultDisp.setForeground(Color.BLACK);
        resultScroll = new JScrollPane(resultDisp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultScroll.setBounds(50, 50, 675, 50);
        add(resultScroll);

        labelForArray = new JLabel("Arreglo");
        labelForArray.setFont(font);
        labelForArray.setBounds(50, 120, 675, 30);
        add(labelForArray);


        arrayDisp = new JLabel("[]");
        arrayDisp.setFont(font);
        arrayDisp.setForeground(Color.BLACK);
        arrayScroll = new JScrollPane(arrayDisp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        arrayScroll.setBounds(50, 150, 675, 50);
        add(arrayScroll);

        setArray = new JButton("Poner Arreglo");
        setArray.setBounds(50, 220, 200, 30);
        add(setArray);
        setArray.addActionListener(e -> {
            int length;
            try {
                length = Integer.parseInt(JOptionPane.showInputDialog("Longitud del arreglo"));
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(this, "Verifica el valor ingresado");
                return;
            }
            if (length <= 0) {
                JOptionPane.showMessageDialog(this, "Verifica el valor ingresado");
                return;
            }
            arreglo = new int[length];
            for (int i = 0; i < arreglo.length; i++) {
                arreglo[i] = random.nextInt(1, Integer.MAX_VALUE);
            }
            mergeTime.setText("0 ms");
            forkTime.setText("0 ms");
            executorTime.setText("0 ms");
            resultDisp.setText("");
            arrayDisp.setText(Arrays.toString(arreglo).replaceAll(" ", ""));
        });

        limpiar = new JButton("Limpiar");
        limpiar.setBounds(270, 220, 200, 30);
        add(limpiar);
        limpiar.addActionListener(e -> {
            arreglo = new int[0];
            arrayDisp.setText("");
            resultDisp.setText("");
            mergeTime.setText("0 ms");
            forkTime.setText("0 ms");
            executorTime.setText("0 ms");
        });

        mergeSort = new JButton("Merge Sort");
        mergeSort.setBounds(50, 260, 200, 30);
        add(mergeSort);
        mergeSort.addActionListener(e -> {
            time = 0;
            Thread timer = new Thread(timerTask);
            timerRunnig = true;
            timer.start();
            int[] res = mergeSort(arreglo);

            try{
                timerSemaphore.acquire();
                timerRunnig = false;
                timerSemaphore.release();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            mergeTime.setText(time + " ms");
            resultDisp.setText(Arrays.toString(res).replaceAll(" ", ""));
        });

        forkJoin = new JButton("Fork Join");
        forkJoin.setBounds(270, 260, 200, 30);
        add(forkJoin);
        forkJoin.addActionListener(e -> {
            time = 0;
            Thread timer = new Thread(timerTask);
            timerRunnig = true;
            timer.start();
            ForkJoinMerge task = new ForkJoinMerge(arreglo);
            ForkJoinPool pool = new ForkJoinPool();
            Future<int[]> result = pool.submit(task);
            int[] res;
            try {
                res = result.get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
            try{
                timerSemaphore.acquire();
                timerRunnig = false;
                timerSemaphore.release();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            forkTime.setText(time + " ms");
            resultDisp.setText(Arrays.toString(res).replaceAll(" ", ""));
        });

        executorService = new JButton("Executor Service");
        executorService.setBounds(490, 260, 200, 30);
        add(executorService);
        executorService.addActionListener(e -> {
            time = 0;
            Thread timer = new Thread(timerTask);
            timerRunnig = true;
            timer.start();
            int[] res;
            try {
                ExecutorService executor = Executors.newCachedThreadPool();
                Future<int[]> future = executor.submit(new ExecutorMerge(arreglo, executor, 0));
                res = future.get();
                executor.shutdown();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
            try{
                timerSemaphore.acquire();
                timerRunnig = false;
                timerSemaphore.release();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            executorTime.setText(time + " ms");
            resultDisp.setText(Arrays.toString(res).replaceAll(" ", ""));
        });

        mergeTime = new JLabel("0 ms");
        mergeTime.setFont(font);
        mergeTime.setBounds(50, 290, 200, 30);
        add(mergeTime);

        forkTime = new JLabel("0 ms");
        forkTime.setFont(font);
        forkTime.setBounds(270, 290, 200, 30);
        add(forkTime);

        executorTime = new JLabel("0 ms");
        executorTime.setFont(font);
        executorTime.setBounds(490, 290, 200, 30);
        add(executorTime);
    }

    public static int[] mergeSort(int[] arreglo) {
        if (arreglo.length == 1) return arreglo;
        int centro = (arreglo.length / 2);
        int[] izq = new int[centro];
        int[] der = new int[arreglo.length - centro];
        int izqIndex = 0;
        int derIndex = 0;
        for (int i = 0; i < arreglo.length; i++) {
            if (i < centro) {
                izq[izqIndex] = arreglo[i];
                izqIndex++;
            } else {
                der[derIndex] = arreglo[i];
                derIndex++;
            }
        }
        izq = mergeSort(izq);
        der = mergeSort(der);

        return merge(izq, der);
    }

    public static int[] merge(int[] izq, int[] der) {
        int[] res = new int[izq.length + der.length];
        int resIndex = 0;
        int izqIndex = 0;
        int derIndex = 0;
        while (izqIndex < izq.length && derIndex < der.length) {
            if (izq[izqIndex] <= der[derIndex]) {
                res[resIndex] = izq[izqIndex];
                izqIndex++;
            } else {
                res[resIndex] = der[derIndex];
                derIndex++;
            }
            resIndex++;
        }
        while (izqIndex < izq.length) {
            res[resIndex] = izq[izqIndex];
            izqIndex++;
            resIndex++;
        }
        while (derIndex < der.length) {
            res[resIndex] = der[derIndex];
            derIndex++;
            resIndex++;
        }
        return res;
    }
}
