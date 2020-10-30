package readFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadWithScanner {
public static void main(String[] args) throws IOException {
	ReadWithScanner parser = new ReadWithScanner("F:\\Education\\8th_semester\\Paper\\IR Paper\\Code\\WildCard Search\\src\\readFile\\javaParsingExample.txt");
	parser.processLineByLine();
}

public ReadWithScanner(String aFileName){
    fFilePath = Paths.get(aFileName);
  }


/** Template method that calls {@link #processLine(String)}.  */
public final void processLineByLine() throws IOException {
  try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
    while (scanner.hasNextLine()){
      processLine(scanner.nextLine());
    }      
  }
}

protected void processLine(String aLine){
    scanner = new Scanner(aLine);
    scanner.useDelimiter(" ");
    while (scanner.hasNext()){
      //assumes the line has a certain structure
     // String name = scanner.next();
     // String value = scanner.next();
      log(scanner.nextLine());
    }
  }
  
// PRIVATE 
private final Path fFilePath;
private final static Charset ENCODING = StandardCharsets.UTF_8;
private Scanner scanner;  

private static void log(Object aObject){
  System.out.println(String.valueOf(aObject));
}

private String quote(String aText){
  String QUOTE = "'";
  return QUOTE + aText + QUOTE;
}


}
