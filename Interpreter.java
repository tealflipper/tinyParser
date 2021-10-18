import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class Interpreter {
  Interpreter(){

  }

  public double interpret(String inputFile){
    InputStream is = System.in;
    if (inputFile != null) is = new FileInputStream(inputFile);
    ANTLRInputStream input = new ANTLRInputStream(is); 
    WhileLangLexer lexer = new WhileLangLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    WhileLangParser parser = new WhileLangParser(tokens);
    ParseTree tree = parser.prog(); // parse; start at prog ​
    ConcreteVisitor eval = new ConcreteVisitor();
    double val = eval.visit(tree);
    System.out.println("value: "+val);
  }
}
