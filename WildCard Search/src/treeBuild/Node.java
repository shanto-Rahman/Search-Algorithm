package treeBuild;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private List<Node> children = null;
    private String value;

    public Node(String value)
    {
        this.children = new ArrayList<>();
        this.value = value;
    }

    public void addChild(Node child)
    {
        children.add(child);
    }
    
    public void printChild(){
    	//int size= children.size();
    	for (int i = 0; i < children.size(); i++) {
    	    Node value1 = children.get(i);
    	    System.out.println("Element: " + value1.value);
    	}
    }

}
