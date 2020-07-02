/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galeshapleymarrigeproblem;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author ASUS
 */
public class GaleShapley {

    private int N, engagedCount;
    private String[][] menPref;
    private String[][] womenPref;
    private String[] men;                  // Declare all the array and data members of the class
    private String[] women;
    private String[] womenPartner;
    private boolean[] menEngaged;
    PrintWriter inprint = new PrintWriter("input.txt");
    PrintWriter outprint = new PrintWriter("output.txt");
    /**
     * Constructor *
     */
    public GaleShapley(int n) throws FileNotFoundException {
        double startTime=System.nanoTime();
        N = n;    // number of men or women
        engagedCount = 0;    // Initially No one is engaged
        men=generateList("M",n);// Initialise men array 
        System.out.println("Mens List :");
        inprint.println("Mens List :");
        PrintList(men);
        women=generateList("W",n);// Initialise women array
        System.out.println("Womens List :");
        inprint.println("Womens List :");
        PrintList(women);
        menPref=generatePrefrenceList(women,n);// Initialise men array with Two dimensional Array String array
        System.out.println("Mens Preference List :");
        inprint.println("Mens Preference List :");
        PrintPreferenceList(menPref);        
        womenPref=generatePrefrenceList(men,n);// Initialise women array with Two dimensional Array String array
        System.out.println("Womens Preference List :");
        inprint.println("Womens Preference List :");
        PrintPreferenceList(womenPref);        
        menEngaged = new boolean[N];   // Initialise with all the false values as no one is engaged
        womenPartner = new String[N]; // Initialise with all the null values as no one is engaged
        inprint.close();
        calculateMatches();
        double elapsedTime = System.nanoTime() - startTime;
        System.out.println("elspsed time  for Running Gale Shapley Marriage Algorithm "+ (double)elapsedTime/1000000000 + "seconds");
        outprint.println("elspsed time  for Running Gale Shapley Marriage Algorithm "+ (double)elapsedTime/1000000000 + "seconds");
        outprint.close();
    }
  
    /**
     * functions to generate men,women,Men preference & women preference list *
     */
    private static String[] generateList(String Alphabet,int n) {
        //for generating the mens and women list with n no
        String[] list=new String[n];
        for (int i = 0, count=1; i < n; i++,count++) {
          list[i] = Alphabet+count;
        }
        return list;
    }
    private static String[][] generatePrefrenceList(String[]prefredList,int n) {
        //for generating the mens and women list with n no
        String[][] finalPreflist=new String[n][n];
        for (int i = 0; i <n; i++) {
            String[]tempMen=shuffleArray(prefredList);
            for (int j = 0; j < n; j++) {
                finalPreflist[i][j]=tempMen[j];
            }
        }
        return finalPreflist;
    }

    public static String[] shuffleArray(String[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
        return a;
    }

    private static void swap(String[] a, int i, int change) {
        String helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }

    /**
     * function to calculate all matches *
     */
    private void calculateMatches() {
        while (engagedCount < N) // Iterate until  engage count is less than N 
        {
            int free;
            for (free = 0; free < N; free++) // iterate until everyone is engaged
            {
                if (!menEngaged[free]) // if already engaged Break
                {
                    break;
                }
            }
            for (int i = 0; i < N && !menEngaged[free]; i++) {
                /* Get First men from two dimensional array(Lets say Wn and pass it to womenIndex fucncion 
                    *  Now women Index fucntion will look for index in women array .that at what position wn is in women array.here Wn is womens last pref
                    *      String[] w = {"W1", "W2", "W3", "W4", "W5",.....,"Wn"}; Array Index starts from 0
                    * 
                    * 
                    * */
                int index = womenIndexOf(menPref[free][i]);  
                if (womenPartner[index] == null) // First Check if that women is already engaged or not
                {
                    womenPartner[index] = men[free];  // if she is free assign that man to women index

                    menEngaged[free] = true;    // Change value of that man to true as he engaged now
                    engagedCount++;            // Update the counter 
                } else {
                    String currentPartner = womenPartner[index];   // But if women is already engaged check her prefrence using morePrefernce. If prefernce have high order update.
                    if (morePreference(currentPartner, men[free], index)) {
                        womenPartner[index] = men[free];   // If new Partner has high order then assign it to women
                        menEngaged[free] = true;
                        menEngaged[menIndexOf(currentPartner)] = false;
                    }
                }
            }
        }
        printCouples();
    }
    /**
     * function to check if women prefers new partner over old assigned partner
     * *
     */
    private boolean morePreference(String curPartner, String newPartner, int index) {
        for (int i = 0; i < N; i++) // Iterate over women pref array
        {
            if (womenPref[index][i].equals(newPartner)) // 
            {
                return true;
            }
            if (womenPref[index][i].equals(curPartner)) {
                return false;
            }
        }
        return false;
    }

    /**
     * get men index *
     */
    private int menIndexOf(String str) {
        for (int i = 0; i < N; i++) // Iterate over Men array to check to get index
        {
            if (men[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * get women index *
     */
    private int womenIndexOf(String str) {
        {
            for (int i = 0; i < N; i++) // Iterate over Women array to check to get index
            {
                if (women[i].equals(str)) {
                    return i;
                }
            }
            return -1;
        }

    }

    /**
     * print couples men/women list and men/women reference list *
     */
    public void printCouples() { 
        outprint.println("Couples are : ");
        System.out.println("Couples are : ");
        for (int i = 0; i < N; i++) {
            outprint.println(womenPartner[i] + " " + women[i]);  // Printing Final Result
            System.out.println(womenPartner[i] + " " + women[i]);
        }
        
        
        System.out.println("");
        System.out.println("Kindly check the Dynamically generated"+"\n"
                + "Input and output files in the root folder");
        System.out.println("");
    }
    
    public void  PrintList(String []array){
        inprint.println(Arrays.toString(array));
        System.out.println(Arrays.toString(array));
        inprint.println("");
        System.out.println("");
    }
    
    public void PrintPreferenceList(String [][] array){
        
        for (String[] row : array){
            // converting each row as string 
            // and then printing in a separate line 
            inprint.println(Arrays.toString(row)); 
            System.out.println(Arrays.toString(row)); 
        }
            inprint.println("");
            System.out.println("");
//         for(int i=0;i<array.length;i++)   {
//         for(int j=0;j<array[i].length;j++){
//             System.out.print("\""+array[i][j]+"\",");
//         }
//             System.out.println("");
//         }
    }
}
