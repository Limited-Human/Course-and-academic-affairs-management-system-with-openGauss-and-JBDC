package studentdemo;
public class depart {
	private String dpname;
	private String dpnber;
    public depart(String a, String b){
    	dpname=b;
    	dpnber=a;
    }
    public String toString(){
           return dpname;
    }
    public String toNber(){
        return dpnber;
 }
}