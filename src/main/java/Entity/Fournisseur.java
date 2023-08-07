package Entity;

public class Fournisseur {
private int fid;
private String fnom;
private String ftel;
private String fadresse;
private int fdelais;
public int getFid() {
	return fid;
}
public void setFid(int fid) {
	this.fid = fid;
}
public String getFnom() {
	return fnom;
}
public void setFnom(String fnom) {
	this.fnom = fnom;
}
public String getFtel() {
	return ftel;
}
public void setFtel(String ftel) {
	this.ftel = ftel;
}
public String getFadresse() {
	return fadresse;
}
public void setFadresse(String fadresse) {
	this.fadresse = fadresse;
}
public int getFdelais() {
	return fdelais;
}
public void setFdelais(int fdelais) {
	this.fdelais = fdelais;
}
public Fournisseur() {
	super();
	// TODO Auto-generated constructor stub
}
public Fournisseur(int fid, String fnom, String ftel, String fadresse, int fdelais) {
	super();
	this.fid = fid;
	this.fnom = fnom;
	this.ftel = ftel;
	this.fadresse = fadresse;
	this.fdelais = fdelais;
}
public Fournisseur(String fnom, String ftel, String fadresse, int fdelais) {
	super();
	this.fnom = fnom;
	this.ftel = ftel;
	this.fadresse = fadresse;
	this.fdelais = fdelais;
}
public Fournisseur(String tfourn) {
	this.fnom = tfourn;
}
@Override
public String toString() {
	return "Fournisseur [fid=" + fid + ", fnom=" + fnom + ", ftel=" + ftel + ", fadresse=" + fadresse + ", fdelais="
			+ fdelais + "]";
}
public Fournisseur(int fid) {
	super();
	this.fid = fid;
}


}
