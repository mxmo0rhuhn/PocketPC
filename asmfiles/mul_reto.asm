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
	500=-1024
	502=1024
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
	SWDD 0 #500                         ' op 1 speichern
	BCD #180                     ' GOTO EINS-RECHTS

' 156:NULL-RECHTS
	LWDD 0 #502                        ' IAM SHIFT-OP2-MUL-DIRECT (lade op2 lower)
	SLL                                ' schiebe op2 lower nach links
	SWDD 0 #502                        ' speichere op 2 lower
	BCD #170                     ' GOTO ADD-TO-UPPER (SKIP LINES)
	LWDD 0 #506                        ' lade res upper
	SLL                                ' schiebe upper (bei 0 passiert nix)
	BD #176                      ' GOTO STORE-UPPER (SKIP LINES)
	LWDD 0 #506		           ' IAM ADD-TO-UPPER (#170)
	SLL                                ' upper wird durch schieben erhoeht
	INC                               ' wegen overflow inkrementieren
	SWDD 0 #506                        ' IAM STORE-UPPER (#176)
	BD #148                      ' GOTO MULTIPLIKATION

' 180: EINS-RECHTS
	BZD #210                     ' GOTO LOWER-RESTSUMME-ADD
	LWDD 0 #502                        ' op 2 lower laden
	LWDD 1 #508                        ' lade restsumme lower
	ADD 1                              ' addiere restsumme zu lower (-> neue restsumme)
	SWDD 0 #508                        ' speichere restsumme lower
	LWDD 0 #506                        ' lade res upper
	LWDD 1 #510                        ' lade restsumme upper
	BCD #202                     ' GOTO INC-WITH-LOWERFLOW
	ADD 1                              ' es gab kein overflow beim lower
	SWDD 0 #510                        ' speichere restsumme upper
	BD #156                      ' GOTO SHIFT-OP2-MUL-DIRECT
	INC                                ' IAM INC-WITH-LOWERFLOW (#202) (es gab einen overflow beim lower)
	ADD 1                              ' addiere res upper zu restsumme upper
	SWDD 0 #510                        ' speichere restsumme upper
	BD #156                      ' GOTO SHIFT-OP2-MUL-DIRECT


' 210:LOWER RESTSUMME ZU OP 2 ADDIEREN
	LWDD 0 #502                        ' op 2 - lower laden
	LWDD 1 #508                        ' restsumme - lower laden
	ADD 1                              ' addieren
	SWDD 0 #504                        ' res lower von op 2 uebernehmen. achtung grusig
	BCD #222                     ' GOTO INC-UPPER-RESTSUMME
	BD #234                      ' GOTO UPPER RESTSUMME ZU OP 2 UPPER ADDIEREN
	LWDD 0 #510                        ' IAM INC-UPPER-RESTSUMME (#222)
	INC
	LWDD 1 #506                        ' restsumme upper laden und grad addieren
	ADD 1
	SWDD 0 #506                        ' in res upper speichern
	BD #242                      ' GOTO SETZE VORZEICHEN


' 234:UPPER RESTSUMME ZU OP 2 UPPER ADDIEREN
	LWDD 0 #506                        ' res upper laden
	LWDD 1 #510                        ' restsumme - upper laden
	ADD 1
	SWDD 0 #506                        ' res upper speichern
	

' 242:SETZE VORZEICHEN
	LWDD 0 #512
	AND 2                             ' im register 2 ist eine 1. wenn (x & 1 == 1) -> negativ
	BZD #280                     ' GOTO END
	LWDD 0 #504                       ' res lower laden
	NOT                               ' basiert auf der annahme, dass (* -1) auf beide seiten funktionieret
	INC
	SWDD 0 #504                       ' res speichern
	LWDD 0 #506                       ' res upper laden
	BCD #274                     ' GOTO LOWER-INV-OVERFLOW
	BZD #268                     ' GOTO UPPERZERO INVERT
	NOT                               ' beim res lower invertieren gabs kein overflow
	SWDD 0 #506                       ' speichern von res lower invertieren
	BD #280                      ' GOTO END
	NOT                               ' IAM UPPERZERO INVERT (#268) (weil res negativ und upper null -> upper invertieren)
	SWDD 0 #506
	BD #280                      ' GOTO END
	NOT                               ' IAM LOWER-INV-OVERFLOW (#274)
	INC                               ' beim res lower invertieren gabs overflow, plus upper-post-invert-inc
	SWDD 0 #506


' 280:DEAD END
	END
