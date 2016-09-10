import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by e_voe_000 on 9/2/2016.
 */
public class Main {

    private int[] sortedNumbers1;
    private int threshold;

    private int[] list1;
    private int[] list2;

    //The bubble sort algorithm
    private void bubbleSort(int[] numbers) {
        boolean flag = true;
        int temp;

        while (flag) {
            flag = false; //Set flag to false awaiting a possible swap
            for (int j = 0; j < numbers.length - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {

                    temp = numbers[j];      //Swap elements
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                    flag = true;      //Shows a swap occurred
                }
            }
        }
    }

    //Add random numbers to the list that has to be sorted
    private void addNumbersToList() {
        //add the generated numbers to the list
        for (int i = 0; i < sortedNumbers1.length; i++) {
            int j = RandomInt(sortedNumbers1.length) + 1;
            sortedNumbers1[i] = j;
        }

        //Split the list in to halves
        for (int i = 0; i < sortedNumbers1.length; i++) {
            if (i < sortedNumbers1.length / 2) {
                list1[i] = sortedNumbers1[i];
            } else {
                list2[i - sortedNumbers1.length / 2] = sortedNumbers1[i];
            }
        }
    }


    //Merge sort algorithm
    private int[] merge(int[] a, int[] b) {
        int[] answer = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i] < b[j])
                answer[k++] = a[i++];

            else
                answer[k++] = b[j++];
        }

        while (i < a.length)
            answer[k++] = a[i++];


        while (j < b.length)
            answer[k++] = b[j++];

        return answer;
    }


    private class Sorter implements Runnable {
        private int[] array;

        Sorter(int[] array) {
            this.array = array;
        }

        @Override
        public void run() {
            array = usingThread(array);
        }
    }

    //if the array length is bigger than the threshold
    //split the array in to two halves and create two new threads for the two new arrays
    //else bubblesort the array
    private int[] usingThread(int[] array) {
        //System.out.println("BEGIN: " + Arrays.toString(array));
        if (array.length > threshold) {
            int[] array1 = Arrays.copyOfRange(array, 0, array.length / 2);
            int[] array2 = Arrays.copyOfRange(array, array1.length, array.length);

            Sorter sorter1 = new Sorter(array1);
            Sorter sorter2 = new Sorter(array2);
            Thread t1 = new Thread(sorter1);
            Thread t2 = new Thread(sorter2);

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException ignored) {
            }

            array = merge(sorter1.array, sorter2.array);

        } else {
            bubbleSort(array);
        }
        //System.out.println("END: " + Arrays.toString(array));
        return array;
    }

    private int RandomInt(int amount) {
        return (int) (Math.random() * amount);
    }

    public static void main(String[] args) {
        Main main = new Main();

        //All the value to be tested

        //The length of the unsorted array
        int[] arrayLength = new int[]{25000, 50000, 100000, 200000, 400000, 800000};
        //The thresholds
        int[] thresholds = new int[]{500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000};

        for (int array : arrayLength) {
            main.sortedNumbers1 = new int[array];

            main.list1 = Arrays.copyOfRange(main.sortedNumbers1, 0, main.sortedNumbers1.length / 2);
            main.list2 = Arrays.copyOfRange(main.sortedNumbers1, main.list1.length, main.sortedNumbers1.length);

            main.addNumbersToList();

            for (int holdNumber : thresholds) {
                main.threshold = holdNumber;

                final long startTime = System.currentTimeMillis();      //start timer

                Thread thread1 = new Thread(() -> main.list1 = main.usingThread(main.list1));

                Thread thread2 = new Thread(() -> main.list2 = main.usingThread(main.list2));

                thread1.start();
                thread2.start();

                try {
                    thread1.join();
                    thread2.join();
                } catch (InterruptedException ignored) {
                }

                int[] mergedList = main.merge(main.list1, main.list2);

                final long endTime = System.currentTimeMillis();        //end timer

                System.out.println("threshold: " + main.threshold);
                System.out.println("length: " + mergedList.length);
                System.out.println("time: " + (endTime - startTime) + " milliseconds");
                System.out.println("\n");
            }

        }
    }
}
