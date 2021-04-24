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

        convertToMap(source)
                .forEach((key, value) -> {
                    RootModelTo rootModelTo = new RootModelTo();
                    rootModelTo.rootCode = key;
                    rootModelTo.techList = new ArrayList<>();
                    roots.add(rootModelTo);
                    value.forEach(list -> rootModelTo.techList.add(toTechModel(list)));
                });

        return roots;
    }


    private static NavigableMap<String, Deque<List<RowModel>>> convertToMap(List<RowModel> source) {

        NavigableMap<String, Deque<List<RowModel>>> roots = new TreeMap<>();
        Deque<List<RowModel>> techs;

        for (RowModel rowModel : source) {
            if (rowModel.positionType.equals(PositionType.ROOT)) {
                techs = new LinkedList<>();
                roots.put(rowModel.anyCode, techs);

            } else if (rowModel.positionType.equals(PositionType.TECHNOLOGY)) {
                roots.get(roots.lastKey()).add(new LinkedList<>());
                roots.get(roots.lastKey()).getLast().add(rowModel);

            } else {
                roots.get(roots.lastKey()).getLast().add(rowModel);
            }
        }
        return roots;
    }
}
