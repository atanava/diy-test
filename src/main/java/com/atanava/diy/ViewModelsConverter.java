package com.atanava.diy;

import com.atanava.diy.to.MaterialTo;
import com.atanava.diy.to.PositionType;
import com.atanava.diy.to.RootModelTo;
import com.atanava.diy.to.TechModelTo;

import java.util.*;

public class ViewModelsConverter {

    // Сюда приходит список:  TECHNOLOGY,MATERIAL,MATERIAL,..,MATERIAL
    public static TechModelTo toTechModel(List<RowModel> source) {
        TechModelTo result = null;
        for (RowModel rowModel : source) {
            switch (rowModel.positionType) {
                case ROOT -> throw new IllegalArgumentException();
                case TECHNOLOGY -> {
                    if (result != null) throw new IllegalArgumentException();
                    result = new TechModelTo(rowModel.anyCode);
                    result.rowTos = new ArrayList<>();
                }
                case MATERIAL -> {
                    if (result == null) throw new IllegalArgumentException();
                    result.rowTos.add(new MaterialTo(rowModel.anyCode));
                }
            }
        }
        return result;
    }

    // Сюда приходит список:  ROOT,TECHNOLOGY,MATERIAL,MATERIAL,..,MATERIAL, [ROOT,TECHNOLOGY,MATERIAL,MATERIAL,..,MATERIAL], ...
    public static List<RootModelTo> toRootModels(List<RowModel> source) {
        List<RootModelTo> roots = new ArrayList<>();

        if (source.isEmpty()) {
            return roots;
        }

        RootModelTo rootModelTo = null;
        List<RowModel> technologyAndMaterials = new ArrayList<>();

        for (RowModel model : source) {
            switch (model.positionType) {
                case ROOT -> {
                    if (rootModelTo != null && technologyAndMaterials.size() > 0) {
                        rootModelTo.techList.add(toTechModel(technologyAndMaterials));
                        technologyAndMaterials = new ArrayList<>();
                    }
                    rootModelTo = new RootModelTo();
                    rootModelTo.rootCode = model.anyCode;
                    rootModelTo.techList = new ArrayList<>();
                    roots.add(rootModelTo);
                }
                case TECHNOLOGY, MATERIAL -> {
                    if (rootModelTo == null)
                        throw new IllegalArgumentException("First element in source must be ROOT");
                    if (model.positionType == PositionType.TECHNOLOGY && technologyAndMaterials.size() > 0) {
                        rootModelTo.techList.add(toTechModel(technologyAndMaterials));
                        technologyAndMaterials = new ArrayList<>();
                    }
                    if (model.positionType == PositionType.MATERIAL && technologyAndMaterials.isEmpty())
                        throw new IllegalArgumentException("Incorrect order of elements in source");
                    technologyAndMaterials.add(model);
                }
            }
        }
        rootModelTo.techList.add(toTechModel(technologyAndMaterials));

        return roots;
    }
}
