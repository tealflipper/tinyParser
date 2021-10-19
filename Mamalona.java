public class Mamalona {
  
  public static void main(String[] args) throws Exception{
    Interpreter intp = new Interpreter();
    String str = args[0];
    intp.interpret(str);
  }
}
