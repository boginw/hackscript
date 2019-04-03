package lang.nodes.expressions.unary;

import hackscript.antlr.HackScriptParser.UnaryExpressionContext;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.ReturnType;
import lang.Visitor;
import lang.nodes.Expression;
import lang.nodes.expressions.CanSetOperator;
import lang.nodes.expressions.UnaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form +1 and -1
 */
public class AdditivePrefixExpression implements UnaryExpression, CanSetOperator {

  private ReturnType returnType;
  private Expression firstOperand;
  private String operator;

  /**
   * Default constructor
   */
  public AdditivePrefixExpression() {

  }

  /**
   * Default constructor
   *
   * @param firstOperand The operand
   * @param operator The operator
   */
  public AdditivePrefixExpression(Expression firstOperand, String operator) {
    setFirstOperand(firstOperand);
    setOperator(operator);
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
    return operator;
  }

  @Override
  public String getLiteral() {
    return getOperator() + getFirstOperand().getLiteral();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    UnaryExpressionContext actx = Node.checkContext(ctx, UnaryExpressionContext.class);

    setFirstOperand((Expression) visitor.visit(ctx.getChild(1)));
    setOperator(actx.unaryOperator().getText());

    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(getFirstOperand());
  }

  @Override
  public void setOperator(String operator) {
    this.operator = operator;
  }

  @Override
  public ReturnType getReturnType() {
    return returnType;
  }

  @Override
  public void setReturnType(ReturnType returnType) {
    this.returnType = returnType;
  }
}
