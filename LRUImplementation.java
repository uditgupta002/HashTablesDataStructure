

import java.util.Scanner;
import java.util.*;



class LRUImplementation
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		while(t>0)
		{
			Set<Integer> s = new HashSet<Integer>() ;
				int n = sc.nextInt();
				LRUCache g = new LRUCache(n);
			int q = sc.nextInt();
			
			while(q>0)
			{
			
				String c = sc.next();
			//	System.out.println(c);
				if(c.equals("GET"))
				{
					int x = sc.nextInt();
					System.out.print(g.get(x)+" ");
				}
				if(c.equals("SET"))
				{
					int x = sc.nextInt();
					int y  = sc.nextInt();
					g.set(x,y);
				}
			
			q--;
			//System.out.println();
			}
		t--;
		System.out.println();
		}
	}
}
class LRUCache {
    
    LinkedHashMap<Integer,Integer> map;
    int size;
    /*Inititalize an LRU cache with size N */
    public LRUCache(int N) {
       map = new LinkedHashMap<Integer,Integer>(N);
       this.size = N;
    }
    
    /*Returns the value of the key x if 
     present else returns -1 */
    public int get(int x) {
       if(map.get(x) != null){
           int value = map.get(x);
           map.remove(x);
           map.put(x,value);
           return value;
       } else
        return -1;
    }
    
    /*Sets the key x with value y in the LRU cache */
    public void set(int x, int y) {
        if(map.get(x) != null){
            map.remove(x);
            map.put(x,y);
            
        } else if(map.size() == this.size){
            Map.Entry<Integer, Integer> first = null;
            first = map.entrySet().iterator().next();
            map.remove(first.getKey());
            map.put(x,y);
        } else {
           map.put(x,y);
        }
    }
}
