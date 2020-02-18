public class Q3 {
    public static void main(String[] args) {
        String str="Abstract class: is a restricted class that cannot be used to create objects".toLowerCase();
        int a = str.length() - str.replace("a", "").length();
        System.out.println("Occurence of a : "+a);
    }
}
