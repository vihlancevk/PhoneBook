package interfaces;

import java.io.IOException;
import java.nio.file.Path;

public interface JsonHandler<T> {
    T read(Path path) throws IOException;
    void write(Path path, T t) throws IOException;
}
