'''''''''''''''''''''''''''''''''''''''
'           MULTIPLICATION            '
'''''''''''''''''''''''''''''''''''''''
'                                     '
' Autor: Reto (Nov, 2012)             '
'                                     '
' 500 = op 1                          '
' 502 = op 2                          '
' 504 = res lower                     '
' 506 = res upper                     '
' 508 = restsumme lower               '
' 510 = restsumme upper               '
' 512 = vorzeichen                    '
' 520..540 = Konstanten               '
'                                     '
'''''''''''''''''''''''''''''''''''''''

' 100:INITIALIZATION
	500=-1234
	502=4321
	520=-32768                           ' 10..0 (Vorzeichen-Check)
	522=1                                ' LSB-Check
	LWDD 2 #522                          ' 1 im Reg-2

' 102:OP 2 VORZEICHEN MERKEN
	LWDD 0 #502
	BZD #280                     ' GOTO END
	LWDD 1 #520                          ' Wenn links eine 1 rausfaellt, ist die Zahl negativ
	AND 1                                ' Mit 10..0 maskieren
	BZD #126                     ' GOTO OP 1 VORZEICHEN MERKEN
	LWDD 0 #502                          ' Frisch laden
	NOT                                  ' Mach Positiv
	INC
	SWDD 0 #502
	CLR 0                                ' Register leeren 
	ADDD #1
	SWDD 0 #512                          ' Merke Vorzeichen

' 126:OP 1 VORZEICHEN MERKEN
	LWDD 0 #500
	BZD #280                     ' GOTO END
	AND 1                                ' Mit 10..0 maskieren
	BZD #148                     ' GOTO MULTIPLICATION
	LWDD 0 #500
	NOT                                  ' Mach Positiv
	INC
	SWDD 0 #500
	LWDD 0 #512
	ADDD #1                              ' Addiere Vorzeichen
	SWDD 0 #512


' 148:MULTIPLIKATION
	LWDD 0 #500                         ' op 1 laden
	SRL                                 ' setze rechtes bit als carry
	BCD #180                     ' GOTO EINS-RECHTS

' 154:NULL-RECHTS
	SWDD 0 #500                        ' Wurde schon geschoben
	LWDD 0 #502
	SLL                                ' Multipliziere mit 2
	SWDD 0 #502
	BCD #170                     ' GOTO ADD-TO-UPPER (SKIP LINES)
	LWDD 0 #506                        ' lade res upper
	SLL                                ' schiebe upper (bei 0 passiert nix)
	BD #176                      ' GOTO STORE-UPPER (SKIP LINES)
	LWDD 0 #506		           ' IAM ADD-TO-UPPER
	SLL                                ' upper wird durch schieben erhoeht
	INC                               ' wegen overflow inkrementieren
	SWDD 0 #506                        ' IAM STORE-UPPER
	BD #148                      ' GOTO MULTIPLIKATION

' 180: EINS-RECHTS
	LWDD 0 #500                        ' op 1 neu laden, weil wir nich schieben wollen
	DEC                                ' nur eins entfernen
	BZD #216                     ' GOTO LOWER-RESTSUMME-ADD
	SWDD 0 #500                        ' wieder speichern
	LWDD 0 #502                        ' lade op 2 lower
	LWDD 1 #508                        ' lade restsumme lower
	ADD 1                              ' addiere restsumme lower zu op 2 lower
	SWDD 0 #508                        ' Speichere restsumme-lower
	LWDD 0 #506                        ' lade res upper
	LWDD 1 #510                        ' lade restsumme upper
	BCD #208                     ' GOTO INC-RESTSUMME-UPPER (SKIP LINES)
	ADD 1                              ' addiere res zu restsumme
	SWDD 0 #510                        ' schreibe neue restsumme
	BD #148                      ' GOTO MULTIPLIKATION
	INC                          ' IAM INC-RESTSUMME-UPPER
	ADD 1                              ' addiere res zu restsumme
	SWDD 0 #510                        ' schreibe restsumme upper
	BD #148                      ' GOTO MULTIPLIKATION
	

' 216:LOWER RESTSUMME ZU OP 2 ADDIEREN
	LWDD 0 #502                        ' op 2 - lower laden
	LWDD 1 #508                        ' restsumme - lower laden
	ADD 1                              ' addieren
	SWDD 0 #504                        ' res lower von op 2 uebernehmen. achtung grusig
	BCD #228                     ' GOTO INC-UPPER-RESTSUMME
	BD #240                      ' GOTO UPPER RESTSUMME ZU OP 2 UPPER ADDIEREN
	LWDD 0 #510                        ' IAM INC-UPPER-RESTSUMME
	INC
	LWDD 1 #506                        ' restsumme upper laden und grad addieren
	ADD 1
	SWDD 0 #506                        ' in res upper speichern
	BD #248                      ' GOTO SETZE VORZEICHEN


' 240:UPPER RESTSUMME ZU OP 2 UPPER ADDIEREN
	LWDD 0 #506                        ' res upper laden
	LWDD 1 #510                        ' restsumme - upper laden
	ADD 1
	SWDD 0 #506                        ' res upper speichern
	

' 248:SETZE VORZEICHEN
	LWDD 0 #512
	AND 2                             ' im register 2 ist eine 1. wenn (x & 1 == 1) -> negativ
	BZD #280                     ' GOTO END
	LWDD 0 #504                       ' res laden
	NOT                               ' basiert auf der annahme, dass (* -1) auf beide seiten funktionieret
	INC
	SWDD 0 #504                       ' res speichern
	LWDD 0 #506                       ' res upper laden
	BCD #274                     ' GOTO LOWER-INV-OVERFLOW
	NOT                               ' beim res lower invertieren gabs kein overflow
	INC
	SWDD 0 #506
	BD #280                      ' GOTO END
	NOT                               ' IAM LOWER-INV-OVERFLOW
	ADDD #2                           ' beim res lower invertieren gabs overflow, plus upper-post-invert-inc
	SWDD 0 #510


' 280:DEAD END
	END
