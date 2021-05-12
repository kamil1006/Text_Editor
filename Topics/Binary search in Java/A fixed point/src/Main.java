
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
        int i = Integer.parseInt(scanner.nextLine());
        boolean jest=false;
       int[] tablica = new int[i];
       for(int x=0;x<i;x++){
           tablica[x]=scanner.nextInt();
        if (x==tablica[x])  jest=true;  
       }
        System.out.println(jest);

    }


}
