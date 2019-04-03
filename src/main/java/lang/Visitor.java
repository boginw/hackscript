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

public interface Visitor {

    // Nodes
    void visit(Node node);

    // Expressions
    void visit(Expression expression);

    // Literals
    void visit(BoolLiteral boolLiteral);

    void visit(IntLiteral intLiteral);

    void visit(CharLiteral charLiteral);

    // Unary
    void visit(UnaryExpression expression);

    void visit(AdditivePrefixExpression expression);

    void visit(AddressOfExpression addressOfExpression);

    void visit(CastExpression expression);

    void visit(FunctionExpression expression);

    void visit(IncrementDecrementExpression expression);

    void visit(NegationExpression expression);

    // Binary Expressions
    void visit(BinaryExpression binaryExpression);

    void visit(AdditiveExpression additiveExpression);

    void visit(MultiplicativeExpression multiplicativeExpression);

    void visit(LogicalRelantionalExpression logicalRelantionalExpression);

    void visit(LogicalAndExpression logicalAndExpression);

    void visit(LogicalEqualityExpression logicalEqualityExpression);

    void visit(LogicalOrExpression logicalOrExpression);

    void visit(AssignmentExpression assignmentExpression);

    // LHS Expressions
    void visit(ArrayExpression expression);

    void visit(DereferenceExpression expression);

    void visit(VariableExpression variableExpression);

    // Other expressions
    void visit(InitializerList initializerList);

    // Statements
    void visit(Statement statement);

    // Jump statements
    void visit(BreakStatement breakStatement);

    void visit(ContinueStatement continueStatement);

    void visit(ReturnStatement returnStatement);

    // Iteration / Selection Statements
    void visit(IfStatement ifStatement);

    void visit(WhileStatement whileStatement);

    // Other statements
    void visit(CompoundStatement compoundStatement);

    void visit(ExpressionStatement expressionStatement);

    void visit(VariableDeclaration variableDeclaration);

    void visit(RawStatement rawStatement);

    // MISC
    void visit(ArgumentExpressionList expressionList);

    void visit(DeclarationSpecifierList declarationSpecifierList);

    void visit(FunctionDeclaration functionDeclaration);

    void visit(Program program);

    static void concreteify(Node node, Visitor visitor) {
        if (node instanceof Expression) {
            Visitor.concreteify(node, visitor);
        } else if (node instanceof Statement) {
            Visitor.concreteify(node, visitor);
        } else if (node instanceof ArgumentExpressionList) {
            visitor.visit((ArgumentExpressionList) node);
        } else if (node instanceof DeclarationSpecifierList) {
            visitor.visit((DeclarationSpecifierList) node);
        } else if (node instanceof FunctionDeclaration) {
            visitor.visit((FunctionDeclaration) node);
        } else if (node instanceof Program) {
            visitor.visit((Program) node);
        }
    }

    static void concreteify(Expression expression, Visitor visitor) {
        if (expression instanceof BoolLiteral) {
            visitor.visit((BoolLiteral) expression);
        } else if (expression instanceof IntLiteral) {
            visitor.visit((IntLiteral) expression);
        } else if (expression instanceof CharLiteral) {
            visitor.visit((CharLiteral) expression);
        } else if (expression instanceof AdditivePrefixExpression) {
            visitor.visit((AdditivePrefixExpression) expression);
        } else if (expression instanceof AddressOfExpression) {
            visitor.visit((AddressOfExpression) expression);
        } else if (expression instanceof CastExpression) {
            visitor.visit((CastExpression) expression);
        } else if (expression instanceof FunctionExpression) {
            visitor.visit((FunctionExpression) expression);
        } else if (expression instanceof IncrementDecrementExpression) {
            visitor.visit((IncrementDecrementExpression) expression);
        } else if (expression instanceof NegationExpression) {
            visitor.visit((NegationExpression) expression);
        } else if (expression instanceof AdditiveExpression) {
            visitor.visit((AdditiveExpression) expression);
        } else if (expression instanceof MultiplicativeExpression) {
            visitor.visit((MultiplicativeExpression) expression);
        } else if (expression instanceof LogicalRelantionalExpression) {
            visitor.visit((LogicalRelantionalExpression) expression);
        } else if (expression instanceof LogicalAndExpression) {
            visitor.visit((LogicalAndExpression) expression);
        } else if (expression instanceof LogicalEqualityExpression) {
            visitor.visit((LogicalEqualityExpression) expression);
        } else if (expression instanceof LogicalOrExpression) {
            visitor.visit((LogicalOrExpression) expression);
        } else if (expression instanceof AssignmentExpression) {
            visitor.visit((AssignmentExpression) expression);
        } else if (expression instanceof ArrayExpression) {
            visitor.visit((ArrayExpression) expression);
        } else if (expression instanceof DereferenceExpression) {
            visitor.visit((DereferenceExpression) expression);
        } else if (expression instanceof VariableExpression) {
            visitor.visit((VariableExpression) expression);
        } else if (expression instanceof InitializerList) {
            visitor.visit((InitializerList) expression);
        } else {
            throw new RuntimeException(
                    "Expression Not Found In ArduinoCodeGenerator visit(Expression expression) Expression: "
                            + expression.getClass().getName());
        }
    }

    static void concreteify(Statement statement, Visitor visitor) {
        if (statement instanceof BreakStatement) {
            visitor.visit((BreakStatement) statement);
        } else if (statement instanceof ContinueStatement) {
            visitor.visit((ContinueStatement) statement);
        } else if (statement instanceof ReturnStatement) {
            visitor.visit((ReturnStatement) statement);
        } else if (statement instanceof IfStatement) {
            visitor.visit((IfStatement) statement);
        } else if (statement instanceof WhileStatement) {
            visitor.visit((WhileStatement) statement);
        } else if (statement instanceof CompoundStatement) {
            visitor.visit((CompoundStatement) statement);
        } else if (statement instanceof ExpressionStatement) {
            visitor.visit((ExpressionStatement) statement);
        } else if (statement instanceof VariableDeclaration) {
            visitor.visit((VariableDeclaration) statement);
        } else if (statement instanceof RawStatement) {
            visitor.visit((RawStatement) statement);
        } else {
            throw new RuntimeException(
                    "Statement Not Found In ArduinoCodeGenerator visit(Statement statement) Statement: "
                            + statement.getClass().getName());
        }
    }
}
