package lang.nodes;

import lang.Node;

/**
 * This interface should be used where an identifier is involved
 */
public interface Identifier extends Node {

    /**
     * Gets the identifier
     *
     * @return The identifier
     */
    String getIdentifier();

    /**
     * Sets the identifier
     *
     * @param identifier Identifier
     */
    void setIdentifier(String identifier);


    void setLocation(int location);

    int getLocation();
}