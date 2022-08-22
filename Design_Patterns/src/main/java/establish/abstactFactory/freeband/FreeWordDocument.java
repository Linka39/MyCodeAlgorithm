package establish.abstactFactory.freeband;

import establish.abstactFactory.WordDocument;

import java.io.IOException;
import java.nio.file.Path;

public class FreeWordDocument implements WordDocument {
    private String name;
    public FreeWordDocument(){}

    public FreeWordDocument(String name){
        this.name = name;
    }

    @Override
    public void save(Path path) throws IOException {
        System.out.println("FreeWordDocument 已保存");
    }
}
