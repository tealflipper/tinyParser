import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ConcreteVisitor extends WhilelangBaseVisitor<Double> {
  Map<String, Double> memory = new HashMap<String, Double>();

  /** ID '=' expression NEWLINE */
  @Override
  public Double visitAttrib(WhilelangParser.AttribContext ctx) {
    String id = ctx.ID().getText(); // id is left-hand side of '='
    double value = visit(ctx.expression()); // compute value of expression on right
    memory.put(id, value); // store it in our memory
    return value;
  }

  /** expression NEWLINE */
  @Override
  public Double visitPrint(WhilelangParser.PrintContext ctx) {
    String value = ctx.Text().getText().replaceAll("\"", "");; // evaluate the expression child
    System.out.print(value); // print the result
    return 1.0; // return dummy value
    
  }

  @Override
  public Double visitWrite(WhilelangParser.WriteContext ctx) {
    double value = visit(ctx.expression()); // evaluate the expression child
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
      return memory.get(id);
    return 0.0;
  }

  /** expression op=('*'|'/') expression */
  @Override
  public Double visitMulDivPow(WhilelangParser.MulDivPowContext ctx) {
    double left = visit(ctx.expression(0)); // get value of left subexpression
    double right = visit(ctx.expression(1)); // get value of right subexpression
    if (ctx.op.getType() == WhilelangParser.POW)
      return Math.pow(left, right);
    if (ctx.op.getType() == WhilelangParser.MUL)
      return left * right;
    return left / right; // must be DIV
  }

  /** expression op=('+'|'-') expression */
  @Override
  public Double visitAddSub(WhilelangParser.AddSubContext ctx) {
    double left = visit(ctx.expression(0)); // get value of left subexpression
    double right = visit(ctx.expression(1)); // get value of right subexpression
    if (ctx.op.getType() == WhilelangParser.ADD)
      return left + right;
    return left - right; // must be SUB
  }

  /** '(' expression ')' */
  @Override
  public Double visitParens(WhilelangParser.ParensContext ctx) {
    return visit(ctx.expression()); // return child expression's value
  }

  @Override
  public Double visitBlock(WhilelangParser.BlockContext ctx) {
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
    double expression = visit(ctx.expression()); // get value of subexpression
    if (ctx.op.getType() == WhilelangParser.SIN)
      return Math.sin(expression);
    if (ctx.op.getType() == WhilelangParser.COS)
      return Math.cos(expression);
    return Math.tan(expression); // must be tan
  }

  @Override
  public Double visitInvTrig(WhilelangParser.InvTrigContext ctx) {
    double expression = visit(ctx.expression()); // get value of subexpression
    if (ctx.op.getType() == WhilelangParser.ASIN)
      return Math.asin(expression);
    if (ctx.op.getType() == WhilelangParser.ACOS)
      return Math.cos(expression);
    return Math.tan(expression); // must be tan
  }

  @Override
  public Double visitSqrt(WhilelangParser.SqrtContext ctx) {
    double value = visit(ctx.expression());
    return Math.sqrt(value);
  }

  @Override
  public Double visitLog(WhilelangParser.LogContext ctx) {
    double value = visit(ctx.expression());
    if (ctx.op.getType() == WhilelangParser.LOG10)
      return Math.log10(value);
    return Math.log(value);
  }
}
