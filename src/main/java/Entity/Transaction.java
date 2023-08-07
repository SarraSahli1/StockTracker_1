package Entity;

import java.sql.Date;

public class Transaction {
	private int Tid;
	private Date Tdate;
	private int Tentree;
	private float Tprixtotal;
	private Produit Tprod;
	private Fournisseur Tfourn;
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Transaction(int tid, java.util.Date tdate2, int tentree, float tprixtotal, Produit tprod, Fournisseur tfourn) {
		Tid = tid;
		Tdate = (Date) tdate2;
		Tentree = tentree;
		Tprixtotal = tprixtotal;
		Tprod = tprod;
		Tfourn = tfourn;
	}
	public Transaction(int tid, Date tdate, int tentree, float tprixtotal, String tprod, String tfourn) {
		Tid = tid;
		Tdate = tdate;
		Tentree = tentree;
		Tprixtotal = tprixtotal;
        this.Tprod = new Produit(tprod);
        this.Tfourn = new Fournisseur(tfourn);
	}
	
	
	public Transaction(int tid2, java.util.Date tdate2, int tentree2, float tprixtotal2, int tprodId, int tfournId) {
		Tid = tid2;
		Tdate = (Date) tdate2;
		Tentree = tentree2;
		Tprixtotal = tprixtotal2;
		 this.Tprod = new Produit(tprodId);
	        this.Tfourn = new Fournisseur(tfournId);
	}
	public int getTid() {
		return Tid;
	}
	public void setTid(int tid) {
		Tid = tid;
	}
	public Date getTdate() {
		return Tdate;
	}
	public void setTdate(Date tdate) {
		Tdate = tdate;
	}
	public int getTentree() {
		return Tentree;
	}
	public void setTentree(int tentree) {
		Tentree = tentree;
	}
	public float getTprixtotal() {
		return Tprixtotal;
	}
	public void setTprixtotal(float tprixtotal) {
		Tprixtotal = tprixtotal;
	}
	public Produit getTprod() {
		return Tprod;
	}
	public void setTprod(Produit tprod) {
		Tprod = tprod;
	}
	public Fournisseur getTfourn() {
		return Tfourn;
	}
	public void setTfourn(Fournisseur tfourn) {
		Tfourn = tfourn;
	}
	@Override
	public String toString() {
		return "Transaction [Tid=" + Tid + ", Tdate=" + Tdate + ", Tentree=" + Tentree + ", Tprixtotal=" + Tprixtotal
				+ ", Tprod=" + Tprod + ", Tfourn=" + Tfourn + "]";
	}
	
	
	
}
