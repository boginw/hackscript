package lang.generators;

import lang.Generator;
import lang.Node;
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

public class HVM implements Generator {
    @Override
    public String generate(Program program) {
        visit(program);
        return "11+p";
    }

    @Override
    public void visit(Node node) {

    }

    @Override
    public void visit(Expression expression) {

    }

    @Override
    public void visit(BoolLiteral boolLiteral) {

    }

    @Override
    public void visit(IntLiteral intLiteral) {

    }

    @Override
    public void visit(CharLiteral charLiteral) {

    }

    @Override
    public void visit(UnaryExpression expression) {

    }

    @Override
    public void visit(AdditivePrefixExpression expression) {

    }

    @Override
    public void visit(AddressOfExpression addressOfExpression) {

    }

    @Override
    public void visit(CastExpression expression) {

    }

    @Override
    public void visit(FunctionExpression expression) {

    }

    @Override
    public void visit(IncrementDecrementExpression expression) {

    }

    @Override
    public void visit(NegationExpression expression) {

    }

    @Override
    public void visit(BinaryExpression binaryExpression) {

    }

    @Override
    public void visit(AdditiveExpression additiveExpression) {

    }

    @Override
    public void visit(MultiplicativeExpression multiplicativeExpression) {

    }

    @Override
    public void visit(LogicalRelantionalExpression logicalRelantionalExpression) {

    }

    @Override
    public void visit(LogicalAndExpression logicalAndExpression) {

    }

    @Override
    public void visit(LogicalEqualityExpression logicalEqualityExpression) {

    }

    @Override
    public void visit(LogicalOrExpression logicalOrExpression) {

    }

    @Override
    public void visit(AssignmentExpression assignmentExpression) {

    }

    @Override
    public void visit(ArrayExpression expression) {

    }

    @Override
    public void visit(DereferenceExpression expression) {

    }

    @Override
    public void visit(VariableExpression variableExpression) {

    }

    @Override
    public void visit(InitializerList initializerList) {

    }

    @Override
    public void visit(Statement statement) {

    }

    @Override
    public void visit(BreakStatement breakStatement) {

    }

    @Override
    public void visit(ContinueStatement continueStatement) {

    }

    @Override
    public void visit(ReturnStatement returnStatement) {

    }

    @Override
    public void visit(IfStatement ifStatement) {

    }

    @Override
    public void visit(WhileStatement whileStatement) {

    }

    @Override
    public void visit(CompoundStatement compoundStatement) {

    }

    @Override
    public void visit(ExpressionStatement expressionStatement) {

    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {

    }

    @Override
    public void visit(RawStatement rawStatement) {

    }

    @Override
    public void visit(ArgumentExpressionList expressionList) {

    }

    @Override
    public void visit(DeclarationSpecifierList declarationSpecifierList) {

    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {

    }

    @Override
    public void visit(Program program) {

    }
}
