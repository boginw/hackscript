package lang;

import lang.nodes.*;
import lang.nodes.expressions.BinaryExpression;
import lang.nodes.expressions.InitializerList;
import lang.nodes.expressions.UnaryExpression;
import lang.nodes.expressions.binary.AssignmentExpression;
import lang.nodes.expressions.binary.arithmetic.AdditiveExpression;
import lang.nodes.expressions.binary.arithmetic.MultiplicativeExpression;
import lang.nodes.expressions.binary.logical.LogicalAndExpression;
import lang.nodes.expressions.binary.logical.LogicalEqualityExpression;
import lang.nodes.expressions.binary.logical.LogicalOrExpression;
import lang.nodes.expressions.binary.logical.LogicalRelantionalExpression;
import lang.nodes.expressions.leftHandSide.ArrayExpression;
import lang.nodes.expressions.leftHandSide.DereferenceExpression;
import lang.nodes.expressions.leftHandSide.VariableExpression;
import lang.nodes.expressions.literal.BoolLiteral;
import lang.nodes.expressions.literal.CharLiteral;
import lang.nodes.expressions.literal.IntLiteral;
import lang.nodes.expressions.unary.*;
import lang.nodes.statements.CompoundStatement;
import lang.nodes.statements.ExpressionStatement;
import lang.nodes.statements.RawStatement;
import lang.nodes.statements.VariableDeclaration;
import lang.nodes.statements.jump.BreakStatement;
import lang.nodes.statements.jump.ContinueStatement;
import lang.nodes.statements.jump.ReturnStatement;
import lang.nodes.statements.selection.IfStatement;
import lang.nodes.statements.selection.WhileStatement;

public interface Visitor<T> {

    // Nodes
    T visit(Node node);

    // Expressions
    T visit(Expression expression);

    // Literals
    T visit(BoolLiteral boolLiteral);

    T visit(IntLiteral intLiteral);

    T visit(CharLiteral charLiteral);

    // Unary
    T visit(UnaryExpression expression);

    T visit(AdditivePrefixExpression expression);

    T visit(AddressOfExpression addressOfExpression);

    T visit(CastExpression expression);

    T visit(FunctionExpression expression);

    T visit(IncrementDecrementExpression expression);

    T visit(NegationExpression expression);

    // Binary Expressions
    T visit(BinaryExpression binaryExpression);

    T visit(AdditiveExpression additiveExpression);

    T visit(MultiplicativeExpression multiplicativeExpression);

    T visit(LogicalRelantionalExpression logicalRelantionalExpression);

    T visit(LogicalAndExpression logicalAndExpression);

    T visit(LogicalEqualityExpression logicalEqualityExpression);

    T visit(LogicalOrExpression logicalOrExpression);

    T visit(AssignmentExpression assignmentExpression);

    // LHS Expressions
    T visit(ArrayExpression expression);

    T visit(DereferenceExpression expression);

    T visit(VariableExpression variableExpression);

    // Other expressions
    T visit(InitializerList initializerList);

    // Statements
    T visit(Statement statement);

    // Jump statements
    T visit(BreakStatement breakStatement);

    T visit(ContinueStatement continueStatement);

    T visit(ReturnStatement returnStatement);

    // Iteration / Selection Statements
    T visit(IfStatement ifStatement);

    T visit(WhileStatement whileStatement);

    // Other statements
    T visit(CompoundStatement compoundStatement);

    T visit(ExpressionStatement expressionStatement);

    T visit(VariableDeclaration variableDeclaration);

    T visit(RawStatement rawStatement);

    // MISC
    T visit(ArgumentExpressionList expressionList);

    T visit(DeclarationSpecifierList declarationSpecifierList);

    T visit(FunctionDeclaration functionDeclaration);

    T visit(Program program);

