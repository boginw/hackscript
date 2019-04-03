package lang.nodes.expressions.binary.logical.relational;


import lang.nodes.expressions.binary.logical.LogicalRelantionalExpression;

/**
 * This class represents an expression of the form 1 > 1
 */
public class LogicalGreaterExpression extends LogicalRelantionalExpression {

  @Override
  public String getOperator() {
    return ">";
  }

}
