package lang;

import hackscript.antlr.HackScriptParser.TypeSpecifierContext;
import lang.nodes.DeclarationSpecifierList;
import lang.visitors.CSTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public interface Node {

    /**
     * Fetches the literal of the node
     *
     * @return LiteralExpression of the node
     */
    String getLiteral();

    Node parse(ParserRuleContext ctx, CSTVisitor visitor);

    void visit(Visitor visitor);

    static <T extends ParserRuleContext> T checkContext(ParserRuleContext ctx, Class<T> classObj) {
        if(!classObj.isInstance(ctx)) {
            throw new RuntimeException("Wrong context");
        }

        return (T) ctx;
    }

    static DeclarationSpecifierList parseDeclarationSpecifiers(ParseTree parseTree) {
        List<String> specifiers = new ArrayList<>();
        if (parseTree instanceof TypeSpecifierContext) {
            TypeSpecifierContext child = (TypeSpecifierContext) parseTree;

            for (ParseTree specifier : child.children) {
                specifiers.add(specifier.getText());
            }

            return new DeclarationSpecifierList(specifiers);
        }
        return null;
    }
}
