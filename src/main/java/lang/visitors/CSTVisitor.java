package lang.visitors;

import hackscript.antlr.HackScriptBaseVisitor;
import hackscript.antlr.HackScriptParser.*;
import lang.Node;
import lang.SymbolTable;
import lang.nodes.ArgumentExpressionList;
import lang.nodes.FunctionDeclaration;
import lang.nodes.Program;
import lang.nodes.expressions.InitializerList;
import lang.nodes.expressions.binary.AssignmentExpression;
import lang.nodes.expressions.binary.arithmetic.AdditiveExpression;
import lang.nodes.expressions.binary.arithmetic.MultiplicativeExpression;
import lang.nodes.expressions.binary.logical.LogicalAndExpression;
import lang.nodes.expressions.binary.logical.LogicalEqualityExpression;
import lang.nodes.expressions.binary.logical.LogicalOrExpression;
import lang.nodes.expressions.binary.logical.relational.LogicalGreaterEqualsExpression;
import lang.nodes.expressions.binary.logical.relational.LogicalGreaterExpression;
import lang.nodes.expressions.binary.logical.relational.LogicalLessEqualsExpression;
import lang.nodes.expressions.binary.logical.relational.LogicalLessExpression;
import lang.nodes.expressions.leftHandSide.ArrayExpression;
import lang.nodes.expressions.leftHandSide.DereferenceExpression;
import lang.nodes.expressions.leftHandSide.VariableExpression;
import lang.nodes.expressions.literal.BoolLiteral;
import lang.nodes.expressions.literal.CharLiteral;
import lang.nodes.expressions.literal.IntLiteral;
import lang.nodes.expressions.unary.*;
import lang.nodes.statements.CompoundStatement;
import lang.nodes.statements.ExpressionStatement;
import lang.nodes.statements.VariableDeclaration;
import lang.nodes.statements.jump.BreakStatement;
import lang.nodes.statements.jump.ContinueStatement;
import lang.nodes.statements.jump.ReturnStatement;
import lang.nodes.statements.selection.IfStatement;
import lang.nodes.statements.selection.WhileStatement;

public class CSTVisitor extends HackScriptBaseVisitor<Node> {

    private SymbolTable global;

    public CSTVisitor(SymbolTable st) {
        global = st;
    }

    public SymbolTable getGlobal() {
        return global;
    }

    public void scopeIn(SymbolTable scope) {
        scope.setParent(global);
        global = scope;
    }

    public void scopeOut() {
        global = global.getParent();
    }

    @Override
    public Node visitStart(StartContext ctx) {
        return new Program().parse(ctx, this);
    }

    @Override
    public Node visitParameterDeclaration(ParameterDeclarationContext ctx) {
        return new VariableDeclaration().parse(ctx, this);
    }


    @Override
    public Node visitDeclarationStatement(DeclarationStatementContext ctx) {
        return new VariableDeclaration().parse(ctx, this);
    }

    @Override
    public Node visitInitializer(InitializerContext ctx) {
        if (ctx.initializerList() != null) {
            return visitInitializerList(ctx.initializerList());
        } else {
            return visitChildren(ctx);
        }
    }

    @Override
    public Node visitInitializerList(InitializerListContext ctx) {
        return new InitializerList().parse(ctx, this);
    }

