package lang.nodes.expressions.binary;

import lang.ReturnType;
import lang.Types;
import lang.nodes.expressions.BinaryExpression;

/**
 * This class represents an expression of the form EXPRESSION (>=, >, <=, <, &&, ||) EXPRESSION
 */
public abstract class LogicalExpression extends BinaryExpression {

    @Override
    public ReturnType getReturnType() {
        return new ReturnType(Types.BOOL);
    }
}
