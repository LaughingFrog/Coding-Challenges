/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentparser;
import java.util.*; 
 import java.io.*; 
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
/**
 *
 * @author Christian Randle
 */

public class WordBank_Class{
    
    // Member Variables: 
    ArrayList<Pair_Class> bank;
    int totalwords = 0;
    int distinctWords = 0;
    String lastSentence;
    
    /* Methods: 
            Constructor
    */
    public WordBank_Class(){
       bank = new ArrayList<>();
    }
    
    /*IsEmpty: 
        checks if WordBank is empty
    
   Returns: boolean
   */
    public boolean isEmpty(){
       return (bank.isEmpty());
   }
   
   /*Contains: 
        checks if bank has an arraylist of a given word
    
   Returns: Boolean
   */
   public boolean contains(String inWord){
    if (isEmpty()) return false;
    
    String inWordUpper = inWord.toUpperCase();
    
    for(int i = 0; i< bank.size();i++){
        if(bank.get(i).getKey().equals(inWordUpper)) return true;
    }
    
    return false;
   }
   
   /*getIndex
        Returns the index of a word if it is present in the bank, else returns -1
   
   Returns: int
   */
   public int getIndex(String inWord){
    String inWordUpper = inWord.toUpperCase();
    if (isEmpty()) 
            return 1;
    
    for (int i = 0; i< bank.size();i++){
        if(bank.get(i).getKey().equals(inWordUpper))
            return i;
    }
    return 1;
   }
    

   /*addWord
        Adds word to wordbank

   Returns: void 
   */
   public void addWord(String inWord){
    String inWordUpper = inWord.toUpperCase();
    if (isEmpty()){
        distinctWords++;
        bank.add(new Pair_Class(inWordUpper,1));
    } else if(contains(inWord)){
        int index = getIndex(inWord);
        bank.get(index).update();
    } else {
       distinctWords++;
       bank.add(new Pair_Class(inWordUpper,1));
    }
   }
   
   /*getNumOccurences
        Returns the number of times a word has occured in this document
   
   Returns: int
   */
   public int getNumOccurences(String inWord){
       if (contains(inWord)){
        int index = getIndex(inWord);
        return bank.get(index).getValue();
       } else return 0;
   }
   
   
   /*getMostFrequent
        Returns the word with the highest associated occurence (value), 
        or "" if  empty
   
   Returns: String
   */
   public String getMostFrequent(){
    String outWord = "";
    int highest = 0;
    
       for(int i = 0; i< bank.size();i++){
           if(bank.get(i).getValue() > highest){
               highest = bank.get(i).getValue();
               outWord = bank.get(i).getKey();
           }
       }
       return outWord;
   }
   
    /*Organize():
       Organize banks into descedning order of occurence via quicksort 

       Returns: void
    */
    public void organize(){
           Collections.sort(bank,new PairComp_Class());
           return;
    } 

    /*getContents():
        returns a copy of current bank contents
    
        Returns: ArrayList<PairClass>
    */
    public ArrayList<Pair_Class> getContents(){
        ArrayList<Pair_Class> returnVal = new ArrayList<>(bank);
        return returnVal;
    }
    


    /* getWordCount():
          Returns number of words found

     Returns: int
     */ 
    public int getWordCount(){
        return totalwords;
    }

    
     /* sentenceSearch(String stringToSearch, String searchString):
        Takes a sentence and a word and returns true if the word is present in the sentence, else false.

       Returns: boolean 
     */
    public boolean sentenceSearch(String stringToSearch, String searchString){
        
        String upperSentence = stringToSearch.toUpperCase();
        String upperWord = searchString.toUpperCase();
        
        String[] parse = upperSentence.split("\\s+");

        for(String word: parse){
            word = word.replaceAll("[^\\w]","");
            if (word.equals(upperWord)) {return true;};
        }
        
        return false;
    }
   
    /* FillFromFile():
        Takes in a string representing a filepath, then attempst to open that file. 
        If succesful, it will use the scanner to populate the wordbank.

       Returns: void
     */
   public void FillFromFile(String inputFile){
      
    //regex for full sentence (assumes leading whitsepace)
    Pattern regex = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)");
    Scanner filescan;
    ArrayList<String> sentenceBank = new ArrayList<>();
    String sentence; 
    Boolean done = false;
    
    try{
        Scanner fileScan = new Scanner(new File(inputFile));
        while(true){
            // Throws NoSuchElementException upon no more sentences
            try{
                sentence = fileScan.next();
                //split sentence whitespace 
                String[] parse = sentence.split("\\s+");

                //scrub for any characters not alpha-numeric, add to bank
                for(String word: parse){
                    totalwords++;
                    word = word.replaceAll("[^\\w]","");
                    addWord(word);
                }
            } catch( NoSuchElementException exSentenceRead){
                //Organize and return;
                organize();
                String highest = bank.get(0).getKey();
                Scanner srchScan = new Scanner(new File(inputFile));
                srchScan.useDelimiter("[.?!]");
                
                try{
                    while(true){
                        String sent = srchScan.next();
                        System.out.println(sent);
                        if (sentenceSearch(sent,highest)){
                            lastSentence = sent;
                        }
                    }
                }catch( NoSuchElementException exSentenceSearch){
                    
                }

                return;
            }
        }
    } catch(FileNotFoundException ex){
        System.out.println(ex);
    }

  }
   
   
   
}


/*PairComp_Class
     Comparator used by Collections.Sort() to sort through word-occurence pairs

*/
class PairComp_Class implements Comparator<Pair_Class>{
        //TODO: Test ordering (might have to flip signs)
    @Override
    public int compare(Pair_Class A,Pair_Class B){
        if(A.getValue() > B.getValue()){
            return -1;} 
        else if (A.getValue() == B.getValue()){
            return 0;} 
        else {
          return 1;}
    }
}