    @Override
    public Node visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return new FunctionDeclaration().parse(ctx, this);
    }

    @Override
    public Node visitPrimaryExpression(PrimaryExpressionContext ctx) {
        if (ctx.children.size() != 0) {
            // There's 3 children when it's a parenthesized expression.
            if (ctx.children.size() == 3) {
                // The middle child is the expression, between the opening and closing parenthesis.
                return visit(ctx.getChild(1));
            }
            /*// If it is string
            else if ((ctx.StringLiteral().size()) != 0) {
                return new StringLiteral().parse(ctx, this);
            }*/
            // If it is char
            else if (ctx.CharLiteral() != null) {
                return new CharLiteral().parse(ctx, this);
            }
            // If it is int
            else if (ctx.IntLiteral() != null) {
                return new IntLiteral().parse(ctx, this);
            }
            // If it is bool
            else if (ctx.BoolLiteral() != null) {
                return new BoolLiteral().parse(ctx, this);
            }
            // If it is identifier
            else if (ctx.Identifier() != null) {
                return new VariableExpression().parse(ctx, this);
            }
        }
        throw new RuntimeException("No recognized primary expression");
    }

    public Node visitCompoundStatement(CompoundStatementContext ctx) {
        return new CompoundStatement().parse(ctx, this);
    }

    @Override
    public Node visitIterationStatement(IterationStatementContext ctx) {
        return new WhileStatement().parse(ctx, this);
    }

    @Override
    public Node visitIfStatement(IfStatementContext ctx) {
        return new IfStatement().parse(ctx, this);
    }

    @Override
    public Node visitJumpStatement(JumpStatementContext ctx) {
        String terminal = ctx.getChild(0).getText();

        switch (terminal) {
            case "return":
                return new ReturnStatement().parse(ctx, this);
            case "continue":
                return new ContinueStatement().parse(ctx, this);
            case "break":
                return new BreakStatement().parse(ctx, this);
            default:
                throw new RuntimeException("Such jump statement does NOT exist");
        }
    }

    public Node visitAssignmentExpression(AssignmentExpressionContext ctx) {
        return new AssignmentExpression().parse(ctx, this);
    }

    @Override
    public Node visitExpressionStatement(ExpressionStatementContext ctx) {
        return new ExpressionStatement().parse(ctx, this);
    }


    @Override
    public Node visitAdditiveExpression(AdditiveExpressionContext ctx) {
        return new AdditiveExpression().parse(ctx, this);
    }

    @Override
    public Node visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
        return new MultiplicativeExpression().parse(ctx, this);
    }

    @Override
    public Node visitLogicalAndExpression(LogicalAndExpressionContext ctx) {
        return new LogicalAndExpression().parse(ctx, this);
    }

    @Override
    public Node visitLogicalOrExpression(LogicalOrExpressionContext ctx) {
        return new LogicalOrExpression().parse(ctx, this);
    }

    @Override
    public Node visitEqualityExpression(EqualityExpressionContext ctx) {
        return new LogicalEqualityExpression().parse(ctx, this);
    }

    @Override
    public Node visitRelationalExpression(RelationalExpressionContext ctx) {
        if (ctx.children.size() == 1) {
            return visitChildren(ctx);
        }

        switch (ctx.children.get(1).getText()) {
            case ">":
                return new LogicalGreaterExpression().parse(ctx, this);
            case "<":
                return new LogicalLessExpression().parse(ctx, this);
            case ">=":
                return new LogicalGreaterEqualsExpression().parse(ctx, this);
            case "<=":
                return new LogicalLessEqualsExpression().parse(ctx, this);
            default:
                throw new RuntimeException("No logical expression of that form");
        }
    }

    @Override
    public Node visitArgumentExpressionList(ArgumentExpressionListContext ctx) {
        return new ArgumentExpressionList().parse(ctx, this);
    }

    @Override
    public Node visitPostfixExpression(PostfixExpressionContext ctx) {

        if (ctx.children.size() > 1) {

            String firstToken = ctx.getChild(1).getText();

            switch (firstToken) {
                case "(":
                    return new FunctionExpression().parse(ctx, this);
                case "[":
                    return new ArrayExpression().parse(ctx, this);
                case "++":
                case "--":
                    return new IncrementDecrementExpression().parse(ctx, this);
            }

        } else if (ctx.primaryExpression() != null) {
            return visitPrimaryExpression(ctx.primaryExpression());
        }

        return visitChildren(ctx);
    }

    @Override
    public Node visitCastExpression(CastExpressionContext ctx) {
        return new CastExpression().parse(ctx, this);
    }

    @Override
    public Node visitUnaryExpression(UnaryExpressionContext ctx) {

        // Check if actually postfix expression
        if (ctx.postfixExpression() != null) {

            return visitPostfixExpression(ctx.postfixExpression());

        } else if (ctx.unaryOperator() != null) {

            // Prefix expression confirmed
            switch (ctx.unaryOperator().getText()) {
                case "--":
                case "++":
                    return new IncrementDecrementExpression().parse(ctx, this);
                case "+":
                case "-":
                    return new AdditivePrefixExpression().parse(ctx, this);
                case "*":
                    return new DereferenceExpression().parse(ctx, this);
                case "&":
                    return new AddressOfExpression().parse(ctx, this);
                case "!":
                    return new NegationExpression().parse(ctx, this);
                default:
                    throw new RuntimeException("The operator for the unary expression wasn't known.");
            }

        } else {

            // We don't know what it is
            throw new RuntimeException("UnaryExpression did not get a valid input");

        }
    }
}