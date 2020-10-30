import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Main {
public static void main(String args[]) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("dog", "type of animal");
	map.put("cat", "Ha ha");
	map.put("dog1", "typePe");
	map.put("cat1", "Ha ha");
	/*Tree left = new Tree ("left", null, null); 
	Tree right = new Tree ("right", null, null);
	Tree tree = new Tree ("Root", left, right);
	String value = tree.total(tree);
	System.out.println(value);*/
	Tree left = new Tree (new Integer(2), null, null); 
    Tree right = new Tree (new Integer(3), null, null); 
    Tree tree = new Tree (new Integer(1), left, right); 
    
    int value= tree.total(tree);
   // tree.print(tree);
   // tree.printPostorder(tree);
    //tree.printInorder(tree);
    //int value= tree.total(tree);
	System.out.println(value);
    
/*
	System.out.println("Plz give a input");
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
	String text = in.readLine();
	System.out.println(map.get(text));*/
}
}
