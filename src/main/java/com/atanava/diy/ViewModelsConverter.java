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

        if (source == null || source.isEmpty()) {
            throw new IllegalArgumentException("Source must not be empty");
        } else if (!source.get(0).positionType.equals(PositionType.ROOT)) {
            throw new IllegalArgumentException("First element must be ROOT");
        }

        for (Map.Entry<String, List<RowModel>> entry : convertToMap(source).entrySet()) {
            RootModelTo rootModelTo = new RootModelTo();
            rootModelTo.rootCode = entry.getKey();
            rootModelTo.techList = new ArrayList<>();
            rootModelTo.techList.add(toTechModel(entry.getValue()));
            roots.add(rootModelTo);
        }

        return roots;
    }

    private static Map<String, List<RowModel>> convertToMap(List<RowModel> source) {
        Map<String, List<RowModel>> rootsMap = new HashMap<>();
        List<RowModel> techs = null;

        for (RowModel rowModel : source) {
            if (rowModel.positionType.equals(PositionType.ROOT)) {
                techs = new ArrayList<>();
                rootsMap.put(rowModel.anyCode, techs);
            } else {
                techs.add(rowModel);
            }
        }
        return rootsMap;
    }
}
