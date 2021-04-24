package com.atanava.diy;

import com.atanava.diy.to.TechModelTo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.atanava.diy.ViewModelsConverter.*;
import static com.atanava.diy.TestData.*;

class ViewModelsConverterTest {

    @Test
    void toTechModelTest() {
        TechModelTo actual = toTechModel(getTechModelInput());
        assertEquals(techModelTo3.techCode, actual.techCode);

        for (int i = 0; i < actual.rowTos.size(); i++) {
            assertEquals(techModelTo3.rowTos.get(i).materialCode, actual.rowTos.get(i).materialCode);
        }
    }

    @Test
    void toTechModelWithRootType() {
        assertThrows(IllegalArgumentException.class, () -> toTechModel(List.of(rowRoot1, rowTech1)));
    }

    @Test
    void toTechModelWithMultiTech() {
        assertThrows(IllegalArgumentException.class, () -> toTechModel(List.of(rowTech1, rowTech2, rowMat1, rowMat2)));
    }

    @Test
    void toRootModelsTest() {
    }
}