import hackscript.antlr.HackScriptLexer;
import hackscript.antlr.HackScriptParser;
import lang.Generator;
import lang.Stdlib;
import lang.visitors.ASTVisitor;
import lang.visitors.CSTVisitor;
import lang.Node;
import lang.generators.HVM;
import lang.SymbolTable;
import lang.nodes.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("examples/test.hacks")),
                StandardCharsets.UTF_8);

        HackScriptLexer lex = new HackScriptLexer(CharStreams.fromString(content));
        HackScriptParser parser = new HackScriptParser(new CommonTokenStream(lex));
        parser.setBuildParseTree(true);

        SymbolTable st = new SymbolTable();
        Stdlib.addTo(st);
        HackScriptParser.StartContext ctx = parser.start();
        Node n = new CSTVisitor(st).visitStart(ctx);

        ASTVisitor ast = new ASTVisitor(st);
        ast.visit((Program) n);

        Generator hvm = new HVM(9*9*9);

        System.out.println(hvm.generate((Program) n));
    }
}