package lang.nodes;

import hackscript.antlr.HackScriptParser.StartContext;
import hackscript.antlr.HackScriptParser.TranslationUnitContext;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class represents a program. A program is just a list of declarations. Therefore, we just
 * extend the ArrayList class
 */
public class Program extends ArrayList<Declaration> implements Node {

    @Override
    public String getLiteral() {
        StringBuilder sb = new StringBuilder();
        for (Declaration node : this) {
            sb.append(node.getLiteral());
        }
        return sb.toString();
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        StartContext actx = Node.checkContext(ctx, StartContext.class);

        TranslationUnitContext tuc = actx.translationUnit();
        Stack<TranslationUnitContext> tucList = new Stack<>();
        while (tuc != null) {
            tucList.push(tuc);
            tuc = tuc.translationUnit();
        }
        while (!tucList.empty()) {
            add((Declaration) visitor.visit(tucList.pop().externalDeclaration()));
        }

        return this;
    }

    @Override
    public void visit(Visitor visitor) {
        for (Declaration declaration : this) {
            visitor.visit(declaration);
        }
    }
}