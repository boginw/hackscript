import hackscript.antlr.HackScriptLexer;
import hackscript.antlr.HackScriptParser;
import lang.Generator;
import lang.visitors.ASTVisitor;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.generators.HVM;
import lang.SymbolTable;
import lang.nodes.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {
    public static void main(String[] args) {
        String sample = "void main(){ 1+1; }";

        HackScriptLexer lex = new HackScriptLexer(CharStreams.fromString(sample));
        HackScriptParser parser = new HackScriptParser(new CommonTokenStream(lex));
        parser.setBuildParseTree(true);

        SymbolTable st = new SymbolTable();
        HackScriptParser.StartContext ctx = parser.start();
        Node n = new CSTVisitor(st).visitStart(ctx);

        ASTVisitor ast = new ASTVisitor(st);
        ast.visit((Program) n);

        Generator hvm = new HVM();

        System.out.println(hvm.generate((Program) n));
    }
}