public class Task2Modified {

    static int[] array = new int[46]; // A statically declared array to improve its efficiency and reduce redundant calls

    public static int fibonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        
        if (array[n] != 0) {
            return array[n];
        } 
        
        array[n] = fibonacci(n - 1) + fibonacci(n - 2);
        return array[n];
    }
}   