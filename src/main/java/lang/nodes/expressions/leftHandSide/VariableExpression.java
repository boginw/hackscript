package lang.nodes.expressions.leftHandSide;

import hackscript.antlr.HackScriptParser.PrimaryExpressionContext ;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.ReturnType;
import lang.Visitor;
import lang.nodes.Identifier;
import lang.nodes.expressions.HasDefinition;
import lang.nodes.expressions.LeftHandSideExpression;
import lang.nodes.statements.VariableDeclaration;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an variable used in an expression.
 */
public class VariableExpression implements LeftHandSideExpression, Identifier, HasDefinition<VariableDeclaration> {

    private String identifier;
    private VariableDeclaration definition;

    public VariableExpression() {
    }

    /**
     * Default constructor
     *
     * @param identifier Identifier of the variable
     * @param definition The definition of the variable
     */
    public VariableExpression(String identifier, VariableDeclaration definition) {
        this.identifier = identifier;
        this.definition = definition;
    }

    @Override
    public String getLiteral() {
        return identifier;
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        PrimaryExpressionContext actx = Node.checkContext(ctx, PrimaryExpressionContext.class);
        String id = actx.Identifier().getText();

        VariableDeclaration def = (VariableDeclaration) visitor.getGlobal().lookup(id);

        if (def == null) {
            throw new RuntimeException(
                    "Variable Definition was not found in Variable " + id);
        }

        setDefinition(def);
        setIdentifier(id);

        return this;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void setLocation(int location) {
        definition.setLocation(location);
    }

    @Override
    public int getLocation() {
        return definition.getLocation();
    }

    @Override
    public VariableDeclaration getDefinition() {
        return this.definition;
    }

    @Override
    public void setDefinition(VariableDeclaration definition) {
        this.definition = definition;
    }

    @Override
    public ReturnType getReturnType() {
        return definition.getReturnType();
    }

    @Override
    public void setReturnType(ReturnType returnReturnType) {
        definition.setReturnType(returnReturnType);
    }
}