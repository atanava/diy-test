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

        if (source == null) {
            throw new IllegalArgumentException("Source must not be null");
        } else if (source.isEmpty()) {
            return roots;
        } else if (source.get(0).positionType != PositionType.ROOT) {
            throw new IllegalArgumentException("First element must be ROOT");
        }

        RootModelTo rootModelTo = null;
        List<RowModel> technologyAndMaterials = null;
        List<List<RowModel>> technologiesOfRoot = null;

        for (RowModel model : source) {
            switch (model.positionType) {
                case ROOT -> {
                    if (rootModelTo != null) {
                        for (List<RowModel> rowModels : technologiesOfRoot)
                            rootModelTo.techList.add(toTechModel(rowModels));
                    }
                    rootModelTo = new RootModelTo();
                    rootModelTo.rootCode = model.anyCode;
                    rootModelTo.techList = new ArrayList<>();
                    roots.add(rootModelTo);

                    technologyAndMaterials = null;
                    technologiesOfRoot = new ArrayList<>();
                }
                case TECHNOLOGY, MATERIAL -> {
                    if (model.positionType == PositionType.TECHNOLOGY) {
                        technologyAndMaterials = new ArrayList<>();
                        technologiesOfRoot.add(technologyAndMaterials);
                    }
                    if (technologyAndMaterials == null)
                        throw new IllegalArgumentException("Incorrect order of elements in source");
                    technologyAndMaterials.add(model);
                }
            }
        }
        if (rootModelTo != null) {
            for (List<RowModel> rowModels : technologiesOfRoot)
                rootModelTo.techList.add(toTechModel(rowModels));
        }

        return roots;
    }
}
