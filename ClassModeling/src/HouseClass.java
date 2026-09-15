public class HouseClass {

    public static void main(String[] args) {
        House myHouse = new House("G20 0LF", "1/2, 98, fingal street");
        myHouse.getAddy();
        myHouse.setAddy("Fingals");
        System.out.println(myHouse.getAddy());
    }


}
