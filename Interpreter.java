import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class Interpreter {
  Interpreter(){

  }

  public double interpret(String inputFile) throws Exception{
    InputStream is = System.in;
    if (inputFile != null) is = new FileInputStream(inputFile);
    ANTLRInputStream input = new ANTLRInputStream(is); 
    WhilelangLexer lexer = new WhilelangLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    WhilelangParser parser = new WhilelangParser(tokens);
    ParseTree tree = parser.program(); // parse; start at prog ​
    ConcreteVisitor eval = new ConcreteVisitor();
    double val = eval.visit(tree);
    System.out.println("value: "+val);
    return val;
  }
}
