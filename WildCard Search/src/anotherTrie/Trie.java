package anotherTrie;

import java.io.*;               // For console input 

public class Trie
{
    public Node root;          // Trie root
    public Node current;       // Current Position Inside the Trie
    
    /** Array of letters that are used in comparison operations in order to 
     *  know which letter is currently being stored into the trie */
    private String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", 
                                "j", "k", "l", "m", "n", "o", "p", "q", "r", 
                                "s", "t", "u", "v", "w", "x", "y", "z"};
                                
    /** Stores the ocurrence of each letter read into the trie, the counter 
     * stored at each index corresponds to the letter stored at the same index
     * in array letters */
    private double[] letterCount = new double[26];
    
    /** Stores the percentage of ocurrence of each letter in the trie, the
     * precentage stored at each  index corresponds to the letter stored at the
     * same index in array letters */
    private double[] percentageOfLetter = new double[26];
    
    // Total number of letters read into the trie
    private double totalLetterCount;
    
    // Numbers used in calculating a random number to find a random letter
    private final double max = 100.0;
    private final double min = 0.0;    
    
    /**
     * Declares an empty trie
     */
    public Trie()
    {
        Node root = new Node();
    }

    /**
     * Build the dictionary trie reading the input from a given filename
     * @param   An array of strings with the command line arguments
     * @return  None
     */
    public Trie(String[] commandLine) throws IOException { 
        
        root = new Node();              // Declare a new trie
        BufferedReader reader;          // To read from a file
        String input;                   // Store line of input
        
        // Check if filename was declared and if it exists
        checkIfValidFilename( commandLine );  
        
        // Declare in which file the input is in
        reader = new BufferedReader( new FileReader ( commandLine[0] ) );    
        
        // Declare in which file the input is in
        //reader = new BufferedReader( new FileReader ( "words" ) );    

        // Read each line of the input file
        while ( (input = reader.readLine() ) != null) {  
            
            // Add word to trie
            addWord(input);                    
        }
        
        // current position in trie is the root
        current = root;
        
        // Calculate percentage of ocurrence of letters 
        calculatePercentages();
    }
    
    /**
     *  Description: Node
     *               This class implement a node structure which is used as the
     *               structural components of the trie
     */
    public class Node{
        private String letter;  // Letter hold in the node    
        boolean isAWord;        // Indicates wether this node represents starting positionof a word
        private Node next;      // Leftmost child pointer
        private Node sibling;   // Right sibling pointer
    
        /**
         * Creates a new node
         * @param   None
         * @return  None
         */
        Node () {                    
            letter = null; 
            isAWord = false;
            next = sibling = null;
        }

        /**
         * Creates a new node with given letter and boolean value
         * @param   A string for the letter hold in node
         *          A boolean value to check if this the start of a word
         * @return  None
         */        
        Node (String s) {                    
            letter = s;
            isAWord = false;
            next = sibling = null;
        }       
    }
    
    // Check if the given letter is in the next level of the trie from the given node pointer
    // Returns boolean if it finds it, else returns null
    public Node findLetter(Node crtPosition, String letter) {        
        Node pointer = crtPosition;                // Initialize pointer for position refernce in trie
                
        // Word is not in tree if still looking for word letters and there aren't any more
        if (pointer.next == null) { 
            return null;
        }
        else {  // There is a child from the current node
            
            // Check if next node contains the current letter we're looking for
            if ( pointer.next.letter.equals(letter) ) {   
                pointer = pointer.next;                 // go to next child
            }
            else {  // Check if a sibling contains the same letter
                pointer = pointer.next;                 // go to start of node sibling list
                  
                // Loop until a node with the same letter is found or sibling list ends  
                while ( pointer.sibling.sibling != null && !pointer.sibling.letter.equals(letter) ) {
                    pointer = pointer.sibling;
                }
                
                // If no sibling with same letter is found, word isn't in trie
                if ( pointer.sibling.sibling == null ) {
                    return null;        // String is not in tree
                }
                else {  // A sibling has the letter we're looking for, continue
                    pointer = pointer.sibling;  // One sibling has same letter
                }
           }
        }
        return pointer;
    }
    
    
    /**
     * Find a given word in the given trie 
     * @param   A string to look up in the trie
     *          The root of the trie in where to look up the word
     * @return  A pointer to the node with the starting position (letter) of the word found
     *          If word is not found, it returns a null pointer
     */
    public Node findStr(String word) {        
        Node pointer = root;                // Initialize pointer for position refernce in trie
        String input = word;                // Word to look up
        
        // Start a while lopp searching the chars of the word in trie starting backwards
        while (input.length() >= 1) {
            
            // Get the last letter in the word
            String firstChar = input.substring( 0, 1 );
            input = input.substring( 1, input.length() );     // Update string minus last letter
        
            if ( !Character.isLetter(firstChar.charAt(0)) ) {    // If char is not a letter skip it
                continue;
            }
                
            // Word is not in tree if still looking for word letters and there aren't any more
            if (pointer.next == null) { 
                return null;
            }
            else {  // There is a child from the current node
                
                // Check if next node contains the current letter we're looking for
                if ( pointer.next.letter.equals(firstChar) ) {   
                    pointer = pointer.next;                 // go to next child
                }
                else {  // Check if a sibling contains the same letter
                    pointer = pointer.next;                 // go to start of node sibling list
                      
                    // Loop until a node with the same letter is found or sibling list ends  
                    while ( pointer.sibling.sibling != null && !pointer.sibling.letter.equals(firstChar) ) {
                        pointer = pointer.sibling;
                    }
                    
                    // If no sibling with same letter is found, word isn't in trie
                    if ( pointer.sibling.sibling == null ) {
                        return null;        // String is not in tree
                    }
                    else {  // A sibling has the letter we're looking for, continue
                        pointer = pointer.sibling;  // One sibling has same letter
                    }
               }
           }
        }
        // All letters from word match, check if current pointer reference is start of word
        if ( pointer.isAWord == true) {    
            current = pointer;
            return pointer;     // Return pointer to start of found word
        }
        else {  // Letters matched, but it not a word in the tree          
            return null;        // String is not in tree
        }
    }

    /**
     * Print n number of words starting from a given reference in the trie
     * @param   The root of the trie
     *          The current reference pointer in the trie
     *          The number of words to be printed
     * @return  A pointer reference to the last word printed
     */
    public Node printNextWords( int n) {
        Node pointer = current;
        
        if (root.next == null) {    // check if Dictinary has no words
            System.out.println(" Trie is Empty");
            return root;
        }
        System.out.println();
        
        // Start loop to print n number of words
        while ( n > 0) {
            
            // Check if we are at the end of the trie or there is no child
            if ( pointer.next == null || pointer.next == root) {
                
                if ( pointer.next == root) {    // We are at the end of the trie
                    System.out.println(">> End of Trie <<");              
                    pointer = root;             // Reset the position reference
                    break;                      // Stop printing words
                }
                else {  // Pointer has no child
                    pointer = pointer.sibling;      // Goto pointer sibling
                    
                    // If we are at the end of the list, back up to parent node next sibling
                    while ( pointer.sibling == null ) {
                        if ( pointer.next == root ) {   // We are at the end of the trie
                            break;                      // Stop printing words       
                        }
                        else {  // Back up to parent node next sibling, list in above level
                            pointer = pointer.next.sibling;
                        }
                    }
                    if ( pointer.isAWord == true) { // If pointer is start of word, prin it
                        printWord(pointer);
                        n--;                        // Decrement words to print counter
                    }
                }
            }
            else {  // Child of current reference pointer is not null
                pointer = pointer.next;
                if ( pointer.isAWord == true) {     // If pointer is start of word, prin it
                    printWord(pointer);
                    n--;                            // Decrement words to print counter
                }
            }   
        }
        current = pointer;
        return pointer;     // Pointer to last word printed
    }
        
    /**
     * Delete a given word from given trie
     * @param   String to be found and deleted
     *          The root from the given trie
     * @return  A pointer to the next closest parent from the current nodes deleted
     *          If word to be deleted is not found, returns a null pointer
     */
    public Node deleteWord(String lookUpWord) {
        Node pointer = findStr( lookUpWord);      // Check if word is in tree
        Node tempPointer = null;                        // Extra reference pointer
        
        if (pointer == null) {      // Word is not in tree
            return null;
        }
        else {      // Word is in trie, delete it            
            if ( pointer.next != null ) {            // Word starting position has children nodes
                pointer.isAWord = false;             // No longer represents a word
                return pointer;
            }
            else {                                  // Word starting position has no children nodes
                tempPointer = pointer;
                
                while (true) {  //  Loop until all parts of the word not used by others are deleted
                    
                    do {    // Go to the parent of the sibling list node of current pointer
                        tempPointer = tempPointer.sibling;
                    }   while ( tempPointer.sibling != null );          // Goto last sibling
                    tempPointer = tempPointer.next;                     // Goto parent
                    
                    // Check if our node is inmmediate child & has no children or siblings
                    if ( tempPointer.next == pointer && pointer.sibling.sibling == null) {       
                        tempPointer.next = null;        // Delete reference to child
                        
                        if ( tempPointer == root) { // We have arrived to root, no more to delete
                            break;
                        }
                        
                        // if parent is not the start of a word, it needs to be deleted
                        if ( tempPointer.isAWord == false ) {
                            pointer = tempPointer;      // Point now to the parent 
                            continue;
                        }
                        else {  // parent is a word, can not delete anymore nodes
                            break;
                        }
                    }
                    else {  // Current pointer has sibling nodes
                                                  
                        // check if child is pointer first in sibling list, 
                        if ( tempPointer.next == pointer ) { 
                            // update the child to be the pointer's sibling
                            tempPointer.next = tempPointer.next.sibling;
                            break;
                        }                                                
                        tempPointer = tempPointer.next;     // Pointer is not first sibling
                        
                        // Find node previous to our start of word node
                        while ( tempPointer.sibling != pointer ) {
                            tempPointer = tempPointer.sibling;
                        }
                        
                        // Update reference of previous node to pointer sibling
                        tempPointer.sibling = pointer.sibling;
                        break;
                    }
                }
            }
        }
        return tempPointer;
    }
            
    /**
     * Add a word to the trie
     * @param   The root of the trie
     *          The string to be added
     * @return  None
     */
    public Node addWord(String input) {
        Node pointer = root;  
	
	// Remove characters that are not letters
	input = removeNonLetterChars(input);
        
        // The words in this trie have to be at least 3 characters long
        if (input.length() < 3 ) {
            return pointer;
        }
        
        input = input.toLowerCase();        // Convert string to lower case
        
        while (input.length() >= 1) {            
            
            // Get the first letter of the word
            String firstChar = input.substring( 0, 1 );
            
            // Update string minus first letter
            input = input.substring( 1, input.length() ); 
                
            // If char is not a letter skip it    
            if ( !Character.isLetter(firstChar.charAt(0)) ) {    
                continue;
            }
            
            // Keep track of letters read into the trie
            countLetter(firstChar);
                          
            if (pointer.next == null) {     
                // No more child nodes, We need to add a new child
                Node newChild = new Node(firstChar);
                // Set child to be new node
                pointer.next = newChild;          
                    
                // Create the last sibling in list pointing to parent    
                Node lastSibling = new Node();    
                lastSibling.sibling = null;
                lastSibling.next = pointer;
                newChild.sibling = lastSibling;
                    
                pointer = newChild;              // Update pointer
            }
                
            else {  // There is still children nodes         
                if ( pointer.next.letter.equals(firstChar) ) { 
                    // node has same letter, go to next child
                    pointer = pointer.next;
                }                    
                else {  // Child has not the same letter we're looking for
                    pointer = pointer.next;
                                                
                    // Check if there's a sibling with same letter
                    while ( pointer.sibling.sibling != null && 
                            !pointer.sibling.letter.equals( firstChar ) ) {
                                
                        pointer = pointer.sibling;
                    }
                        
                    // Check if we got tothe last sibling, node matching letter was not found    
                    if (pointer.sibling.sibling == null) { 
                        
                        // Add new node at end of sibling list
                        Node newSibling = new Node(firstChar);        
                        newSibling.sibling = pointer.sibling;
                        pointer.sibling = newSibling;
                        pointer = newSibling;
                    }
                    else {  
                        //There is a node matching the letter we're looking for
                        // update pointer to this position
                        pointer = pointer.sibling;      
                    }
                }
            }
        }        
        if ( pointer == root ) {    // All characters in word were not a letter  
            return null;                 // No word will be added
        }        
        pointer.isAWord = true;       
        return pointer;
    }

    /**
     * Print a word that that starts at the given pointer reference
     * @param   A node referencce pointer
     * @return  None
     */
    public void printWord(Node pointer) {
        String word = "";       // Declaration of string to hold word to print
        
        do {
            word = pointer.letter + word;       // Store next character of word
            
            do {    // Go to the end of the sibling list
                pointer = pointer.sibling;
            }   while ( pointer.sibling != null );    
            
            pointer = pointer.next;             // Go to the parent of the node
                        
        }   while (pointer.sibling != null );   // Stop when we get to the root
        
        System.out.println(word);               // Print word
    }
    
    /** 
     * Get a random letter contained inside the trie based on how often it 
     * occurs in the trie
     * @ param  None
     * @ return A string containing a letter
     */
    public String getRandomLetter() {
        
        /** The method used to get arandom letter is based on the percentage of
         * how many times it was read into the trie. It gets a random number 
         * between 0.0 and 100.00, it adds the percentages of letters starting
         * from "a" to "z" until a number equal or bigger than the random 
         * number is found, then we take the letter where this occurred as the
         * random letter.
         */
        
        String randomString = null;
        double temp = 0.0;
        
        // Get a random number between 0.0 and 100.0
        double randomNumber = Math.floor (Math.random() * ( max - min + 1) ) + min;
        
        // Add the ocurrence percentages of each letter starting from "a" to "z"
        for (int i = 0; i < percentageOfLetter.length; i++) {
            
            // Store the percentage sum
            temp = percentageOfLetter[i] + temp;
            
            // Stop when the sum is equal or bigger than random number
            if ( temp >= randomNumber ) {
                randomString =  letters[i];
                break;
            }
        }
        
        // Return letter where the sum isequal or bigger than random number
        return randomString;
    }

    /** 
     * Count the number of times aeach letter is read in the trie
     * @ param  A string containing the letter to be counted
     * @ return None
     */
    public void countLetter(String letter) {
         String temp;                   // Temporary string holder
         
         // Compare letter to array containing alphabet letters
         for (int i = 0; i < letters.length; i++ ) {
            temp = letters[i];
            
            // If a match is found
            if ( temp.equals( letter ) ) {
                
                // Increase counter of that particular letter
                letterCount[i]++;
                
                // Increase counter of total letters read
                totalLetterCount++;
            }
         }
    }
    
    /** 
     * Calculate the percentage of ocurrance of all letters read into trie
     * @ param  None
     * @ return None
     */
    public void calculatePercentages() {
        
        // Valculate the percentage of ocurrence of each letter 
        for (int i = 0; i < percentageOfLetter.length; i++) {
            
            // Math formula: Percentage ox X = times X occurs * 100 / total
            percentageOfLetter[i] = (letterCount[i] * 100) / totalLetterCount;
        } 
    }
    
    /**
     * Checks if a file was specified by the user in the inputline to use as input to 
     * build the trie, and if it was declared, it checks it exists.
     * @param   A string array containing the arguments from the command line
     * @return  None
     */
    public static void checkIfValidFilename( String[] commandLine) throws IOException {
        int numberOfArgs = commandLine.length;      // Number of arguments in command line
        
        // Check if an input file was actually declared
        if ( numberOfArgs < 1 ) {
            System.out.println( " No Input File Declared." );
            System.out.println( " Ending Program . . . \n" );
            System.exit(0);         // End program if no file was declared
        }
        
        try {   // Check if the file actually exists
            BufferedReader reader = new BufferedReader( new FileReader ( commandLine[0] ) );    
        } 
        catch (FileNotFoundException e) {
            System.out.println( " Input Filename Not Found" );
            System.out.println( " Ending Program . . . \n" );
            System.exit(0);         // End program if no file was declared
        }
    }
    
    /**
     * Removes the non letter characters from the received String and returns that string
     * @ param  A string
     * @ return A string with the non letter characters removed
     */
    public String removeNonLetterChars(String word) {
    
    	// Temporary string will hold string with no letter chars
    	String tempWord = "";
	
	// Traverse the entire string
	while ( word.length() >=1 ) {
	
	    // Get the first letter of the word
	    String firstChar = word.substring(0, 1);
	    
	    // Update string minus first letter
	    word = word.substring(1, word.length() );
	    
	    //Add character to the new word only if it is a letter
	    if ( Character.isLetter( firstChar.charAt(0) ) ) {
	        tempWord = tempWord + firstChar;
	    }
	}
	
	// Returned string with no letter chars
	return tempWord;
	
    }    
            
    /**
     * Check if number in a given string is really a number and bigger than zero
     * @param   A string containing a numerical value
     * @return  A boolean value, true f string is a number and bigger than zero
     *                           false otherwise
     */
    public boolean isValidNumber(String str) {
        char[]c = str.toCharArray();    // An array of the chrarcter of the string
        
        // Check that every character is a number
        for ( int i = 0; i < str.length(); i ++ ) {
            if ( !Character.isDigit( c[i] ) ) {
                System.out.println(" Argument Is Not A Number");
                printInvalidCommand();
                return false;
            }
        }        
        
        int number = Integer.parseInt( str );   
        
        // Check if the number is bigger than zero
        if ( number < 1 ) {
            System.out.println(" Number not valid: Smaller than zero");
            printInvalidCommand();
            return false;
        }        
        return true;
    }

    /**
     * Print a invalid command message
     * @param   None
     * @return  None
     */
    public void printInvalidCommand() {
        System.out.println("Not Valid Command. Please Try Again. ");
    }    
   

}
