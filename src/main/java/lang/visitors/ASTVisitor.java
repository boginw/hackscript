package lang.visitors;

import lang.*;
import lang.nodes.*;
import lang.nodes.expressions.BinaryExpression;
import lang.nodes.expressions.InitializerList;
import lang.nodes.expressions.UnaryExpression;
import lang.nodes.expressions.binary.ArithmeticExpression;
import lang.nodes.expressions.binary.AssignmentExpression;
import lang.nodes.expressions.binary.LogicalExpression;
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

import java.util.List;

public class ASTVisitor implements Visitor {

    private SymbolTable global;

    public ASTVisitor(SymbolTable st) {
        this.global = st;
    }

    /**
     * Visits the crux, namely the program
     *
     * @param program The program to check
     */
    @Override
    public void visit(Program program) {

        // We enforce the inclusion of both setup and loop
        Node main = global.lookup("main");

        if (!(main instanceof FunctionDeclaration)) {
            throw new RuntimeException("Could not locate main-function.");
        }

        for (Node node : program) {
            if (node instanceof FunctionDeclaration) {
                visit((FunctionDeclaration) node);
            } else if (node instanceof VariableDeclaration) {
                visit((VariableDeclaration) node);
            }
        }
    }

    /**
     * Visits a function definition
     *
     * @param funcDef The function definition
     */
    @Override
    public void visit(FunctionDeclaration funcDef) {
        // Visit all the parameters
        for (VariableDeclaration vd : funcDef) {
            visit(vd);
        }

        visit(funcDef.getBody());

        ReturnType returnType = determineDeclarationSpecifier(funcDef.getSpecifiers());

        checkReturnValidityFunctionDefinition(funcDef.getBody(), returnType);
    }

    /**
     * Visits a statement
     *
     * @param stm The statement to visit
     */
    @Override
    public void visit(Statement stm) {
        if (stm instanceof ExpressionStatement) {
            visit((ExpressionStatement) stm);
        } else if (stm instanceof VariableDeclaration) {
            visit((VariableDeclaration) stm);
        } else if (stm instanceof IfStatement) {
            visit((IfStatement) stm);
        }
    }

    @Override
    public void visit(BreakStatement breakStatement) {
        // No type to decorate, hence empty
    }

    @Override
    public void visit(ContinueStatement continueStatement) {
        // No type to decorate, hence empty
    }

    @Override
    public void visit(ReturnStatement returnStatement) {
        // TODO: this
    }

    /**
     * Visits a compound statement
     *
     * @param cpmStm The compound statement
     */
    @Override
    public void visit(CompoundStatement cpmStm) {
        for (Statement stm : cpmStm) {
            visit(stm);
        }
    }

    /**
     * Visits an if statement
     *
     * @param stm The if statement to visit
     */
    @Override
    public void visit(IfStatement stm) {
        Expression condition = stm.getCondition();

        visit(stm.getCondition());
        visit(stm.getConsequence());

        if (stm.getAlternative() != null) {
            visit(stm.getAlternative());
        }

        if (condition.getReturnType().type != Types.BOOL) {
            throw new RuntimeException(
                    "The condition was not a boolean expression in " + stm.getClass().getName());
        }
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        Expression condition = whileStatement.getCondition();

        visit(whileStatement.getCondition());
        visit(whileStatement.getConsequence());

        if (condition.getReturnType().type != Types.BOOL) {
            throw new RuntimeException(
                    "The condition was not a boolean expression in " + whileStatement.getClass().getName());
        }
    }

    /**
     * Visits an expression statement
     *
     * @param exprStm Expression statement
     */
    @Override
    public void visit(ExpressionStatement exprStm) {
        if (exprStm.getExpression() instanceof FunctionExpression) {
            visit((FunctionExpression) exprStm.getExpression());
        } else if (exprStm.getExpression() instanceof BinaryExpression) {
            visit((BinaryExpression) exprStm.getExpression());
        } else if (exprStm.getExpression() instanceof UnaryExpression) {
            visit((UnaryExpression) exprStm.getExpression());
        } else {
            throw new RuntimeException("Expression was not found: " + exprStm.getLiteral());
        }
    }

