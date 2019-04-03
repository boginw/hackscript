package lang.nodes.expressions.literal;

import hackscript.antlr.HackScriptParser.PrimaryExpressionContext;
import lang.*;
import lang.nodes.expressions.LiteralExpression;
import lang.visitors.CSTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the domain of integer numbers
 */
public class IntLiteral implements LiteralExpression<Integer> {
    private int value;

    public IntLiteral() {
    }

    /**
     * The default constructor
     *
     * @param value The integer value
     */
    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = Integer.valueOf(value.toString());
    }

    @Override
    public String getLiteral() {
        return String.valueOf(value);
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        PrimaryExpressionContext actx = Node.checkContext(ctx, PrimaryExpressionContext.class);
        setValue(Integer.valueOf(actx.IntLiteral().getSymbol().getText()));

        return this;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ReturnType getReturnType() {
        return new ReturnType(Types.INT);
    }

    @Override
    public void setReturnType(ReturnType returnReturnType) {
        throw new RuntimeException("Cannot set return type of an integer literal");
    }
}
