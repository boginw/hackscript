package lang;

public class ReturnType implements Comparable<ReturnType> {
    final public Types type;

    public ReturnType(Types type) {
        this.type = type;
    }

    @Override
    public int compareTo(ReturnType o) {
        return type.compareTo(o.type);
    }

    public int getPrecedenceLevel() {
        int precedenceLevel;
        switch (type) {
            case VOID:
                precedenceLevel = 0;
                break;
            case BOOL:
                precedenceLevel = 2;
                break;
            case CHAR:
                precedenceLevel = 4;
                break;
            case INT:
                precedenceLevel = 6;
                break;
            default:
                precedenceLevel = 0;
                break;
        }

        return precedenceLevel;
    }

    @Override
    public String toString() {
        String string = "";

        if (type != null) {
            string += type.toString();
        }

        return string;
    }
}
