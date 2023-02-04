package src;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * @author aniketchoudhary
 * MergeSort Algorithm demonstrated using GUI bar graphs
 * Time Complexity O(N*log(base2)(N))
 */

public class MergeSort extends Frame {
    static int[] arr = new int[45];
    static int count = 0;

    // GUI code starts
    public MergeSort() {
        super("Mergesort");
        prepareGUI();
    }

    private void prepareGUI() {
        setSize(2000, 900);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2;
        int xCoordinate = 20;
        for (int k : arr) {
            g2 = (Graphics2D) g;
            g2.drawRect(xCoordinate, 700 - (60 + k), 20, 60 + k);
            g2.drawString(String.valueOf(k), xCoordinate, 635 - k);
            g2.fillRect(xCoordinate, 700 - (60 + k), 20, 60 + k);
            g.drawString("MergeSort Demonstration Trial:   " + (MergeSort.count), 200, 50);
            xCoordinate = xCoordinate + 30;
        }
    }
    // GUI code ends

    /*
    Recursive calls are made to the sort method
    1. Sorts the left half
    2. Sort the right half
    3. Merge the two sorted halves
    Note: We are not initializing auxiliary array in this method
          to save time.
    * */
    public static void sort(int start, int end, int[] arr) throws InterruptedException {
        if (end <= start) {
            return;
        }
        int mid = (start + end) / 2;
        sort(start, mid, arr);
        sort(mid + 1, end, arr);
        merge(start, end, arr);
        // Below call is made to render the current Array contents in the GUI
        MergeSort mergeSortDemo = new MergeSort();
        mergeSortDemo.setVisible(true);
        // without the delay you will not be able to see the
        // sorting play out. The Algorithm is so fast ;)
        Thread.sleep(250);
    }

    public static void merge(int start, int end, int[] arr) {
        int size = end - start + 1;
        int[] auxArr = new int[size];
        int auxMid = size / 2;
        int j = 0;
        if (size % 2 != 0) {
            auxMid++;
        }
        int k = auxMid;
        int i;
        for (i = 0; i < size; i++) {
            auxArr[i] = arr[start + i];
        }
        i = start;
        while (j < auxMid || k < size) {
            if (j >= auxMid) {
                arr[i] = auxArr[k];
                k++;
            } else if (k < size && auxArr[k] < auxArr[j]) {
                arr[i] = auxArr[k];
                k++;
            } else {
                arr[i] = auxArr[j];
                j++;
            }
            i++;
        }
        count++;
    }

    public static void main(String[] args) throws InterruptedException {

        Random r = new Random();
        arr = r.ints(45, 0, 500).toArray();
        sort(0, arr.length - 1, arr);
    }
}
