import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public abstract class EhancedInsertionSort_ThresholdSwapping {

    public static void main(String[] args) {

        try {
            File file = new File("result.txt");
            FileWriter writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);

             //*** Generate random array

            int[] sizes = {10000, 50000, 100000, 500000};
            int numTests = 20; // Number of tests for each array size

            for (int size : sizes) {

                printWriter.println("\nArray Size: " + size);

                int[] arr_test1 = new int[size];
                int[] arr_test2 = new int[size];
                int[] arr_test3 = new int[size];
                int[] arr_test4 = new int[size];
                long[] stored_time1 = new long[numTests];
                long[] stored_time2 = new long[numTests];
                long[] stored_time3 = new long[numTests];
                long[] stored_time4 = new long[numTests];
                long totalExecutionTime1 = 0;
                long totalExecutionTime2 = 0;
                long totalExecutionTime3 = 0;
                long totalExecutionTime4 = 0;

                for (int test = 0; test < numTests; test++) {
                    // Generate random array for each test
                    for (int i = 0; i < size; i++) {
                        int n = (int) (Math.random() * size + 1);
                        arr_test1[i] = n;
                        arr_test2[i] = n;
                        arr_test3[i] = n;
                        arr_test4[i] = n;
                    }

                    long startTime1 = System.currentTimeMillis();
                    int[] arr1 = classisInsretionSort(arr_test1);
                    long endTime1 = System.currentTimeMillis();
                    long executionTime1 = endTime1 - startTime1;
                    totalExecutionTime1 += executionTime1;
                    stored_time1[test] = executionTime1;
                    printWriter.println("Execution time for Classic Insertion Sort (test " + (test+1) + "): " + executionTime1);

                    long startTime2 = System.currentTimeMillis();
                    int[] arr2 = doEncWInsertionSort(arr_test2);
                    long endTime2 = System.currentTimeMillis();
                    long executionTime2 = endTime2 - startTime2;
                    totalExecutionTime2 += executionTime2;
                    stored_time2[test] = executionTime2;
                    printWriter.println("Execution time for Enhanced With 1/3 (test " + (test+1) + "): " + executionTime2);

                    long startTime3 = System.currentTimeMillis();
                    int[] arr3 = doEncWInsertionSortWithOneFourth(arr_test3);
                    long endTime3 = System.currentTimeMillis();
                    long executionTime3 = endTime3 - startTime3;
                    totalExecutionTime3 += executionTime3;
                    stored_time3[test] = executionTime3;
                    printWriter.println("Execution time for Enhanced With 1/4 (test " + (test+1) + "): " + executionTime3);

                    long startTime4 = System.currentTimeMillis();
                    int[] arr4 = doEncWInsertionSortWithLogE(arr_test4);
                    long endTime4 = System.currentTimeMillis();
                    long executionTime4 = endTime4 - startTime4;
                    totalExecutionTime4 += executionTime4;
                    stored_time4[test] = executionTime4;
                    printWriter.println("Execution time for Enhanced With Log base e (test " + (test+1) + "): " + executionTime4 + "\n");
                }
                
                long averageExecutionTime1 = (long) ((double) totalExecutionTime1 / (double) numTests);
                long averageExecutionTime2 = (long) ((double) totalExecutionTime2 / (double) numTests);
                long averageExecutionTime3 = (long) ((double) totalExecutionTime3 / (double) numTests);
                long averageExecutionTime4 = (long) ((double) totalExecutionTime4 / (double) numTests);

                printWriter.println("\nAverage Execution time For Classic Insertion Sort: " + averageExecutionTime1);
                printWriter.println("Max Execution time For Classic Insertion Sort: " + Arrays.stream(stored_time1).max().getAsLong());
                printWriter.println("Min Execution time For Classic Insertion Sort: " + Arrays.stream(stored_time1).min().getAsLong());

                printWriter.println("\nAverage Execution time For Enhanced With 1/3: " + averageExecutionTime2);
                printWriter.println("Max Execution time For Enhanced With 1/3: " + Arrays.stream(stored_time2).max().getAsLong());
                printWriter.println("Min Execution time For Enhanced With 1/3: " + Arrays.stream(stored_time2).min().getAsLong() + "\n");

                printWriter.println("\nAverage Execution time For Enhanced With 1/4: " + averageExecutionTime3);
                printWriter.println("Max Execution time For Enhanced With 1/4: " + Arrays.stream(stored_time3).max().getAsLong());
                printWriter.println("Min Execution time For Enhanced With 1/4: " + Arrays.stream(stored_time3).min().getAsLong());

                printWriter.println("\nAverage Execution time For Enhanced With Log base e: " + averageExecutionTime4);
                printWriter.println("Max Execution time For Enhanced With Log base e: " + Arrays.stream(stored_time4).max().getAsLong());
                printWriter.println("Min Execution time For Enhanced With Log base e: " + Arrays.stream(stored_time4).min().getAsLong());

                printWriter.println();
            }
        printWriter.close();
        System.out.println("Results written to output.txt.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] classisInsretionSort(int[] input) {
        int temp;
        for (int i = 1; i < input.length; i++) {
            int j = i - 1;
            temp = input[i];
            while (j >= 0 && input[j] > temp) {
                input[j + 1] = input[j];
                j = j - 1;
            }
            input[j + 1] = temp;
        }
        return input;
    }

    public static int[] doEncWInsertionSortWithLogE(int[] input) {
        int t = 0;
        int temp;
        for (int i = 1; i < input.length; i++) {
            if (input[i] <= input[(int) (Math.log(i))]) { // If larger than half

                for (int j = (int) (Math.log(i)); j > 0; j--) {
                    if (input[i] >= input[j - 1] && input[i] < input[j]) {
                        t = j;
                        temp = input[i];
                        input[i] = input[j];
                        input[j] = temp;
                        break;
                    }
                }
                t = 0;
                for (int j = i; j > t; j--) {
                    if (input[j] < input[j - 1]) {
                        temp = input[j];
                        input[j] = input[j - 1];
                        input[j - 1] = temp;
                    }
                }
            } else {
                int j;
                temp = input[i];
                j = i - 1;
                while (j >= 0 && input[j] > temp) {
                    input[j + 1] = input[j];
                    j = j - 1;
                }
                input[j + 1] = temp;
            }
        }
        return input;
    }

    public static int[] doEncWInsertionSort ( int[] input){
        int t = 0;
        int temp;
        for (int i = 1; i < input.length; i++) {
            if (input[i] <= input[i/3]) { // If larger than half

                for (int j = i/3; j > 0; j--) {
                    if (input[i] >= input[j - 1] && input[i] < input[j]) {
                        t = j;
                        temp = input[i];
                        input[i] = input[j];
                        input[j] = temp;
                        break;
                    }
                }
                t = 0;
                for (int j = i; j > t; j--) {
                    if (input[j] < input[j - 1]) {
                        temp = input[j];
                        input[j] = input[j - 1];
                        input[j - 1] = temp;
                    }
                }
            } else {
                int j;
                temp = input[i];
                j = i - 1;
                while (j >= 0 && input[j] > temp) {
                    input[j + 1] = input[j];
                    j = j - 1;
                }
                input[j + 1] = temp;
            }
        }
        return input;

    }

    public static int[] doEncWInsertionSortWithOneFourth ( int[] input){
        int t = 0;
        int temp;
        for (int i = 1; i < input.length; i++) {
            if (input[i] <= input[i/4]) { // If larger than half

                for (int j = i/4; j > 0; j--) {
                    if (input[i] >= input[j - 1] && input[i] < input[j]) {
                        t = j;
                        temp = input[i];
                        input[i] = input[j];
                        input[j] = temp;
                        break;
                    }
                }
                t = 0;
                for (int j = i; j > t; j--) {
                    if (input[j] < input[j - 1]) {
                        temp = input[j];
                        input[j] = input[j - 1];
                        input[j - 1] = temp;
                    }
                }
            } else {
                int j;
                temp = input[i];
                j = i - 1;
                while (j >= 0 && input[j] > temp) {
                    input[j + 1] = input[j];
                    j = j - 1;
                }
                input[j + 1] = temp;
            }
        }
        return input;

    }

}
