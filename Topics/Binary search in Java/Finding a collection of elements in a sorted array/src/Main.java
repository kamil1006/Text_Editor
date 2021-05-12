
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        int i = Integer.parseInt(scanner.nextLine());

        int[] tablica1 = new int[i];
        for(int x=0;x<i;x++){
            tablica1[x]=scanner.nextInt();
        }

        int j = Integer.parseInt(scanner.next());

        int[] tablica2 = new int[j];
        for(int x=0;x<j;x++){
            tablica2[x]=scanner.nextInt();
        }

        int[] tablica3 = new int[j];
        for(int x=0;x<j;x++){
            tablica3[x] =binarySearch(tablica1,tablica2[x],0, tablica1.length-1);
            if(tablica3[x]!=-1)
                tablica3[x]++;
            System.out.print(tablica3[x]+" ");
        }




    }

    public static int binarySearch(int[] array, int elem, int left, int right) {
        if (left > right) {
            return -1; // search interval is empty, the element is not found
        }

        int mid = left + (right - left) / 2; // the index of the middle element

        if (elem == array[mid]) {
            return mid; // the element is found, return its index
        } else if (elem < array[mid]) {
            return binarySearch(array, elem, left, mid - 1); // go to the left subarray
        } else {
            return binarySearch(array, elem, mid + 1, right); // go to the right subarray
        }
    }




}
