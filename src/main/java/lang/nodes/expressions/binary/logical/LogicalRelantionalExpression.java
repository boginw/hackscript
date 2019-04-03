package lang.nodes.expressions.binary.logical;


import hackscript.antlr.HackScriptParser.RelationalExpressionContext;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.nodes.Expression;
import lang.nodes.expressions.binary.LogicalExpression;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class LogicalRelantionalExpression extends LogicalExpression {

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkContext(ctx, RelationalExpressionContext.class);
    setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
    setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));
    return this;
  }
}
