import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here

        Scanner scanner = new Scanner(System.in);
        String line= scanner.nextLine();

        int i = line.indexOf('?');
        String substring = line.substring(i+1);
        String[] tablica=substring.split("&");
        int length = tablica.length;
        Map<String,String> mapa = new LinkedHashMap<>();
        for(int x=0;x<length;x++){

            String[] temp=tablica[x].split("=");
            if(temp.length>1)
            mapa.put(temp[0],temp[1]);
            else
                mapa.put(temp[0],"not found");
        }

        for( Map.Entry<String,String> ss:mapa.entrySet()){
            System.out.print(ss.getKey()+" : ");
            System.out.println(ss.getValue()==null?"not found":ss.getValue());
        }
        if(mapa.containsKey("pass")){
            System.out.print("password : ");
            System.out.println(mapa.getOrDefault("pass",""));
        }

    }
}