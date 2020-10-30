package treeBuild;
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
	
	Node root = new Node("root");
    root.addChild(new Node("child1"));
    root.addChild(new Node("child2")); //etc.
    
    
    
    root.printChild();
}
}
