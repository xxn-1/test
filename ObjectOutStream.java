package System;

import java.io.*;

public class ObjectOutStream extends ObjectOutputStream {
    public ObjectOutStream(OutputStream out) throws IOException,SecurityException{
        super(out);
    }
    @Override
    protected void writeStreamHeader() throws IOException {
        super.reset();
    }
}

