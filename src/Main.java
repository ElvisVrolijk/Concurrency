/**
 * Created by e_voe_000 on 9/2/2016.
 */
public class Main {

    private int[] sortedNumbers1 = new int[50000];  //change these two to equal the tested number
    private int[] sortedNumbers2 = new int[50000];

    //we have added the key word synchronized to this method so that when wen call it in a
    //thread the one thread will know to finish the method before switching to another thread!
    private synchronized void bubbleSort(int[] numbers) {
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

        for (int i = 0; i < sortedNumbers2.length; i++) {
            int j = RandomInt(sortedNumbers2.length) + 1;
            sortedNumbers2[i] = j;
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

    private void usingThread() {

        final long startTime = System.currentTimeMillis();      //start timer

        Thread t1 = new Thread(() -> bubbleSort(sortedNumbers1));

        Thread t2 = new Thread(() -> bubbleSort(sortedNumbers2));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {
        }

        int[] mergedList = merge(sortedNumbers1, sortedNumbers2);

        final long endTime = System.currentTimeMillis();        //end timer

        for (int sortedNumber : mergedList) {
            System.out.println(sortedNumber);
        }

        System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
        System.out.println("Tested length: " + mergedList.length);
    }

    private int RandomInt(int amount) {
        return (int) (Math.random() * amount);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.addNumbersToList();
        main.usingThread();
    }
}
