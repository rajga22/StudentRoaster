import java.io.*;
import java.util.*;
public class StudentRoaster {
    public String findId(String id,Scanner fr){
        int f=0;
        String record,recordid="";
        while (fr.hasNext()){
            record= fr.nextLine();
            StringTokenizer stk= new StringTokenizer(record);
            String field1= stk.nextToken();
            String field2= stk.nextToken();
            if (field1.compareTo (id)==0)
            {
                f=1;
                recordid=field2;
                break;
            }
        }
        if (f==0) {
            System.out.println("Student ID not found for studentName");
        }
            return (recordid);
    }

    public String findName(String nm,Scanner fr){
        int f=0;
        String record,recordid="";
        while (fr.hasNext()){
            record= fr.nextLine();
            StringTokenizer stk= new StringTokenizer(record);
            String field1= stk.nextToken();
            String field2= stk.nextToken();
            if (field2.compareTo (nm)==0)
            {
                f=1;
                recordid=field1;
                break;
            }
        }
        if (f==0) {
            System.out.println("Student name not found for studentID");
        }
        return (recordid);
    }
 public static void main (String [] args) throws IOException {
     StudentRoaster ob = new StudentRoaster();
     FileInputStream myfile = new FileInputStream("src/roster (1).txt");
     Scanner fr = new Scanner(myfile);
     Scanner sc = new Scanner(System.in);
     String id;
     String nm;
     System.out.println("1.Find Student Id");
     System.out.println("2.Find Student name");
     System.out.print("enter choice");
     int ch = sc.nextInt();
     if (ch == 1) {
         System.out.print("enter name to be searched");
         id = sc.next();
         String result = ob.findId(id, fr);
         System.out.println(result);
     } else {
         System.out.print("enter ID to be searched");
         nm = sc.next();
         String result1 = ob.findName(nm, fr);
         System.out.println(result1);
     }
 }
}
