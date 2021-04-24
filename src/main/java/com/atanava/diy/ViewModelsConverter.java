package com.atanava.diy;

import com.atanava.diy.RowModel;
import com.atanava.diy.to.MaterialTo;
import com.atanava.diy.to.RootModelTo;
import com.atanava.diy.to.TechModelTo;

import java.util.ArrayList;
import java.util.List;

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
            // TODO - реализовать
        }
        return roots;
    }
}
