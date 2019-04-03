package lang.nodes.statements.selection;

import hackscript.antlr.HackScriptParser.IterationStatementContext;
import lang.visitors.CSTVisitor;
import lang.Visitor;
import lang.Node;
import lang.nodes.Statement;
import lang.nodes.Expression;
import lang.nodes.statements.SelectionStatement;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents a statement of the form: while(condition) statements
 */
public class WhileStatement implements SelectionStatement {

  private Expression condition;
  private Statement consequence;

  @Override
  public Expression getCondition() {
    return condition;
  }

  @Override
  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  @Override
  public Statement getConsequence() {
    return consequence;
  }

  @Override
  public void setConsequence(Statement consequence) {
    this.consequence = consequence;
  }

  @Override
  public String getLiteral() {
    return "while(" + condition.getLiteral() + ") " + consequence.getLiteral();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkContext(ctx, IterationStatementContext.class);

    setCondition((Expression) visitor.visit(ctx.getChild(2)));
    setConsequence((Statement) visitor.visit(ctx.getChild(4)));

    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(condition);
    visitor.visit(consequence);
  }
}
