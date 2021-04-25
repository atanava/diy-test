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
            throw new IllegalArgumentException("Source must not be empty");
        } else if (source.get(0).positionType != PositionType.ROOT) {
            throw new IllegalArgumentException("First element must be ROOT");
        }

        RootModelTo rootModelTo = null;
        RowModel previous = null;
        List<RowModel> techAndMats = null;

        for (int i = 0; i < source.size(); i++) {
            RowModel model = source.get(i);

            switch (model.positionType) {
                case ROOT -> {
                    if (previous != null && (previous.positionType == PositionType.ROOT || previous.positionType == PositionType.TECHNOLOGY)) {
                        throw new IllegalArgumentException("Incorrect order of elements in source");
                    }
                    rootModelTo = new RootModelTo();
                    rootModelTo.rootCode = model.anyCode;
                    rootModelTo.techList = new ArrayList<>();
                    roots.add(rootModelTo);
                }
                case TECHNOLOGY -> {
                    if (previous == null || previous.positionType == PositionType.TECHNOLOGY) {
                        throw new IllegalArgumentException("Incorrect order of elements in source");
                    }
                    techAndMats = new ArrayList<>();
                    techAndMats.add(model);
                }
                case MATERIAL -> {
                    if (previous == null || previous.positionType == PositionType.ROOT) {
                        throw new IllegalArgumentException("Incorrect order of elements in source");
                    }
                    techAndMats.add(model);
                    if (i == source.size() - 1 || source.get(i + 1).positionType != PositionType.MATERIAL) {
                        rootModelTo.techList.add(toTechModel(techAndMats));
                    }
                }
            }
            previous = model;
        }

        return roots;
    }
}
