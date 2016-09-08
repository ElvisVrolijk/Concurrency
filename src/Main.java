/**
 * Created by e_voe_000 on 9/2/2016.
 */
public class Main {

    private int[] sortedNumbers1 = new int[50000];  //change these two to equal the tested number
    private int threshold = 100;

    private int[] list1 = new int[sortedNumbers1.length / 2];
    private int[] list2 = new int[sortedNumbers1.length / 2];

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
        if (list1.length > threshold) {
            final long startTime = System.currentTimeMillis();      //start timer

            for (int i = 0; i < list1.length; i++) {
                list1[i] = sortedNumbers1[i];
            }
            for (int i = (sortedNumbers1.length / 2) + 1; i < sortedNumbers1.length; i++) {
                list2[i] = sortedNumbers1[i];
            }


            Thread t1 = new Thread(() -> bubbleSort(list1));

            Thread t2 = new Thread(() -> bubbleSort(list2));

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException ignored) {
            }

            sortedNumbers1 = merge(list1, list2);

            final long endTime = System.currentTimeMillis();        //end timer

            for (int sortedNumber : sortedNumbers1) {
                System.out.println(sortedNumber);
            }

            System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
            System.out.println("Tested length: " + sortedNumbers1.length);
        } else {
            usingThread();
        }
    }

    private int RandomInt(int amount) {
        return (int) (Math.random() * amount);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.addNumbersToList();


    }
}
