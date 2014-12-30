/*
 * Copyright (c) 2012 - Reto Hablützel, Max Schrimpf, Désirée Sacher
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ch.zhaw.powerpc.model.instructions;

import static org.junit.Assert.assertEquals;

public final class TestUtil {

    /**
     * Helper-Funktion, die sicherstellt, dass ein String (also eine binaere char folge) dem Wert vom char entspricht.
     * Fuer Tests zu verwenden wie assertEquals.
     */
    public static void binEquals(String expected, char actual) {
        if (expected.length() != 16) {
            throw new IllegalArgumentException("Es kann nur ein 16 Bit Wort erwaretet werden! (TestUtil.binEquals)");
        }
        assertEquals(Integer.parseInt(expected, 2), actual);
    }
}
