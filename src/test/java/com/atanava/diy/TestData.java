package com.atanava.diy;

import com.atanava.diy.to.MaterialTo;
import com.atanava.diy.to.PositionType;
import com.atanava.diy.to.RootModelTo;
import com.atanava.diy.to.TechModelTo;

import java.util.List;

public class TestData {

    static final RowModel rowMat1;
    static final RowModel rowMat2;
    static final RowModel rowMat3;

    static final RowModel rowTech1;
    static final RowModel rowTech2;
    static final RowModel rowTech3;

    static final RowModel rowRoot1;
    static final RowModel rowRoot2;
    static final RowModel rowRoot3;

    static final MaterialTo materialTo1;
    static final MaterialTo materialTo2;
    static final MaterialTo materialTo3;

    static final TechModelTo techModelTo1;
    static final TechModelTo techModelTo2;
    static final TechModelTo techModelTo3;

    static final RootModelTo rootModelTo1;
    static final RootModelTo rootModelTo2;
    static final RootModelTo rootModelTo3;


    static {
        rowMat1 = new RowModel();
        rowMat1.anyCode = "matCode1";
        rowMat1.positionType = PositionType.MATERIAL;

        rowMat2 = new RowModel();
        rowMat2.anyCode = "matCode2";
        rowMat2.positionType = PositionType.MATERIAL;

        rowMat3 = new RowModel();
        rowMat3.anyCode = "matCode3";
        rowMat3.positionType = PositionType.MATERIAL;


        rowTech1 = new RowModel();
        rowTech1.anyCode = "techCode1";
        rowTech1.positionType = PositionType.TECHNOLOGY;

        rowTech2 = new RowModel();
        rowTech2.anyCode = "techCode2";
        rowTech2.positionType = PositionType.TECHNOLOGY;

        rowTech3 = new RowModel();
        rowTech3.anyCode = "techCode3";
        rowTech3.positionType = PositionType.TECHNOLOGY;


        rowRoot1 = new RowModel();
        rowRoot1.anyCode = "root1";
        rowRoot1.positionType = PositionType.ROOT;

        rowRoot2 = new RowModel();
        rowRoot2.anyCode = "root2";
        rowRoot2.positionType = PositionType.ROOT;

        rowRoot3 = new RowModel();
        rowRoot3.anyCode = "root3";
        rowRoot3.positionType = PositionType.ROOT;


        materialTo1 = new MaterialTo("matCode1");
        materialTo2 = new MaterialTo("matCode2");
        materialTo3 = new MaterialTo("matCode3");


        techModelTo1 = new TechModelTo("techCode1");
        techModelTo1.rowTos = List.of(materialTo1);

        techModelTo2 = new TechModelTo("techCode2");
        techModelTo2.rowTos = List.of(materialTo1, materialTo2);

        techModelTo3 = new TechModelTo("techCode3");
        techModelTo3.rowTos = List.of(materialTo1, materialTo2, materialTo3);


        rootModelTo1 = new RootModelTo();
        rootModelTo1.techList = List.of(techModelTo1);

        rootModelTo2 = new RootModelTo();
        rootModelTo2.techList = List.of(techModelTo2);

        rootModelTo3 = new RootModelTo();
        rootModelTo3.techList = List.of(techModelTo3);


    }

    static List<RowModel> getTechModelInput() {
        return List.of(rowTech3, rowMat1, rowMat2, rowMat3);
    }

}
