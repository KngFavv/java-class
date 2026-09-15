public class House {
    private String postCode;
    private String address;

    public House(){

    }

    public House(String postalCode, String addy){
        this.postCode = postalCode;
        this.address = addy;

    }

    public String getPostCode(){
        return postCode;
    }

    public void setPostCode(String postalCode){
        this.postCode = postalCode;
    }

    public String getAddy(){
        return address;
    }

    public void setAddy(String addy){
        this.address = addy;
    }
}
