package Entity;


public class Categorie {
private int catid;
private String catlib;



public Categorie() {
	super();
}

public Categorie(String catlib) {
	super();
	this.catlib = catlib;
}

public Categorie(int catid, String catlib) {
	super();
	this.catid = catid;
	this.catlib = catlib;
}
public int getCatid() {
	return catid;
}
public void setCatid(int catid) {
	this.catid = catid;
}
public String getCatlib() {
	return catlib;
}
public void setCatlib(String catlib) {
	this.catlib = catlib;
}

@Override
public String toString() {
	return catlib;
}


}

