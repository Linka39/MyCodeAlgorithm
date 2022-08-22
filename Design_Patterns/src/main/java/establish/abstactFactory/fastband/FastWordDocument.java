package establish.abstactFactory.fastband;

import establish.abstactFactory.WordDocument;

import java.io.IOException;
import java.nio.file.Path;

public class FastWordDocument implements WordDocument {
    private String name;
    public FastWordDocument(){}

    public FastWordDocument(String name){
        this.name = name;
    }

    @Override
    public void save(Path path) throws IOException {
        System.out.println("FastWordDocument 已保存");
    }
}
