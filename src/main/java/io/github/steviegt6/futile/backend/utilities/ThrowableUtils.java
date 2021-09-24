package io.github.steviegt6.futile.backend.utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public final class ThrowableUtils {
    public static String throwableToString(Throwable throwable) {
        if (throwable == null)
            return "Null exception.";

        StringWriter writer = new StringWriter();
        PrintWriter printer = new PrintWriter(writer);

        try {
            throwable.printStackTrace(printer);
            return writer.getBuffer().toString();
        }
        catch(Exception e) {
            return "Failed to write exception stack-trace to a string.";
        }
        finally {
            try {
                writer.close();
                printer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
