'''''''''''''''''''''''''''''''''''''''
'           MULTIPLICATION            '
'''''''''''''''''''''''''''''''''''''''
'                                     '
' Autor: Reto (Nov, 2012)             '
'                                     '
' 500 = op 1                          '
' 502 = op 2                          '
' 504 = vorzeichen ( 1 = negativ)     '
' 506 = restsumme lower               '
' 508 = restsumme upper               '
' 510 = op 2 - upper
' 520..540 = addresses                '
'                                     '
'''''''''''''''''''''''''''''''''''''''

' OP 2 VORZEICHEN MERKEN
	LWDD 0 #502
	BZ                           ' GOTO END
	SLL                                   ' Wenn links eine 1 rausfaellt, ist die Zahl negativ
	BNC                          ' GOTO OP 1 VORZEICHEN
	LWDD 0 #502                           ' Frisch laden
	NOT                                   ' Mach Positiv
	INC
	SWDD 0 #502
	CLR
	ADDD #1
	SWDD 0 #504                          ' Merke Vorzeichen

'# OP 1 VORZEICHEN MERKEN
	LWDD 0 #500
	BZ                           ' GOTO END
	SLL                                  ' Wenn links eine 1 rausfaellt, ist die Zahl negativ
	BNC                          ' GOTO MULTIPLICATION
	LWDD 0 #500
	NOT                                  ' Mach Positiv
	INC
	SWDD 0 #500
	LWDD 0 #504
	ADDD #1                              ' Addiere Vorzeichen
	SWDD 0 #504



'# MULTIPLICATION
	LWDD 0 #500
	SRL                                 ' Zahl kann nicht negativ sein
	BNC                          ' GOTO NULL-RECHTS

'# EINS-RECHTS
	DEC
	SWDD 0 #500
	LWDD 0 #502
	LWDD 1 #506                        ' Lade restsumme-lower
	ADD 1                              ' Addiere op 1 zu restsumme
	SWDD 0 #506                        ' Speichere restsumme-upper
	BNC                          ' GOTO MULTIPLICATION
	LWDD 0 #508                        ' Lade restsumme-upper wegen overflow
	INC
	SWDD 0 #508

'# NULL-RECHTS
	SWDD 0 #500                        ' Wurde schon geschoben
	LWDD 0 #502
	SLL                                ' Multipliziere mit 2
	SWDD 0 #502
	LWDD 0 #510
	SLL                                ' Erhoehe in jedem Fall (bei 0 passiert nix)
	BNC                          ' GOTO STORE UPPER (skip line)
	OR 1                               ' 1 ist lower rausgefallen, fuege diese upper ein
	SWDD 0 #510
