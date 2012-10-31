500=13
502=221
504=104

' operand 2 dekrementieren (weil
'  sonst einmal zu viel multipli
'  ziert wird)
' Register 1 haelt sprungadresse
' --> das ganze nur machen wenn
'     keine zahl null is
' operand 1 in register 2
' operand 1 in akku laden
' akku mit sich selbst addieren
' akku speichern
' operand 2 in akku
' akku/operand 2 dekrementieren
' akku/operand 2 speichern
' wenn akku > 0
'   - branch wieder an anfang

LWDD 2 #500
LWDD 1 #504
LWDD 0 #500
ADD 2
SWDD 0 #500
LWDD 0 #502
DEC
SWDD 0 #502
DEC
BNZ 1
END
