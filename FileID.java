package System;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileID extends File {
    private boolean isFile = false;
    public FileID(@NotNull String pathname) {
        super(pathname);
    }

    @Override
    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }
}
