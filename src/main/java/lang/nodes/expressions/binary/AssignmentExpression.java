package lang.nodes.expressions.binary;

import hackscript.antlr.HackScriptParser;
import hackscript.antlr.HackScriptParser.AssignmentExpressionContext;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.nodes.Expression;
import lang.nodes.expressions.BinaryExpression;
import lang.nodes.expressions.LeftHandSideExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form a = true
 */
public class AssignmentExpression extends BinaryExpression {
    @Override
    public String getOperator() {
        return "=";
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        AssignmentExpressionContext actx = Node.checkContext(ctx, AssignmentExpressionContext.class);

        if (ctx.getChild(0) instanceof HackScriptParser.LogicalOrExpressionContext) {
            return visitor.visitLogicalOrExpression(actx.logicalOrExpression());
        }

        LeftHandSideExpression ld = (LeftHandSideExpression) visitor.visit(actx.unaryExpression());

        if (ld == null) {
            throw new RuntimeException("Left side of assignment is not LHS expression");
        }

        setFirstOperand(ld);
        setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));

        return this;

    }
}
