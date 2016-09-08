import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by e_voe_000 on 9/2/2016.
 */
public class Main {

    private int[] sortedNumbers1 = new int[12];  //change these two to equal the tested number
    private int threshold = 2;

    private int[] list1 = Arrays.copyOfRange(sortedNumbers1, 0, sortedNumbers1.length / 2);
    private int[] list2 = Arrays.copyOfRange(sortedNumbers1, list1.length, sortedNumbers1.length);

    private void bubbleSort(int[] numbers) {
        boolean flag = true;
        int temp;

        while (flag) {
            flag = false; //set flag to false awaiting a possible swap
            for (int j = 0; j < numbers.length - 1; j++) {
                if (numbers[j] < numbers[j + 1]) {

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

        bubbleSort(answer);

        return answer;
    }

    private int[] usingThread(int[] array) {
        int[] array1 = Arrays.copyOfRange(array, 0, array.length / 2);
        int[] array2 = Arrays.copyOfRange(array, array1.length, array.length);

        if (array.length < threshold) {

            Thread t1 = new Thread(() -> bubbleSort(array1));

            Thread t2 = new Thread(() -> bubbleSort(array2));

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException ignored) {
            }

            array = merge(array1, array2);
            return array;

        } else {
            usingThread(array1);
            usingThread(array2);

        }
        return array;
    }

    private int RandomInt(int amount) {
        return (int) (Math.random() * amount);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.addNumbersToList();

        final long startTime = System.currentTimeMillis();      //start timer

        Thread thread1 = new Thread(() -> main.list1 = main.usingThread(main.list1));

        Thread thread2 = new Thread(() -> main.list2 = main.usingThread(main.list2));

        thread1.start();
        thread2.start();



        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException i) {
        }

        int[] mergedList = main.merge(main.list1, main.list2);

        final long endTime = System.currentTimeMillis();        //end timer

        for (int sorted : mergedList) {
            System.out.println(sorted);
        }
        System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
        System.out.println("Tested length: " + mergedList.length);

    }
}
