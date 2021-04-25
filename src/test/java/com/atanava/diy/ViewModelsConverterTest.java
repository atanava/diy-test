package com.atanava.diy;

import com.atanava.diy.to.RootModelTo;
import com.atanava.diy.to.TechModelTo;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.atanava.diy.ViewModelsConverter.*;
import static com.atanava.diy.TestData.*;

class ViewModelsConverterTest {

    @Test
    void toTechModelTest() {
        TechModelTo actual = toTechModel(getToTechModelInput());
        assertEquals(techModelTo3.techCode, actual.techCode);

        for (int i = 0; i < techModelTo3.rowTos.size(); i++) {
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
        List<RootModelTo> expected = getExpectedRootModels();
        List<RootModelTo> actual = toRootModels(getToRootModelsInput());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).rootCode, actual.get(i).rootCode);
            List<TechModelTo> expectedTechs = expected.get(i).techList;
            List<TechModelTo> actualTechs = actual.get(i).techList;

            for (int j = 0; j < expectedTechs.size(); j++) {
                TechModelTo expTech = expectedTechs.get(j);
                TechModelTo actTech = actualTechs.get(j);
                assertEquals(expTech.techCode, actTech.techCode);

                for (int k = 0; k < expTech.rowTos.size(); k++) {
                    assertEquals(expTech.rowTos.get(k).materialCode, actTech.rowTos.get(k).materialCode);
                }
            }
        }
    }

    @Test
    void toRootModelsWithEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> toRootModels(null));
        assertIterableEquals(Collections.emptyList(), toRootModels(Collections.emptyList()));
    }

    @Test
    void toRootModelsWithWrongElementsOrder() {
        assertThrows(IllegalArgumentException.class, () -> toRootModels(List.of(rowTech1, rowMat1, rowMat2)));
        assertThrows(IllegalArgumentException.class, () -> toRootModels(List.of(rowRoot1, rowMat1, rowTech1)));
        assertThrows(IllegalArgumentException.class, () -> toRootModels(List.of(rowRoot1, rowRoot2, rowMat1)));
    }
}