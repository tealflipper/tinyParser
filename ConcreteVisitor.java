import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ConcreteVisitor extends WhilelangBaseVisitor<Object> {
  Map<String, Object> memory = new HashMap<String, Object>();

  /** ID '=' expression NEWLINE */
  @Override
  public Double visitAttrib(WhilelangParser.AttribContext ctx) {
    String id = ctx.ID().getText(); // id is left-hand side of '='
    double value = (double) visit(ctx.expression()); // compute value of expression on right
    memory.put(id, value); // store it in our memory
    return value;
  }

  /** expression NEWLINE */
  @Override
  public Double visitPrint(WhilelangParser.PrintContext ctx) {
    String value = ctx.Text().getText().replaceAll("\"", "");; // evaluate the expression child
    System.out.println(value); // print the result  
    return 1.0;
  }

  @Override
  public Double visitWrite(WhilelangParser.WriteContext ctx) {
    double value = (double) visit(ctx.expression()); // evaluate the expression child
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(8);
    System.out.println(df.format(value)); // print the result
    return 1.0; // return dummy value
  }

  /** INT */
  @Override
  public Double visitFloat(WhilelangParser.FloatContext ctx) {
    return Double.valueOf(ctx.FLOAT().getText());
  }

  /** ID */
  @Override
  public Double visitId(WhilelangParser.IdContext ctx) {
    String id = ctx.ID().getText();
    if (memory.containsKey(id))
      return (Double) memory.get(id);
    return 0.0;
  }

  /** expression op=('*'|'/') expression */
  @Override
  public Double visitMulDivPow(WhilelangParser.MulDivPowContext ctx) {
    double left = (double) visit(ctx.expression(0)); // get value of left subexpression
    double right = (double) visit(ctx.expression(1)); // get value of right subexpression
    if (ctx.op.getType() == WhilelangParser.POW)
      return Math.pow(left, right);
    if (ctx.op.getType() == WhilelangParser.MUL)
      return left * right;
    return left / right; // must be DIV
  }

  /** expression op=('+'|'-') expression */
  @Override
  public Double visitAddSub(WhilelangParser.AddSubContext ctx) {
    double left = (double) visit(ctx.expression(0)); // get value of left subexpression
    double right = (double) visit(ctx.expression(1)); // get value of right subexpression
    if (ctx.op.getType() == WhilelangParser.ADD)
      return left + right;
    return left - right; // must be SUB
  }

  /** '(' expression ')' */
  @Override
  public Object visitParens(WhilelangParser.ParensContext ctx) {
    return visit(ctx.expression()); // return child expression's value
  }

  @Override
  public Object visitBlock(WhilelangParser.BlockContext ctx) {
    return visit(ctx.seqStatement()); // return child expression's value
  }

  @Override
  public Double visitPi(WhilelangParser.PiContext ctx) {
    return Math.PI;
  }

  @Override
  public Double visitEuler(WhilelangParser.EulerContext ctx) {
    return Math.E;
  }

  @Override
  public Double visitTrig(WhilelangParser.TrigContext ctx) {
    double expression = (double) visit(ctx.expression()); // get value of subexpression
    if (ctx.op.getType() == WhilelangParser.SIN)
      return Math.sin(expression);
    if (ctx.op.getType() == WhilelangParser.COS)
      return Math.cos(expression);
    return Math.tan(expression); // must be tan
  }

  @Override
  public Double visitInvTrig(WhilelangParser.InvTrigContext ctx) {
    double expression = (double) visit(ctx.expression()); // get value of subexpression
    if (ctx.op.getType() == WhilelangParser.ASIN)
      return Math.asin(expression);
    if (ctx.op.getType() == WhilelangParser.ACOS)
      return Math.cos(expression);
    return Math.tan(expression); // must be tan
  }

  @Override
  public Double visitSqrt(WhilelangParser.SqrtContext ctx) {
    double value = (double) visit(ctx.expression());
    return Math.sqrt(value);
  }

  @Override
  public Double visitLog(WhilelangParser.LogContext ctx) {
    double value = (double) visit(ctx.expression());
    if (ctx.op.getType() == WhilelangParser.LOG10)
      return Math.log10(value);
    return Math.log(value);
  }

  @Override public Boolean visitBoolean(WhilelangParser.BooleanContext ctx) {
    //system.out.println(Boolean.valueOf(ctx.getText())); 
    return Boolean.valueOf(ctx.getText());
  }

	@Override 
  public Object visitBoolParen(WhilelangParser.BoolParenContext ctx) {

    return visit(ctx.bool()); 
  }

  @Override 
  public Boolean visitAnd(WhilelangParser.AndContext ctx) {
    Boolean first = (Boolean) visit(ctx.bool(0));
    Boolean second = (Boolean) visit(ctx.bool(1));
    //system.out.println("First" + first + "Second" + second);
    return first && second; 
  }

  @Override 
  public Boolean visitNot(WhilelangParser.NotContext ctx) { 
    Boolean value = (Boolean) visit(ctx.bool());
    //system.out.println("Bool: " +  !value);
    return !value; 
  }


  @Override 
  public Boolean visitRelOp(WhilelangParser.RelOpContext ctx) { 
    String op = String.valueOf(ctx.op.getText());
    ////system.out.println("Operador" + op.equals("<="));
    Double val1 = (Double) visit(ctx.expression(0));
    Double val2 = (Double) visit(ctx.expression(1));
    Boolean res = false;

    switch (op){
      case "<=":
         res = val1 <= val2; 
        break;
      case "=":
        res = val1 == val2;
        break;
       
    }
    ////system.out.println("Este es res relop:" + res);  
    return res; 
  }

  @Override 
  public Object visitIf(WhilelangParser.IfContext ctx) { 
    if((Boolean)visit(ctx.bool())){
      return visit(ctx.statement(0));
    }else{
      return visit(ctx.statement(1));
    }
    
  }

  @Override 
  public Object visitWhile(WhilelangParser.WhileContext ctx) { 
    Object value=null;
    while((Boolean)visit(ctx.bool())){
      value = visit(ctx.statement());
    }
    return value; 
  }

  @Override 
  public Object visitRead(WhilelangParser.ReadContext ctx) { 
    String inputString = ctx.getText();
    System.out.println(inputString);
    
		try {
			
				BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
				return new String(buffer.readLine());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

//*/
    
  }

  

}