    /**
     * Visits a declaration statement
     *
     * @param stm The declaration statement to visit
     */
    @Override
    public void visit(VariableDeclaration stm) {
        ReturnType returnType = determineDeclarationSpecifier(stm.getSpecifiers());
        stm.setReturnType(returnType);

        if (stm.getInitializer() != null) {

            // Check if variable is array, and if so, visits the initializer if it exists
            if (stm.isArray()) {
                if (stm.getInitializer() instanceof InitializerList) {
                    visit((InitializerList) stm.getInitializer(), returnType);
                } else {
                    throw new RuntimeException("Trying to assign "
                            + stm.getInitializer().getLiteral() + " which isn't an array.");
                }
            }

            // visit the initializer
            visit(stm.getInitializer());

            throwIfInvalidDeclaration(returnType, stm.getInitializer());

            // string assignment
            /*if (stm.getInitializer() instanceof StringLiteral && !stm.isPointer()) {
                throw new RuntimeException("Trying to assign a string to an illegal type.");
            }*/

            // TODO: explain this
            if (isPrecedenceCorrect(
                    stm.getReturnType(), stm.getInitializer().getReturnType())) {
                return;
            } else {
                System.out.print("Trying to assign " +
                        stm.getInitializer().getReturnType() + " to " + stm.getReturnType() +
                        ", but this will lose precision.");
            }
        }
    }

    @Override
    public void visit(RawStatement rawStatement) {
        // Ignore this
    }

    @Override
    public void visit(ArgumentExpressionList expressionList) {
        // TODO: this
    }


    /**
     * Visits declartion specifiers
     *
     * @param specifiers The specifiers to visit
     */
    @Override
    public void visit(DeclarationSpecifierList specifiers) {
        determineDeclarationSpecifier(specifiers);
    }

    @Override
    public void visit(Node node) {
        Visitor.concreteify(node, this);
    }

    /**
     * Visits an expression
     *
     * @param expr The expression to visit
     */
    @Override
    public void visit(Expression expr) {
        Visitor.concreteify(expr, this);
    }

    @Override
    public void visit(BoolLiteral boolLiteral) {
        // Type already assigned, as it's static, thereby this is ignored
    }

    @Override
    public void visit(IntLiteral intLiteral) {
        // Type already assigned, as it's static, thereby this is ignored
    }

    @Override
    public void visit(CharLiteral charLiteral) {
        // Type already assigned, as it's static, thereby this is ignored
    }

    /**
     * Visits a unary expression
     *
     * @param expr The unary expression
     */
    @Override
    public void visit(UnaryExpression expr) {
        if (expr instanceof AddressOfExpression) {
            visit((AddressOfExpression) expr);
        } else if (expr instanceof DereferenceExpression) {
            visit((DereferenceExpression) expr);
        } else if (expr instanceof NegationExpression) {
            visit((NegationExpression) expr);
        } else if (expr instanceof CastExpression) {
            visit((CastExpression) expr);
        } else if (expr instanceof AdditivePrefixExpression) {
            visit(expr.getFirstOperand());
            expr.setReturnType(expr.getFirstOperand().getReturnType());
        } else {
            throw new RuntimeException("Unary expression was not found: " + expr.getLiteral());
        }
    }

    @Override
    public void visit(AdditivePrefixExpression expression) {
        visit(expression.getFirstOperand());
        expression.setReturnType(expression.getFirstOperand().getReturnType());
    }

    /**
     * Visits a binary expression
     *
     * @param expr The binary expression
     */
    @Override
    public void visit(BinaryExpression expr) {
        if (expr instanceof ArithmeticExpression) {
            visit((ArithmeticExpression) expr);
        } else if (expr instanceof LogicalExpression) {
            visit((LogicalExpression) expr);
        } else if (expr instanceof AssignmentExpression) {
            visit((AssignmentExpression) expr);
        } else if (expr instanceof ArrayExpression) {
            visit((ArrayExpression) expr);
        } else {
            throw new RuntimeException(
                    "Binary expression was not found: " + expr.getLiteral());
        }
    }

    @Override
    public void visit(AdditiveExpression additiveExpression) {
        visit((ArithmeticExpression) additiveExpression);
    }

    @Override
    public void visit(MultiplicativeExpression multiplicativeExpression) {
        visit((ArithmeticExpression) multiplicativeExpression);
    }

    @Override
    public void visit(LogicalRelantionalExpression logicalRelantionalExpression) {
        // TODO: this
    }

    @Override
    public void visit(LogicalAndExpression logicalAndExpression) {
        visit((LogicalExpression) logicalAndExpression);
    }

    @Override
    public void visit(LogicalEqualityExpression logicalEqualityExpression) {
        // TODO: this
    }

    @Override
    public void visit(LogicalOrExpression logicalOrExpression) {
        visit((LogicalExpression) logicalOrExpression);
    }

    /**
     * Visits a negation expression
     *
     * @param expr The negation expression
     */
    @Override
    public void visit(NegationExpression expr) {
        expr.visit(this);
        determineIfValidType(expr.getFirstOperand(), Types.BOOL);
    }

