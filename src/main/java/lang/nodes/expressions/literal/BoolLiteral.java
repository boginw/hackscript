package lang.nodes.expressions.literal;

import hackscript.antlr.HackScriptParser.PrimaryExpressionContext;
import lang.*;
import lang.nodes.expressions.LiteralExpression;
import lang.visitors.CSTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class BoolLiteral implements LiteralExpression<Boolean> {
    private boolean value;

    public BoolLiteral() {
    }

    /**
     * The default constructor
     *
     * @param value The boolean value
     */
    public BoolLiteral(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public String getLiteral() {
        return String.valueOf(value);
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        PrimaryExpressionContext actx = Node.checkContext(ctx, PrimaryExpressionContext.class);
        setValue(actx.BoolLiteral().getChild(0).toString().equals("true"));

        return this;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ReturnType getReturnType() {
        return new ReturnType(Types.BOOL);
    }

    @Override
    public void setReturnType(ReturnType returnReturnType) {
        throw new RuntimeException("Cannot set returnReturnType of a boolean literal");
    }
}
