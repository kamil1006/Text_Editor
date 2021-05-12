import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner= new Scanner(System.in);
        int a= scanner.nextInt();
        int b= scanner.nextInt();
        int sum=0;
        int x=0;
        for(int i=a;i<=b;i++){
            if(i%3==0){
                sum+=i;
                x++;
            }


        }
        double wynik=(double) sum/(double) x;
        System.out.println(wynik);

    }
}