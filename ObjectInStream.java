package System;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

public class ObjectInStream extends ObjectInputStream {
    public ObjectInStream(InputStream in) throws IOException {
        super(in);
    }
    @Override
    protected void readStreamHeader() throws IOException, StreamCorruptedException {
    }
}
