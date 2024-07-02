
public enum Tipi {
	NORMALE(1),
	LOTTA(2),
	VOLANTE(3),
	VELENO(4),
	TERRA(5),
	ROCCIA(6),
	COLEOTTERO(7),
	SPETTRO(8),
	FUOCO(9),
	ACQUA(10),
	ERBA(11),
	ELETTRO(12),
	PSICO(13),
	GHIACCIO(14),
	DRAGO(15);
	
	final int typeNumber;
	
	Tipi (int typeNumber){
		this.typeNumber=typeNumber;
	}
}