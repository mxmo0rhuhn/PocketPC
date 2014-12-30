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

import ch.zhaw.powerpc.model.ControlUnit;

/**
 * Eine Instruktion ist ein Befehl für unseren PowerPC. Pro Befehl wird ein Objekt instanziert, welcher dann anhand der
 * ControlUnit ausgeführt werden kann (Methode run).
 *
 * @author Max / Reto
 */
public interface Instruction {

    /**
     * Zu diesem Zeitpunkt muss die Instruktion wissen, welche Operanden Sie benötigt und diese entsprechend aus dem
     * Hauptspeicher lesen können. Auf den Hauptspeicher kann über die ControlUnit zugegriffen werden.
     * <p/>
     * Danach werden allfällige Werte aus dem Register gelesen (über die ControlUnit).
     * <p/>
     * Schlussendlich wird die eigentliche Operation mit den Operanden ausgeführt.
     *
     * @param controlUnit Für Zugriff auf Hauptspeicher, Program Counter, Register, usw
     * @return Der neue (absolute!) Programm Counter
     */
    int run(ControlUnit controlUnit);

    char getBinary();
}