    static <T> T concreteify(Node node, Visitor<T> visitor) {
        if (node instanceof Expression) {
            return Visitor.concreteify(node, visitor);
        } else if (node instanceof Statement) {
            return Visitor.concreteify(node, visitor);
        } else if (node instanceof ArgumentExpressionList) {
            return visitor.visit((ArgumentExpressionList) node);
        } else if (node instanceof DeclarationSpecifierList) {
            return visitor.visit((DeclarationSpecifierList) node);
        } else if (node instanceof FunctionDeclaration) {
            return visitor.visit((FunctionDeclaration) node);
        } else if (node instanceof Program) {
            return visitor.visit((Program) node);
        } else {
            throw new RuntimeException(
                    "Expression Not Found In ArduinoCodeGenerator visit(Expression expression) Expression: "
                            + node.getClass().getName());
        }
    }

    static <T> T concreteify(Expression expression, Visitor<T> visitor) {
        if (expression instanceof BoolLiteral) {
            return visitor.visit((BoolLiteral) expression);
        } else if (expression instanceof IntLiteral) {
            return visitor.visit((IntLiteral) expression);
        } else if (expression instanceof CharLiteral) {
            return visitor.visit((CharLiteral) expression);
        } else if (expression instanceof AdditivePrefixExpression) {
            return visitor.visit((AdditivePrefixExpression) expression);
        } else if (expression instanceof AddressOfExpression) {
            return visitor.visit((AddressOfExpression) expression);
        } else if (expression instanceof CastExpression) {
            return visitor.visit((CastExpression) expression);
        } else if (expression instanceof FunctionExpression) {
            return visitor.visit((FunctionExpression) expression);
        } else if (expression instanceof IncrementDecrementExpression) {
            return visitor.visit((IncrementDecrementExpression) expression);
        } else if (expression instanceof NegationExpression) {
            return visitor.visit((NegationExpression) expression);
        } else if (expression instanceof AdditiveExpression) {
            return visitor.visit((AdditiveExpression) expression);
        } else if (expression instanceof MultiplicativeExpression) {
            return visitor.visit((MultiplicativeExpression) expression);
        } else if (expression instanceof LogicalRelantionalExpression) {
            return visitor.visit((LogicalRelantionalExpression) expression);
        } else if (expression instanceof LogicalAndExpression) {
            return visitor.visit((LogicalAndExpression) expression);
        } else if (expression instanceof LogicalEqualityExpression) {
            return visitor.visit((LogicalEqualityExpression) expression);
        } else if (expression instanceof LogicalOrExpression) {
            return visitor.visit((LogicalOrExpression) expression);
        } else if (expression instanceof AssignmentExpression) {
            return visitor.visit((AssignmentExpression) expression);
        } else if (expression instanceof ArrayExpression) {
            return visitor.visit((ArrayExpression) expression);
        } else if (expression instanceof DereferenceExpression) {
            return visitor.visit((DereferenceExpression) expression);
        } else if (expression instanceof VariableExpression) {
            return visitor.visit((VariableExpression) expression);
        } else if (expression instanceof InitializerList) {
            return visitor.visit((InitializerList) expression);
        } else {
            throw new RuntimeException(
                    "Expression Not Found In ArduinoCodeGenerator visit(Expression expression) Expression: "
                            + expression.getClass().getName());
        }
    }

    static <T> T concreteify(Statement statement, Visitor<T> visitor) {
        if (statement instanceof BreakStatement) {
            return visitor.visit((BreakStatement) statement);
        } else if (statement instanceof ContinueStatement) {
            return visitor.visit((ContinueStatement) statement);
        } else if (statement instanceof ReturnStatement) {
            return visitor.visit((ReturnStatement) statement);
        } else if (statement instanceof IfStatement) {
            return visitor.visit((IfStatement) statement);
        } else if (statement instanceof WhileStatement) {
            return visitor.visit((WhileStatement) statement);
        } else if (statement instanceof CompoundStatement) {
            return visitor.visit((CompoundStatement) statement);
        } else if (statement instanceof ExpressionStatement) {
            return visitor.visit((ExpressionStatement) statement);
        } else if (statement instanceof VariableDeclaration) {
            return visitor.visit((VariableDeclaration) statement);
        } else if (statement instanceof RawStatement) {
            return visitor.visit((RawStatement) statement);
        } else {
            throw new RuntimeException(
                    "Statement Not Found In ArduinoCodeGenerator visit(Statement statement) Statement: "
                            + statement.getClass().getName());
        }
    }
}
