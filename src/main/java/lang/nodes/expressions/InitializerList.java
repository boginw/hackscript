package lang.nodes.expressions;

import hackscript.antlr.HackScriptParser.InitializerListContext;
import lang.visitors.CSTVisitor;
import lang.ReturnType;
import lang.Visitor;
import lang.nodes.Expression;
import lang.Node;
import java.util.ArrayList;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;

public class InitializerList extends ArrayList<Node> implements Expression {

    private ReturnType returnReturnType;

    @Override
    public void setReturnType(ReturnType returnReturnType) {
        this.returnReturnType = returnReturnType;
    }

    @Override
    public ReturnType getReturnType() {
        return returnReturnType;
    }

    @Override
    public String getLiteral() {
        return "{ " + StringUtils.join(this, ", ") + " }";
    }

    @Override
    public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        InitializerListContext actx = Node.checkContext(ctx, InitializerListContext.class);
        Stack<ParseTree> parseTreeStack = new Stack<>();

        while (actx.initializerList() != null) {
            // If there are more siblings
            parseTreeStack.push(actx.initializer());

            // Go down
            actx = actx.initializerList();
        }
        parseTreeStack.push(actx.initializer());

        while (!parseTreeStack.empty()) {
            add(visitor.visit(parseTreeStack.pop()));
        }

        return this;
    }

    @Override
    public void visit(Visitor visitor) {
        for(Node node : this) {
            visitor.visit(node);
        }
    }

}
