package lang.generators;

import lang.Generator;
import lang.Node;
import lang.Visitor;
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

import java.util.ArrayList;
import java.util.List;

public class HVM implements Generator<String> {
    private int offset;
    private int funcOffset = 0;
    private int next;

    public HVM(int offset) {
        this.offset = offset;
        this.next = offset;
    }

    @Override
    public String generate(Program program) {
        return visit(program);
    }

    @Override
    public String visit(Node node) {
        return Visitor.concreteify(node, this);
    }

    @Override
    public String visit(Expression expression) {
        return Visitor.concreteify(expression, this);
    }

    @Override
    public String visit(BoolLiteral boolLiteral) {
        return boolLiteral.getValue() ? "1" : "0";
    }

    @Override
    public String visit(IntLiteral intLiteral) {
        return optimalNumber(intLiteral.getValue());
    }

    @Override
    public String visit(CharLiteral charLiteral) {
        return optimalNumber(charLiteral.getValue().substring(1, 2).charAt(0));
    }

    @Override
    public String visit(UnaryExpression expression) {
        // TODO: this
        return "";
    }

    @Override
    public String visit(AdditivePrefixExpression expression) {
        if (expression.getOperator().equals("-")) {
            return "0" + visit(expression.getFirstOperand()) + "-";
        } else {
            return visit(expression.getFirstOperand());
        }
    }

    @Override
    public String visit(AddressOfExpression addressOfExpression) {
        if (addressOfExpression.getFirstOperand() instanceof VariableExpression) {
            return optimalNumber(((VariableExpression) addressOfExpression.getFirstOperand()).getLocation());
        }

        return "";
    }

    @Override
    public String visit(CastExpression expression) {
        // TODO: this
        return "";
    }

    @Override
    public String visit(FunctionExpression expression) {
        if (expression.getIdentifier().equals("print")) {
            return visit(expression.getParameterList().get(0)) + "p0";
        } else if (expression.getIdentifier().equals("pprint")) {
            return visit(expression.getParameterList().get(0)) + "P0";
        } else if (expression.getIdentifier().equals("exit")) {
            return "!";
        }

        return "";
    }

    @Override
    public String visit(IncrementDecrementExpression expression) {
        if (expression.getFirstOperand() instanceof VariableExpression) {
            String loc = optimalNumber(((VariableExpression) expression.getFirstOperand()).getLocation());
            String op = expression.getOperator().equals("++") ? "+" : "-";

            return loc + "<1" + op + loc + ">" + loc + "<" + (expression.isPrefix() ? "1-" : "");
        }

        return "";
    }

    @Override
    public String visit(NegationExpression expression) {
        return visit(expression.getFirstOperand()) + "1:3?11g0";
    }

    @Override
    public String visit(BinaryExpression binaryExpression) {
        return Visitor.concreteify(binaryExpression, this);
    }

    @Override
    public String visit(AdditiveExpression additiveExpression) {
        return visit(additiveExpression.getFirstOperand()) +
                visit(additiveExpression.getSecondOperand()) + "+";
    }

    @Override
    public String visit(MultiplicativeExpression multiplicativeExpression) {
        return visit(multiplicativeExpression.getFirstOperand()) +
                visit(multiplicativeExpression.getSecondOperand()) + "*";
    }

    @Override
    public String visit(LogicalRelantionalExpression logicalRelantionalExpression) {
        String f = visit(logicalRelantionalExpression.getFirstOperand());
        String s = visit(logicalRelantionalExpression.getSecondOperand());
        String tmp;

        switch (logicalRelantionalExpression.getOperator()) {
            case "<":
                tmp = f;
                f = s;
                s = tmp;
            case ">":
                return f + s + ":1:3?01g1";
            case "<=":
                tmp = f;
                f = s;
                s = tmp;
            case ">=":
                return f + s + ":3?01g1" + f + s + ":1:3?01g1" + "+0:1:3?01g1";
        }

        return "";
    }

    @Override
    public String visit(LogicalAndExpression logicalAndExpression) {
        return visit(logicalAndExpression.getFirstOperand()) +
                visit(logicalAndExpression.getSecondOperand()) + "+2:3?01g1";
    }

    @Override
    public String visit(LogicalEqualityExpression logicalEqualityExpression) {
        if (logicalEqualityExpression.getOperator().equals("==")) {
            return visit(logicalEqualityExpression.getFirstOperand()) +
                    visit(logicalEqualityExpression.getSecondOperand()) + ":3?01g1";
        } else {
            return visit(logicalEqualityExpression.getFirstOperand()) +
                    visit(logicalEqualityExpression.getSecondOperand()) + ":3?11g0";
        }
    }

