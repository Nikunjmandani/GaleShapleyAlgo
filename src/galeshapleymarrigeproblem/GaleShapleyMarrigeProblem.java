/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galeshapleymarrigeproblem;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class GaleShapleyMarrigeProblem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        System.out.println("Enter 'N' numbers of men or womens"+"\n"
                            +"NOTE : this algo automatically generate N no of"
                + " Mens,Womens,"
                +"\n"+"Men preference list &women preference list "+ " : ");
        Scanner keyboard = new Scanner(System.in);
        int N = keyboard.nextInt();
        
        System.out.println("Gale Shapley Marriage Algorithm\n");   // Printing Statement
        GaleShapley gs = new GaleShapley(N);    // Create Ref Object and Here we are calling constructor by passing N
        
    }
    
}
