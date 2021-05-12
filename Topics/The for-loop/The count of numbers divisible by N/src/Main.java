import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        // put your code here

        Scanner scanner= new Scanner(System.in);
        int a= scanner.nextInt();
        int b= scanner.nextInt();
        int n = scanner.nextInt();
        long stream = IntStream.range(a, b+1).filter(e->e%n==0).count();
        System.out.println(stream);

    }
}