import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by e_voe_000 on 9/2/2016.
 */
public class Main {

    private int[] sortedNumbers1;  //change these two to equal the tested number
    private int threshold;

    private int[] list1;
    private int[] list2;

    private void bubbleSort(int[] numbers) {
        boolean flag = true;
        int temp;

        while (flag) {
            flag = false; //set flag to false awaiting a possible swap
            for (int j = 0; j < numbers.length - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {

                    temp = numbers[j];      //swap elements
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                    flag = true;      //shows a swap occurred
                }
            }
        }
    }

    private void addNumbersToList() {
        for (int i = 0; i < sortedNumbers1.length; i++) {
            int j = RandomInt(sortedNumbers1.length) + 1;
            sortedNumbers1[i] = j;
        }

        for (int i = 0; i < sortedNumbers1.length; i++) {
            if (i < sortedNumbers1.length / 2) {
                list1[i] = sortedNumbers1[i];
            } else {
                list2[i - sortedNumbers1.length / 2] = sortedNumbers1[i];
            }

        }
    }



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

    private int[] usingThread(int[] array) {
        //System.out.println("BEGIN: " + Arrays.toString(array));
        if (array.length > threshold) {
            int[] array1 = Arrays.copyOfRange(array, 0, array.length / 2);
            int[] array2 = Arrays.copyOfRange(array, array1.length, array.length);


            //Thread t1 = new Thread(() -> usingThread(array1));
            Sorter sorter1 = new Sorter(array1);
            Sorter sorter2 = new Sorter(array2);
            Thread t1 = new Thread(sorter1);
            Thread t2 = new Thread(sorter2);
            //Thread t2 = new Thread(() -> usingThread(array2));

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException ignored) {
            }

            array = merge(sorter1.array, sorter2.array);
//            return array;

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

        int[] arrayLength = new int[] {25000, 50000, 100000, 200000, 400000, 800000};
        int[] thresholds = new int[] {500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000};

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
