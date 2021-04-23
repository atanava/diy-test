package to;

import java.util.List;

//  Технология - набор материалов
public class TechModelTo {
    public TechModelTo(String techCode) {
        this.techCode = techCode;
    }

    public String techCode;
    public List<MaterialTo> rowTos;
}
