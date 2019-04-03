package lang.nodes.statements;

import hackscript.antlr.HackScriptParser.ExpressionStatementContext;
import lang.visitors.CSTVisitor;
import lang.Visitor;
import lang.Node;
import lang.nodes.Statement;
import lang.nodes.Expression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * In order to make assignments, call functions etc. We need an expression statement to evaluate
 * arbitrary expressions without storing its return value
 */
public class ExpressionStatement implements Statement {

  private Expression expr;

  @Override
  public String getLiteral() {
    return expr.getLiteral() + ";";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    ExpressionStatementContext actx = Node.checkContext(ctx, ExpressionStatementContext.class);
    setExpression((Expression) visitor.visit(ctx.getChild(0)));

    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(getExpression());
  }

  public Expression getExpression() {
    return expr;
  }

  public void setExpression(Expression expr) {
    this.expr = expr;
  }
}
