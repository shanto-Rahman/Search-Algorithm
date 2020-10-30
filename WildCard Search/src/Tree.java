public class Tree {
	 Object root;
	 Tree left, right;
	 public Tree (Object root, Tree left, Tree right) {
	     this.root = root; 
	     this.left = left; 
	     this.right = right;
	    }
	 public static int total (Tree tree) { 
	        if (tree == null) return 1; 
	        Integer root = (Integer) tree.root;
	        //System.out.println();
	        return root.intValue() + total (tree.left) + total (tree.right); 
	    } 
	 
	  public static void print (Tree tree) { 
	        if (tree == null) return;
	        Integer cargo = (Integer) tree.root;
	        System.out.print (tree + " "); 
	        print (tree.left); 
	        print (tree.right); 
	    }
	  
	   public static void printPostorder (Tree tree) { 
	        if (tree == null) return;
	        Integer cargo = (Integer) tree.root;
	        printPostorder (tree.left); 
	        printPostorder (tree.right); 
	        System.out.print (tree + " "); 
	    }
	   
	   public static void printInorder (Tree tree) { 
	        if (tree == null) return; 
	        printInorder (tree.left); 
	        System.out.print (tree + " "); 
	        printInorder (tree.right); 
	    } 
	 
}
