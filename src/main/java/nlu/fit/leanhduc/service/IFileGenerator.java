package nlu.fit.leanhduc.service;

import java.io.IOException;

public interface IFileGenerator {
    boolean loadKey(String src) throws IOException;

    boolean saveKey(String dest) throws IOException;
}
