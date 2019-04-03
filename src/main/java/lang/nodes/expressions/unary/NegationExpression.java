package lang.nodes.expressions.unary;


import hackscript.antlr.HackScriptParser.UnaryExpressionContext;
import lang.visitors.CSTVisitor;
import lang.ReturnType;
import lang.Types;
import lang.Visitor;
import lang.nodes.Expression;
import lang.Node;
import lang.nodes.expressions.UnaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form !a
 */
public class NegationExpression implements UnaryExpression {

  private Expression firstOperand;

  /**
   * Default constructor
   */
  public NegationExpression() {

  }

  /**
   * Default constructor
   *
   * @param firstOperand The operand to negate
   */
  public NegationExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
  }

  @Override
  public Expression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = firstOperand;
  }

  @Override
  public String getOperator() {
    return "!";
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkContext(ctx, UnaryExpressionContext.class);

    setFirstOperand((Expression) visitor.visit(ctx.getChild(1)));

    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(getFirstOperand());
  }

  @Override
  public ReturnType getReturnType() {
    return new ReturnType(Types.BOOL);
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    // TODO: this!!
  }
}
