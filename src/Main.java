/**
 * Created by e_voe_000 on 9/2/2016.
 */
public class Main {

    private int[] sortedNumbers1 = new int[50000];  //change these two to equal the tested number
    private int[] sortedNumbers2 = new int[50000];
    private int[] mergedList;

    private void BubbleSort(int[] numbers) {
        boolean flag = true;
        int temp;

        while (flag) {
            flag = false; //set flag to false awaiting a possible swap
            for (int j = 0; j < sortedNumbers1.length - 1; j++) {
                if (sortedNumbers1[j] < sortedNumbers1[j + 1]) {

                    temp = sortedNumbers1[j];      //swap elements
                    sortedNumbers1[j] = sortedNumbers1[j + 1];
                    sortedNumbers1[j + 1] = temp;
                    flag = true;      //shows a swap occurred
                }
            }
        }
    }

    private void AddNumbersToList() {
        for (int i = 0; i < sortedNumbers1.length; i++) {
            int j = RandomInt(sortedNumbers1.length) + 1;
            sortedNumbers1[i] = j;
        }

        for (int i = 0; i < sortedNumbers2.length; i++) {
            int j = RandomInt(sortedNumbers2.length) + 1;
            sortedNumbers2[i] = j;
        }

        final long startTime = System.currentTimeMillis();      //start timer

        Thread t1 = new Thread(() -> BubbleSort(sortedNumbers1));

        Thread t2 = new Thread(() -> BubbleSort(sortedNumbers2));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }

        mergedList = Merge(sortedNumbers1, sortedNumbers2); //merge the two threads

        final long endTime = System.currentTimeMillis();        //end timer

        for (int sortedNumber : mergedList) {
            System.out.println(sortedNumber);
        }

        System.out.println("Total execution time: " + (endTime - startTime) + " miliseconds");
        System.out.println("Tested length: " + mergedList.length);
    }

    public static int[] Merge(int[] a, int[] b) {

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

    private int RandomInt(int amount) {
        return (int) (Math.random() * amount);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.AddNumbersToList();
    }
}
