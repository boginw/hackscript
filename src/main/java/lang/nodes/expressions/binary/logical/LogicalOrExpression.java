package lang.nodes.expressions.binary.logical;


import hackscript.antlr.HackScriptParser.LogicalAndExpressionContext;
import hackscript.antlr.HackScriptParser.LogicalOrExpressionContext;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.nodes.Expression;
import lang.nodes.expressions.binary.LogicalExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form true || true
 */
public class LogicalOrExpression extends LogicalExpression {

  @Override
  public String getOperator() {
    return "||";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    LogicalOrExpressionContext actx = Node.checkContext(ctx, LogicalOrExpressionContext.class);

    if (ctx.getChild(0) instanceof LogicalAndExpressionContext) {
      return visitor.visitLogicalAndExpression(actx.logicalAndExpression());
    }

    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));

    return this;
  }
}
