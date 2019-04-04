package lang.nodes.expressions.binary.logical;


import hackscript.antlr.HackScriptParser.RelationalExpressionContext;
import hackscript.antlr.HackScriptParser.EqualityExpressionContext;
import lang.nodes.expressions.CanSetOperator;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.nodes.Expression;
import lang.nodes.expressions.binary.LogicalExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form 1 == 1
 */
public class LogicalEqualityExpression extends LogicalExpression implements CanSetOperator {

  private String operator;

  @Override
  public String getOperator() {
    return operator;
  }

  @Override
  public void setOperator(String operator) {
    this.operator = operator;
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    EqualityExpressionContext actx = Node.checkContext(ctx, EqualityExpressionContext.class);

    if (ctx.getChild(0) instanceof RelationalExpressionContext) {
      return visitor.visitRelationalExpression(actx.relationalExpression());
    }

    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));
    setOperator(ctx.getChild(1).getText());
    return this;
  }


}