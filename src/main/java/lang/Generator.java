package lang;

import lang.nodes.Program;

public interface Generator extends Visitor {
    String generate(Program program);
}
