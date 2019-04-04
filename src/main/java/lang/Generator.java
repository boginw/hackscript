package lang;

import lang.nodes.Program;

public interface Generator<T> extends Visitor<T> {
    T generate(Program program);
}
