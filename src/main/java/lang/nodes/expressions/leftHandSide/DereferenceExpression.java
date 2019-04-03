package lang.nodes.expressions.leftHandSide;

import hackscript.antlr.HackScriptParser.UnaryExpressionContext;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.ReturnType;
import lang.Visitor;
import lang.nodes.Expression;
import lang.nodes.expressions.LeftHandSideExpression;
import lang.nodes.expressions.UnaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form *a. This class represents the dereferencing of an
 * identifier
 */
public class DereferenceExpression implements LeftHandSideExpression, UnaryExpression {

    private ReturnType returnReturnType;
    private Expression firstOperand;

    /**
     * Default constructor
     */
    public DereferenceExpression() {
    }

    /**
     * Default constructor
     *
     * @param firstOperand The operand to dereference
     */
    public DereferenceExpression(Expression firstOperand) {
        setFirstOperand(firstOperand);
    }

    @Override
    public Expression getFirstOperand() {
        return firstOperand;
    }

    @Override
    public void setFirstOperand(Expression firstOperand) {
        this.firstOperand = firstOperand;
    }

    @Override
    public String getOperator() {
        return "*";
    }

    @Override
    public String getLiteral() {
        return getOperator() + getFirstOperand().getLiteral();
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
