package lang.nodes.statements.jump;

import hackscript.antlr.HackScriptParser.JumpStatementContext;
import lang.visitors.CSTVisitor;
import lang.Visitor;
import lang.Node;
import lang.nodes.statements.JumpStatement;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * The continue statement, used in loops
 */
public class ContinueStatement implements JumpStatement {

  @Override
  public String getLiteral() {
    return "continue;";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkContext(ctx, JumpStatementContext.class);
    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(this);
  }
}
