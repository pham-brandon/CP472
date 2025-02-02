public class Task1 {
    public static int countAboveAverage(int[] array) {
        if (array.length == 0) return 0;

        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        
        double average = (double) sum / array.length;

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > average) count++;
        }
        return count;
    }
}