    @Override
    public String visit(LogicalOrExpression logicalOrExpression) {
        return visit(logicalOrExpression.getFirstOperand()) +
                visit(logicalOrExpression.getSecondOperand()) + "+0:1:3?01g1";
    }

    @Override
    public String visit(AssignmentExpression assignmentExpression) {
        if (assignmentExpression.getFirstOperand() instanceof VariableExpression) {
            String loc = optimalNumber(((VariableExpression) assignmentExpression.getFirstOperand()).getLocation());
            return visit(assignmentExpression.getSecondOperand()) + loc + ">" + loc + "<";
        }
        return "";
    }

    @Override
    public String visit(ArrayExpression expression) {
        // TODO: this
        return "";
    }

    @Override
    public String visit(DereferenceExpression expression) {
        return visit(expression.getFirstOperand()) + "<";
    }

    @Override
    public String visit(VariableExpression variableExpression) {
        return optimalNumber(variableExpression.getLocation()) + "<";
    }

    @Override
    public String visit(InitializerList initializerList) {
        // TODO: this
        return "";
    }

    @Override
    public String visit(Statement statement) {
        return Visitor.concreteify(statement, this);
    }

    @Override
    public String visit(BreakStatement breakStatement) {
        // TODO: this
        return "";
    }

    @Override
    public String visit(ContinueStatement continueStatement) {
        // TODO: this
        return "";
    }

    @Override
    public String visit(ReturnStatement returnStatement) {
        if (returnStatement.getReturnValue() != null) {
            return visit(returnStatement.getReturnValue()) + "$";
        } else {
            return "$";
        }
    }

    @Override
    public String visit(IfStatement ifStatement) {
        String cons = visit(ifStatement.getConsequence());
        String cond = visit(ifStatement.getCondition());

        if (ifStatement.getAlternative() != null) {
            String alt = visit(ifStatement.getAlternative());
            String consLoc = optimalNumber(cons.length());

            return cond + "1:" +
                    (optimalNumber(alt.length() + consLoc.length() + 1)) + "?" +
                    alt + consLoc + "g" + cons;
        } else {
            return cond + "0:" + (optimalNumber(cons.length())) + "?" + cons;
        }
    }

    @Override
    public String visit(WhileStatement whileStatement) {
        String cons = visit(whileStatement.getConsequence());
        String cond = visit(whileStatement.getCondition());

        int i = 0;
        String init;
        String ret;

        do {
            init = cond + "0:" + optimalNumber(cons.length() + i + 1) + "?" + cons + "0";
            ret = init + optimalNumber(init.length() + i) + "-g";
            i++;
        } while (init.length() + i != ret.length() + 1);

        return ret;
    }

    @Override
    public String visit(CompoundStatement compoundStatement) {
        StringBuilder sb = new StringBuilder();
        for (Statement stm : compoundStatement) {
            sb.append(visit(stm));
        }
        return sb.toString();
    }

    @Override
    public String visit(ExpressionStatement expressionStatement) {
        return visit(expressionStatement.getExpression()) + "d";
    }

    @Override
    public String visit(VariableDeclaration variableDeclaration) {
        if (!variableDeclaration.isArray()) {
            variableDeclaration.setLocation(next++);

            if (variableDeclaration.getInitializer() != null) {
                return visit(variableDeclaration.getInitializer()) + optimalNumber(next-1) + ">";
            }
        }

        return "";
    }

    @Override
    public String visit(RawStatement rawStatement) {
        return rawStatement.getRawCode();
    }

    @Override
    public String visit(ArgumentExpressionList expressionList) {
        // TODO: this
        return "";
    }

    @Override
    public String visit(DeclarationSpecifierList declarationSpecifierList) {
        // TODO: this
        return "";
    }

    @Override
    public String visit(FunctionDeclaration functionDeclaration) {
        for (VariableDeclaration var : functionDeclaration) {
            var.setLocation(next++);
        }

        String f = visit(functionDeclaration.getBody());
        functionDeclaration.setLocation(funcOffset);
        funcOffset += f.length();

        for (int i = 0; i < functionDeclaration.size(); i++) {
            next--;
        }

        return f;
    }

    @Override
    public String visit(Program program) {
        StringBuilder sb = new StringBuilder();

        for (Declaration decl : program) {
            sb.append(visit(decl));
        }

        return sb.toString();
    }

    public static String optimalNumber(int number) {
        // Strings less than 10 are supported
        if (number < 10) {
            return String.valueOf(number);
        }

        int i = 1;
        while (i * 9 < number) {
            i++;
        }

        int from = i * 9 - number;

        return "9" + optimalNumber(i) + "*" + (from != 0 ? from + "-" : "");
    }

    private static List<Integer> primeFactors(int number) {
        int n = number;
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }

}