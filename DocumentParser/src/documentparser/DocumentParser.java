/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentparser;
import java.io.*; 
import java.util.*; 
import java.util.regex.Pattern; 

/**
 *
 * @author Christian Randle
 */
public class DocumentParser
    {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
        {
        // TODO code application logic here
        WordBank_Class wordBank = new WordBank_Class();
        wordBank.FillFromFile(args[0]);
        
        ArrayList<Pair_Class> read = wordBank.getContents();
        System.out.println("\nWords in Document: "+"\n   "+wordBank.getWordCount()+"\n");
        System.out.println("Top Ten Words by Occurence: ");
        int i = 0;
        for(Pair_Class temp: read){
            if (i < 10){
                System.out.println("   "+temp.getKey()+" : "+temp.getValue());
                i++;
            }
            else {
                 System.out.println();
                break;
            }
        }
        
        System.out.println("Last Sentence with most frequent word: "+wordBank.lastSentence);
        
        }
    
    }
