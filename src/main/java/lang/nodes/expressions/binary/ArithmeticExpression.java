package lang.nodes.expressions.binary;

import lang.visitors.CSTVisitor;
import lang.nodes.Expression;
import lang.nodes.expressions.BinaryExpression;
import lang.nodes.expressions.CanSetOperator;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form EXPRESSION (+,-,*,/,%) EXPRESSION
 */
public abstract class ArithmeticExpression extends BinaryExpression implements CanSetOperator {

    private String operator;

    @Override
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String getOperator() {
        return this.operator;
    }

    protected void setOperands(ParserRuleContext ctx, CSTVisitor visitor) {
        setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
        setOperator(ctx.children.get(1).toString());
        setSecondOperand((Expression) visitor.visit(ctx.getChild(2)));
    }
}