    /**
     * Visits a function call expression
     *
     * @param expr The function call
     */
    @Override
    public void visit(FunctionExpression expr) {
        String FuncID = expr.getIdentifier();
        FunctionDeclaration funcDef = (FunctionDeclaration) global.lookup(FuncID);

        // check if function is declared
        if (funcDef == null) {
            throw new RuntimeException("Function " + expr.getIdentifier() + " was not declared");
        }

        expr.setDefinition(funcDef);
        ReturnType returnType = determineDeclarationSpecifier(funcDef.getSpecifiers());
        expr.setReturnType(returnType);

        // match number of parameters
        if (funcDef.size() > expr.getParameterList().size()) {
            throw new RuntimeException("Too few parameters given in " + expr.getLiteral());
        } else if (funcDef.size() < expr.getParameterList().size()) {
            throw new RuntimeException("Too many parameters given in " + expr.getLiteral());
        }

        // verify that all parameters match types
        for (int i = 0; i < funcDef.size(); i++) {
            visit(funcDef.get(i));
            ReturnType declarationReturnType = funcDef.get(i).getReturnType();
            Expression parameter = expr.getParameterList().get(i);
            visit(parameter);
            throwIfInvalidDeclaration(declarationReturnType, parameter);
        }
    }

    @Override
    public void visit(IncrementDecrementExpression expression) {

    }

    /**
     * Visits an array expression
     *
     * @param expr The array expression
     */
    @Override
    public void visit(ArrayExpression expr) {
        visit(expr.getFirstOperand());
        visit(expr.getSecondOperand());

        // TODO: long int, long double and long char are also supported.
        determineIfValidType(expr.getSecondOperand(), Types.INT, Types.CHAR);

        expr.setReturnType(expr.getFirstOperand().getReturnType());
    }

    /**
     * Visits address of expression
     *
     * @param expr The address of expression
     */
    @Override
    public void visit(AddressOfExpression expr) {
        expr.visit(this);
        expr.setReturnType(expr.getFirstOperand().getReturnType());
    }

    /**
     * Visits a dereference expression
     *
     * @param expr The dereference expression
     */
    @Override
    public void visit(DereferenceExpression expr) {
        expr.visit(this);
        expr.setReturnType(expr.getFirstOperand().getReturnType());
    }

    /**
     * Visits an assignment expression
     *
     * @param expr Assignment expression
     */
    @Override
    public void visit(AssignmentExpression expr) {
        expr.visit(this);

        ReturnType returnType = throwIfInvalidDeclaration(
                expr.getFirstOperand().getReturnType(), expr.getSecondOperand()
        );

        expr.setReturnType(returnType);
    }

    /**
     * Visits a logical binary expression
     *
     * @param expr Logical binary expression
     */
    public void visit(LogicalExpression expr) {
        expr.visit(this);

        Node firstReturnType = expr.getFirstOperand();
        Node secondReturnType = expr.getSecondOperand();

        if (expr instanceof LogicalAndExpression || expr instanceof LogicalOrExpression) {
            determineBooleanReturnType(firstReturnType, secondReturnType);
        } else if (expr instanceof LogicalEqualityExpression) {
            throwIfInvalidDeclaration(expr.getFirstOperand().getReturnType(), secondReturnType);
        } else {
            determineArithmeticReturnType(firstReturnType, secondReturnType);
        }
    }

    /**
     * Visits an arithmetic binary expression
     *
     * @param expr The arithmetic binary expression
     */
    public void visit(ArithmeticExpression expr) {
        expr.visit(this);

        expr.setReturnType(determineArithmeticReturnType(
                expr.getFirstOperand(), expr.getSecondOperand())
        );

    }

    /**
     * Visits a variable expression
     *
     * @param expr The variable expression
     */
    @Override
    public void visit(VariableExpression expr) {
        expr.setReturnType(expr.getDefinition().getReturnType());
    }

    /**
     * Visits an initializer list
     *
     * @param expr The initializer list
     */
    @Override
    public void visit(InitializerList expr) {
        ReturnType ty = null;

        if (expr.size() > 0) {
            ty = new ReturnType(((Expression) expr.get(0)).getReturnType().type);

            for (int i = 1; i < expr.size(); i++) {
                throwIfInvalidDeclaration(ty, expr.get(1));
            }
        }

        expr.setReturnType(ty);
    }

    /**
     * Visits a cast expression
     *
     * @param expr The cast expression
     */
    @Override
    public void visit(CastExpression expr) {
        ReturnType specifierReturnType = determineDeclarationSpecifier(expr.getSpecifiers());

        if (specifierReturnType.type == Types.BOOL) {
            determineIfValidType(expr.getFirstOperand(), Types.BOOL);
        } else {
            determineIfValidType(expr.getFirstOperand(), Types.INT, Types.CHAR);
        }

        expr.setReturnType(specifierReturnType);
    }

