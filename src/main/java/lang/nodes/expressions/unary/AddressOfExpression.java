package lang.nodes.expressions.unary;


import hackscript.antlr.HackScriptParser.UnaryExpressionContext;
import lang.visitors.CSTVisitor;
import lang.ReturnType;
import lang.Visitor;
import lang.nodes.Expression;
import lang.Node;
import lang.nodes.expressions.LeftHandSideExpression;
import lang.nodes.expressions.UnaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form &a
 */
public class AddressOfExpression implements UnaryExpression {

  private ReturnType returnReturnType;
  private LeftHandSideExpression firstOperand;

  /**
   * Default constructor
   */
  public AddressOfExpression() {
  }

  /**
   * The default constructor
   *
   * @param firstOperand The operand to take the address of
   */
  public AddressOfExpression(Expression firstOperand) {
    setFirstOperand(firstOperand);
  }

  @Override
  public LeftHandSideExpression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (LeftHandSideExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return "&";
  }

  @Override
  public String getLiteral() {
    return getOperator() + firstOperand.getLiteral();
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
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }
}
