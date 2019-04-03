package lang.nodes.expressions.binary.arithmetic;

import hackscript.antlr.HackScriptParser.AdditiveExpressionContext;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.nodes.expressions.binary.ArithmeticExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form 1 + 1, and 1 - 1
 */
public class AdditiveExpression extends ArithmeticExpression {

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        AdditiveExpressionContext actx = Node.checkContext(ctx, AdditiveExpressionContext.class);

        if (ctx.children.size() == 1) {
            return visitor.visitMultiplicativeExpression(actx.multiplicativeExpression());
        }

        setOperands(ctx, visitor);

        return this;
    }
}