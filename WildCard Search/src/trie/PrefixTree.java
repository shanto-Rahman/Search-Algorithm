package trie;


public class PrefixTree
{
	public static int wordType=0;//Suffix, 1 for prefix
	//static boolean  middle= false;
	static boolean foundWildCard=false;
	static int x=0;
	
    static TrieNode createTree()
    {
        return(new TrieNode('\0'));
    }
    
    static void backtrackingWord(TrieNode root, String word)
    {
        int offset = 97;
        int l = word.length();
        char[] letters = word.toCharArray();
        TrieNode backTrackCurNode = root;
        
        for (int i =l-1 ; i >=0; i--)
        {
            if (backTrackCurNode.links[letters[i]-offset] == null)
            	backTrackCurNode.links[letters[i]-offset] = new TrieNode(letters[i]);
            backTrackCurNode = backTrackCurNode.links[letters[i]-offset];
        }
        backTrackCurNode.fullWord = true;  
    }
    
    static void insertWord(TrieNode root, String word)
    {
        int offset = 97;
        int l = word.length();
        char[] letters = word.toCharArray();
        TrieNode curNode = root;
        
        for (int i = 0; i < l; i++)
        {
            if (curNode.links[letters[i]-offset] == null)
                curNode.links[letters[i]-offset] = new TrieNode(letters[i]);
            curNode = curNode.links[letters[i]-offset];
        }
        curNode.fullWord = true;  
    }

    static boolean find(TrieNode root, TrieNode backRoot, String searchword)
    {
    	String prefix="", sufix="";
    	int index=1;
            if( searchword.contains("*")){
            	foundWildCard= true;
            	index = searchword.indexOf('*', 0);
            	String[] parts = searchword.split("\\*");
            	int searchwordLength=searchword.length()-1;
            	if(index == searchwordLength){
            		if(parts[0].length()>0)
            			prefix = parts[0];
            		searchword= prefix;
            		wordType=0;
            	}
            	else 
            	{
            		prefix = parts[0];
            		sufix = parts[1];
//            		middle=true;
            		wordType=2;
            		find(root, backRoot, prefix);
            		findBack(backRoot, sufix);
            	}
        }
        char[] letters = searchword.toCharArray();
        int l = letters.length;
        TrieNode curNode = root;
        int i,count=0;
        char[] prev=new char[1000];
        for (i = 0; i < l; i++)
        {
            if (curNode == null)
                return false;
            	curNode = curNode.links[letters[i]-97];
            	if(foundWildCard==true)
            	{
	            	int searchlength=searchword.length()-1;
	            	if(i<searchlength)
	            	{
	            		if(curNode != null){
	            			prev[i] = curNode.letter;
	            			count++;
	            		}
	            	}
            	}
        }
        if (i == l && curNode == null)
        	return false;
        char[] branch = new char[50];
        if(foundWildCard){
        	int idx=index-1;
        	printTree(curNode, idx, branch, prev, count, wordType);
        }
        return true;
    }
    private static boolean findBack(TrieNode backTrackTree, String searchWord){
		// TODO Auto-generated method stub
    	String sufix="";
    	int index=1;
    	int idx=0;
    	foundWildCard= true;
    	index = searchWord.indexOf('*', 0);
    	String[] parts = searchWord.split("\\*");
    	if(index==0){
    		if(parts[1].length()>0)
    			sufix = parts[1];
    		searchWord= sufix;
    		idx=parts[1].length()-1;
    		wordType=1;
    	}
        char[] letters = searchWord.toCharArray();
        int l = letters.length;
        TrieNode backTrackTree1 = backTrackTree;
        int i,count=0;
        char[] prev=new char[1000];
        for (i = 0; i < l; i++)
        {
            if (backTrackTree1 == null)
                return false;
            	backTrackTree1 = backTrackTree1.links[letters[i]-97];
            	if(foundWildCard==true)
            	{
	            	int searchlength=searchWord.length()-1;
	            	if(i<=searchlength)
	            	{
	            		if(backTrackTree1 != null){
	            		prev[i] = backTrackTree1.letter;
	            		count++;
	            		}
	            	}
            	}
        }
        if (i == l && backTrackTree1 == null)
        	return false;
        char[] branch = new char[50];
        if(foundWildCard){        
        printTree(backTrackTree1, idx, branch, prev, count, wordType);
        }
        return true;
	}
        
    static void printTree(TrieNode root, int level, char[] branch, char[] prfixTree, int prefixInsertStatus, int type)
    {
    	
    		for(int i=0;i<prefixInsertStatus;i++)
    		{
    			branch[i] = prfixTree[i];
    		}
    	prefixInsertStatus = 0;
        if (root == null)
            return;
        for (int i = 0; i < root.links.length; i++)
        {
            branch[level] = root.letter;
            printTree(root.links[i], level+1, branch, prfixTree, prefixInsertStatus, type);    
        }
        String arr[]= new String[1000];
        if (root.fullWord)
        {
        	if(type==1)
        	{
        		for (int j = level; j>= 0; j--)
                {
                    System.out.print(branch[j]);
                }
        	}
        	else if(type==0)
        	{
	            for (int j = 0; j <= level; j++)
	            {
	                System.out.print(branch[j]);
	            }
        	}
        	
        	else if(type==2){
        		for (int j = 0; j <= level; j++)
	            {
	                System.out.print(branch[j]);
	            }
        		String word = branch.toString();
        		arr[x++]=word;
        	}
        	
        	System.out.println();
        	
        	//calculateMiddle(arr);
        }
    }
    
    static void calculateMiddle(String[] arr){
    	
    }
    
    public static void main(String[] args)
    {
        TrieNode tree = createTree();
        TrieNode backTrackTree = createTree();
        String[] words = {"an", "ant", "allom", "allot","gjtyu", "alloy", "aloe", "are", "alent", "be"};
        for (int i = 0; i < words.length; i++)
        {
            insertWord(tree, words[i]);
        	backtrackingWord(backTrackTree, words[i]);
        }
        String searchWord = "*nt";      
        int l = searchWord.length();
        int pos= searchWord.indexOf("*");
        char[] letters1 = searchWord.toCharArray();
        int j=0;
        if(pos<(l-1)){
            char[] letters = searchWord.toCharArray();
            if(searchWord.contains("*")){
        	String[] parts = searchWord.split("\\*");
       		if(parts[1].length()>0){
       			for(int i= 0;i<l;){
	       				if(i>=(pos+1) && i < l){
	       					j++;
	       					letters1[i] = letters[l-j];  
	       				}
	       				else
	       					letters1[i] = letters[i];
       				i++;
       			}
       		}
       		searchWord = new String(letters1);
        }
     }
        
        if(pos==0)
        {
        	if (findBack(backTrackTree, searchWord))
            {
                System.out.println("The word was found");
            }
            else
            {
                System.out.println("The word was NOT found");
            }
        }
                
        else // if(pos==(l-1) || pos < 0)
        { 
        	if (find(tree, backTrackTree, searchWord))
	        	{
	        		System.out.println("The word was found");
	        	}
        	else
		        {
		            System.out.println("The word was NOT found");
		        }
        }
    }
}
