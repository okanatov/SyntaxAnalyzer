package org.okanatov.test.parser;

import java.io.IOException;

interface States {
    void handle_id(Parser parser) throws IOException;
    void handle_plus(Parser parser) throws IOException;
    void handle_minus(Parser parser) throws IOException;
    void action(Parser parser);
    void handle_empty(Parser parser);
    void handle_multiply(Parser parser) throws IOException;
    void handle_division(Parser parser) throws IOException;
    void handle_left_brace(Parser parser) throws IOException;
    void handle_right_brace(Parser parser) throws IOException;
}
