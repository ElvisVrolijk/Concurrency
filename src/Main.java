/**
 * Created by e_voe_000 on 9/2/2016.
 */
public class Main {

    private int randomNumbers;
    int[] sortedNumbers = new int[200000] ;  //only change number here to test

    private void BubbleSort(int[] numbers){
        boolean flag = true;
        int temp;

        while(flag){
            flag=false; //set flag to false awaiting a possible swap
            for (int j = 0; j < sortedNumbers.length-1 ; j++) {
                if(sortedNumbers[j]<sortedNumbers[j+1]){

                    temp=sortedNumbers[j];      //swap elements
                    sortedNumbers[j]=sortedNumbers[j+1];
                    sortedNumbers[j+1]=temp;
                    flag=true;      //shows a swap occurred
                }
            }
        }
    }

    private void AddNumbersToList(){
        for (int i = 0; i < sortedNumbers.length; i++) {
            int j = RandomInt(sortedNumbers.length)+1;
            sortedNumbers[i]=j;
        }

        final long startTime = System.currentTimeMillis();      //start timer
        BubbleSort(sortedNumbers);
        final long endTime = System.currentTimeMillis();        //end timer
        for (int i = 0; i < sortedNumbers.length; i++) {
            System.out.println(sortedNumbers[i]);
        }

        System.out.println("Tested length: " + sortedNumbers.length );
        System.out.println("Total execution time: " + (endTime - startTime) + " miliseconds" );
    }

    private int RandomInt(int amount){
        randomNumbers = (int)(Math.random() * amount);
        return randomNumbers;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.AddNumbersToList();
    }
}
