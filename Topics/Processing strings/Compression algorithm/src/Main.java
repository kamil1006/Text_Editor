import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here


        Scanner scanner= new Scanner(System.in);
        String line=scanner.nextLine();

        String wyjscie="";
        int licznik=0;
        char znak;

        char x1,x2;

        int k=line.length();
        if(line.length()>1){
            znak=line.charAt(0);
            licznik=1;
            for(int i=1;i<k;i++){
                x1 = line.charAt(i-1);
                x2 = line.charAt(i);
                if(x1==x2){
                    licznik++;
                    if(i==k-1) wyjscie=wyjscie+znak+licznik;


                }else {
                    wyjscie=wyjscie+znak+licznik;
                    znak=x2;
                    licznik=1;
                    if(i==k-1) wyjscie=wyjscie+znak+licznik;

                }



            }





        } else if(line.length()==1){
            znak=line.charAt(0);
            wyjscie= String.valueOf(znak)+1;
        }

        System.out.println(wyjscie);



    }
}