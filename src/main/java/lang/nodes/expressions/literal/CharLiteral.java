package lang.nodes.expressions.literal;

import lang.*;
import lang.nodes.expressions.LiteralExpression;
import hackscript.antlr.HackScriptParser.PrimaryExpressionContext;
import lang.visitors.CSTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the domain of chars ('a', 'b' '\n')
 */
public class CharLiteral implements LiteralExpression<String> {
    private String value;

    public CharLiteral() {
    }

    /**
     * Default constructor
     *
     * @param value The character literal
     */
    public CharLiteral(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getLiteral() {
        return "\'" + String.valueOf(this.value) + "\'";
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        PrimaryExpressionContext actx = Node.checkContext(ctx, PrimaryExpressionContext.class);
        setValue(actx.CharLiteral().getSymbol().getText());

        return this;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ReturnType getReturnType() {
        return new ReturnType(Types.CHAR);
    }

    @Override
    public void setReturnType(ReturnType returnReturnType) {
        throw new RuntimeException("Cannot set return type of a character literal");
    }
}
