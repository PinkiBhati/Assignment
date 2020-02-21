import java.util.*;
import java.util.Map.Entry;

public class Q4 {
    public static void main(String[] args) {
        HashMap<String, Integer> obj = new HashMap<String, Integer>();
        obj.put("Pinki", 23);
        obj.put("John", 33);
        obj.put("Divya", 43);
        obj.put("Kashish", 61);
        obj.put("Aditi", 34);
        obj.put("Nidhi", 84);
        Map<String, Integer> obj1 = sorting(obj);

        for (Map.Entry<String, Integer> i : obj1.entrySet()) {
            System.out.println("Key=" + i.getKey() + "    " + "Value=" + i.getValue());
        }

    }



    public static HashMap<String, Integer> sorting(HashMap<String, Integer> h) {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(h.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> t2, Map.Entry<String, Integer> t1) {
                return (t2.getValue()).compareTo(t1.getValue());
            }
        });
        HashMap<String,Integer> hashmap=new LinkedHashMap<String,Integer>();
        for(Map.Entry<String,Integer> i: list)
        {
            hashmap.put(i.getKey(),i.getValue());
        }
        return hashmap;
    }
}