    /**
     * Helper method to check return type of a function
     *
     * @param body       The function body
     * @param returnType The expected return type
     */
    private void checkReturnValidityFunctionDefinition(CompoundStatement body,
                                                       ReturnType returnType) {
        if (body.size() == 0 && returnType.type != Types.VOID) {
            throw new RuntimeException("No returntype exists, expected " + returnType.toString());
        }

        if (returnType.type == Types.VOID) {
            return;
        }

        for (Statement stm : body) {

            if (stm instanceof IfStatement) {
                IfStatement iStm = (IfStatement) stm;
                if (iStm.getConsequence() != null) {
                    checkReturnValidityFunctionDefinition((CompoundStatement) iStm.getConsequence(),
                            returnType);
                }
                if (iStm.getAlternative() != null) {
                    checkReturnValidityFunctionDefinition((CompoundStatement) iStm.getAlternative(),
                            returnType);
                }
            }

            if (stm instanceof WhileStatement) {
                WhileStatement wStm = (WhileStatement) stm;
                checkReturnValidityFunctionDefinition((CompoundStatement) wStm.getConsequence(),
                        returnType);
            }

            if (stm instanceof ReturnStatement) {
                visit(((ReturnStatement) stm).getReturnValue());
                if (((ReturnStatement) stm).getReturnValue().getReturnType().compareTo(returnType)
                        != 0) {
                    throw new RuntimeException(
                            "Invalid type returned, expected " + returnType.toString());
                } else {
                    return;
                }
            }
        }

        throw new RuntimeException("No returnvalue was given, expected " + returnType.toString());
    }

    /**
     * Visit an initializer list, and verify that it can be assigned to the parents type
     *
     * @param initializerList  The initalizer list
     * @param parentReturnType The return type of the parent
     */
    private void visit(InitializerList initializerList, ReturnType parentReturnType) {
        for (Node initializer : initializerList) {
            visit((Expression) initializer);
            throwIfInvalidDeclaration(parentReturnType, initializer);
        }

        initializerList.setReturnType(parentReturnType);
    }



    // Type rules

    private ReturnType determineArithmeticReturnType(Node firstNode, Node secondNode) {
        ReturnType firstReturnType = determineIfValidType(firstNode, Types.INT, Types.CHAR);
        ReturnType secondReturnType = determineIfValidType(secondNode, Types.INT, Types.CHAR);

        return determineReturnType(firstReturnType, secondReturnType);
    }

    private void determineBooleanReturnType(Node firstNode, Node secondNode) {
        ReturnType firstReturnType = determineIfValidType(firstNode, Types.BOOL);
        ReturnType secondReturnType = determineIfValidType(secondNode, Types.BOOL);

        determineReturnType(firstReturnType, secondReturnType);
    }

    private ReturnType throwIfInvalidDeclaration(ReturnType declReturnType, Node firstNode) {
        if (declReturnType.type == Types.BOOL) {
            return determineIfValidType(firstNode, Types.BOOL);
        } else {
            return determineIfValidType(firstNode, Types.INT, Types.CHAR);
        }
    }

    private ReturnType determineIfValidType(Node node, Types... allowedTypes) {
        ReturnType nodeReturnType = ((Expression) node).getReturnType();
        for (Types type : allowedTypes) {
            if (nodeReturnType.type == type) {
                return nodeReturnType;
            }
        }

        StringBuilder allowedTypesString = new StringBuilder();
        for (Types type : allowedTypes) {
            allowedTypesString.append(type.toString());
            allowedTypesString.append(", ");
        }

        throw new RuntimeException(
                "Invalid type. Expected: " + allowedTypesString + " but it wasn't found.");
    }

    private ReturnType determineReturnType(ReturnType op1, ReturnType op2) {
        if (op1.type == Types.INT || op2.type == Types.INT) {
            return new ReturnType(Types.INT);
        } else if (op1.type == Types.CHAR || op2.type == Types.CHAR) {
            return new ReturnType(Types.CHAR);
        } else if (op1.type == Types.BOOL || op2.type == Types.BOOL) {
            return new ReturnType(Types.BOOL);
        } else {
            return new ReturnType(Types.VOID);
        }
    }

    private boolean isPrecedenceCorrect(ReturnType op1, ReturnType op2) {
        return op1.getPrecedenceLevel() >= op2.getPrecedenceLevel();
    }

    private static Types specifierToType(String specifier) {
        switch (specifier) {
            case "int":
                return Types.INT;
            case "char":
                return Types.CHAR;
            case "bool":
                return Types.BOOL;
            case "void":
                return Types.VOID;
            default:
                throw new RuntimeException("Unrecognized type: " + specifier);
        }
    }

    private ReturnType determineDeclarationSpecifier(List<String> specifiers) {
        return new ReturnType(specifierToType(specifiers.get(specifiers.size() - 1)));
    }
}
