public class Mamalona {
  
  public static void main(String[] args) throws Exception{
    Interpreter intp = new Interpreter();
    String str = "mamalon.exp";
    intp.interpret(str);
  }
}
