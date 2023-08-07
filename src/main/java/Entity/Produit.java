package Entity;

import java.util.List;

public class Produit {
    private int prodid;
    private String prodlib;
    private String proddesc;
    private String prodimg;
    private float prodprix;
    private int prodquant;
    private List<String> taille;
    private List<String> couleur;
    private Categorie prodcat;

    public Produit() {
        super();
    }

    public Produit(int prodid) {
		super();
		this.prodid = prodid;
	}

	public Produit(int prodid, String prodlib, String proddesc, String prodimg, float prodprix, int prodquant,
            List<String> taille, List<String> couleur, Categorie prodcat) {
        this.prodid = prodid;
        this.prodlib = prodlib;
        this.proddesc = proddesc;
        this.prodimg = prodimg;
        this.prodprix = prodprix;
        this.prodquant = prodquant;
        this.taille = taille;
        this.couleur = couleur;
        this.prodcat = prodcat;
    }

    public Produit(String prodlib, String proddesc, String prodimg, float prodprix, int prodquant, List<String> taille,
            List<String> couleur, String prodcat) {
        this.prodlib = prodlib;
        this.proddesc = proddesc;
        this.prodimg = prodimg;
        this.prodprix = prodprix;
        this.prodquant = prodquant;
        this.taille = taille;
        this.couleur = couleur;
        this.prodcat = new Categorie(prodcat);
    }

    

	public Produit(String prodlib2, String proddesc2, float prodprix2, int prodquant2, String prodimg2,
			List<String> taille2, List<String> couleur2) {
		this.prodlib = prodlib2;
        this.proddesc = proddesc2;
        this.prodprix = prodprix2;
        this.prodquant = prodquant2;
        this.prodimg=prodimg2;
        this.taille = taille2;
        this.couleur = couleur2;	}

	public Produit(String prodlib, String proddesc, float prodprix, int prodquant,String prodimg,
            List<String> taille, List<String> couleur, Categorie prodcat) {
	        this.prodlib = prodlib;
	        this.proddesc = proddesc;
	        this.prodimg = prodimg;
	        this.prodprix = prodprix;
	        this.prodquant = prodquant;
	        this.taille = taille;
	        this.couleur = couleur;
	        this.prodcat = prodcat;	}

	public Produit(String tprod) {
		this.prodlib=tprod;
	}

	public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public String getProdlib() {
        return prodlib;
    }

    public void setProdlib(String prodlib) {
        this.prodlib = prodlib;
    }

    public String getProddesc() {
        return proddesc;
    }

    public void setProddesc(String proddesc) {
        this.proddesc = proddesc;
    }

    public String getProdimg() {
        return prodimg;
    }

    public void setProdimg(String prodimg) {
        this.prodimg = prodimg;
    }

    public float getProdprix() {
        return prodprix;
    }

    public void setProdprix(float prodprix) {
        this.prodprix = prodprix;
    }

    public int getProdquant() {
        return prodquant;
    }

    public void setProdquant(int prodquant) {
        this.prodquant = prodquant;
    }

    public List<String> getTaille() {
        return taille;
    }

    public void setTaille(List<String> taille) {
        this.taille = taille;
    }

    public List<String> getCouleur() {
        return couleur;
    }

    public void setCouleur(List<String> couleur) {
        this.couleur = couleur;
    }

    public Categorie getProdcat() {
        return prodcat;
    }

    public void setProdcat(Categorie prodcat) {
        this.prodcat = prodcat;
    }

    @Override
    public String toString() {
        return "Produit [prodid=" + prodid + ", prodlib=" + prodlib + ", proddesc=" + proddesc + ", prodimg=" + prodimg
                + ", prodprix=" + prodprix + ", prodquant=" + prodquant + ", taille=" + taille + ", couleur=" + couleur
                + ", prodcat=" + prodcat + "]";
    }
}
