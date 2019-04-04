package lang;

import lang.nodes.DeclarationSpecifierList;
import lang.nodes.FunctionDeclaration;
import lang.nodes.Identifier;
import lang.nodes.statements.VariableDeclaration;

import java.util.ArrayList;

public class Stdlib {
    public static Identifier exit() {
        return getFuncDef("exit", getVoidSpecifier());
    }

    public static Identifier pprint() {
        FunctionDeclaration pprint = getFuncDef("pprint", getVoidSpecifier());

        VariableDeclaration seed = getVarDef("toPrint", getCharSpecifier());
        pprint.add(seed);

        return pprint;
    }

    public static Identifier print() {
        FunctionDeclaration print = getFuncDef("print", getVoidSpecifier());

        VariableDeclaration seed = getVarDef("toPrint", getIntSpecifier());
        print.add(seed);

        return print;
    }

    private static VariableDeclaration getVarDef(String identifier, DeclarationSpecifierList decl) {
        VariableDeclaration var = new VariableDeclaration();
        var.setIdentifier(identifier);
        var.setSpecifiers(decl);

        return var;
    }

    private static FunctionDeclaration getFuncDef(String identifier, DeclarationSpecifierList decl) {
        FunctionDeclaration func = new FunctionDeclaration();
        func.setIdentifier(identifier);
        func.setSpecifiers(decl);

        return func;
    }

    private static DeclarationSpecifierList getIntSpecifier() {
        return new DeclarationSpecifierList(new ArrayList<String>() {{
            add("int");
        }});
    }

    private static DeclarationSpecifierList getCharSpecifier() {
        return new DeclarationSpecifierList(new ArrayList<String>() {{
            add("char");
        }});
    }

    private static DeclarationSpecifierList getVoidSpecifier() {
        return new DeclarationSpecifierList(new ArrayList<String>() {{
            add("void");
        }});
    }

    public static void addTo(SymbolTable st) {
        st.insert(exit());
        st.insert(print());
        st.insert(pprint());
    }
}
