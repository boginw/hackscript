package lang.nodes.expressions.leftHandSide;

import hackscript.antlr.HackScriptParser.PostfixExpressionContext;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.ReturnType;
import lang.nodes.Expression;
import lang.nodes.expressions.BinaryExpression;
import lang.nodes.expressions.LeftHandSideExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form a[0]. This class represents all array indexing
 */
public class ArrayExpression extends BinaryExpression implements LeftHandSideExpression {

    private ReturnType returnReturnType;

    @Override
    public String getLiteral() {
        return this.getFirstOperand().getLiteral() + "[" + this.getSecondOperand().getLiteral() + "]";
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        PostfixExpressionContext actx = Node.checkContext(ctx, PostfixExpressionContext.class);

        //This is flawed, since it doesn't support (*a)[2]
        setFirstOperand((Expression) visitor.visitPostfixExpression(actx.postfixExpression()));
        setSecondOperand((Expression) visitor.visitExpression(actx.expression()));

        return this;
    }

    @Override
    public String getOperator() {
        return "";
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
