package studentdemo;

public class stu_courr {
    public String stuname;
    public String  stunber;
    public String courname;
    public String  cournber;
    public stu_courr(String a, String b,String c, String d){
    	stuname=b;
    	stunber=a;
    	courname=d;
    	cournber=c;	
    }
    public String tostuname(){
        return stuname;
    }
    public String tostunber(){
        return stunber;
 }
    public String tocourname(){
        return courname;
 } 
    public String tocourner(){
        return cournber;
 }    
}