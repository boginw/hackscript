package lang.nodes.expressions;

/**
 * This interface is to be used where an expression CAN set the operator
 */
public interface CanSetOperator {

    /**
     * Sets the operator of the expression
     *
     * @param operator The operator of the expression
     */
    void setOperator(String operator);
}
