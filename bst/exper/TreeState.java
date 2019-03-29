package exper;

import java.text.DecimalFormat;
import java.util.Random;

import adt.Map;
import impl.AVLBalancer;
import impl.AVLInfo;
import impl.BSTMap;
import impl.Balancer;
import impl.LLRBBalancer;
import impl.NodeInfo;
import impl.RBInfo;
import impl.TradRBBalancer;

public class TreeState {
    public static void main(String[] args) {
        int optHeight = Integer.parseInt(args[0]);
        int nodesAtDepth = 1;
        int totalSize = 0;
        int optTotalDepth = 0;
        for (int i = 1; i <= optHeight; i++) {
               totalSize += nodesAtDepth;
               optTotalDepth += nodesAtDepth * i; 
               nodesAtDepth <<= 1;
        }

        System.out.println("n = " + totalSize + ".");
        System.out.println("\t\tHeight \tLeaves(%) \t\t\t Total depth");
        System.out.println("Perfect: \t"
                    + optHeight + "\t"
                    + ((totalSize + 1) / 2) + " (" + 
                    (((double) ((totalSize + 1) / 2))/totalSize) + ")\t"
                    + optTotalDepth);
                
        int maxKey = totalSize * 3;
        Random randy = new Random();
        DecimalFormat lp = new DecimalFormat("#.0%");
        
        // five repetitions
        String uRes = "", avlRes = "", trbRes = "", llrbRes = "";
        for (int i = 0; i < 5; i++) {
            BSTMap<Integer, Integer,DummyNodeInfo> unbalanced = new BSTMap<Integer, Integer,DummyNodeInfo>(dummyBalancer);
            BSTMap<Integer, Integer, AVLInfo<Integer,Integer>>avl = new BSTMap<Integer, Integer, AVLInfo<Integer,Integer>>(new AVLBalancer<Integer,Integer>());
            BSTMap<Integer, Integer, RBInfo<Integer,Integer>>trb = new BSTMap<Integer, Integer, RBInfo<Integer,Integer>> (new TradRBBalancer<Integer,Integer>());
            BSTMap<Integer, Integer, RBInfo<Integer,Integer>> llrb = new BSTMap<Integer, Integer, RBInfo<Integer,Integer>> (new LLRBBalancer<Integer,Integer>());
       
            boolean[] used = new boolean[maxKey];
           for (int j = 0; j < totalSize; ) {
                int key = randy.nextInt(maxKey);
                if (! used[key]) {
                    unbalanced.put(key,  0);
                    avl.put(key, 0);
                    trb.put(key, 0);
                    llrb.put(key, 0);
                    used[key] = true;
                    j++;
                }
           }
            
           uRes += unbalanced.height() + " & " + lp.format((double) unbalanced.leaves()/totalSize) 
               + " & " + unbalanced.totalDepth()+ " & ";
           avlRes += avl.height() + " & " + lp.format((double) avl.leaves()/totalSize) 
           + " & " + avl.totalDepth()+ " & ";
           trbRes += trb.height() + " & " + lp.format((double) trb.leaves()/totalSize) 
           + " & " + trb.totalDepth()+ " & ";
           llrbRes += llrb.height() + " & " + lp.format((double) llrb.leaves()/totalSize) 
           + " & " + llrb.totalDepth()+ " & ";
           
            
           boolean[] deleted = new boolean[maxKey];
           for (int j = 0; j < totalSize/2; ) {
               int key = randy.nextInt(maxKey);
               if (used[key] && ! deleted[key]) {
                   unbalanced.remove(key);
                   avl.remove(key);
                   trb.remove(key);
                   llrb.remove(key);
                   deleted[key] = true;
                   j++;
               }
          }
           
           uRes += unbalanced.height() + " & " + lp.format((double) unbalanced.leaves()/totalSize) 
           + " & " + unbalanced.totalDepth() + "\\\\\n";
       avlRes += avl.height() + " & " + lp.format((double) avl.leaves()/totalSize) 
       + " & " + avl.totalDepth()+ "\\\\\n";;
       trbRes += trb.height() + " & " + lp.format((double) trb.leaves()/totalSize) 
       + " & " + trb.totalDepth()+ "\\\\\n";;
       llrbRes += llrb.height() + " & " + lp.format((double) llrb.leaves()/totalSize) 
       + " & " + llrb.totalDepth()+ "\\\\\n";;
          
           
        }
        
        System.out.println("unbalanced");
        System.out.println(uRes);
        System.out.println("avl");
        System.out.println(avlRes);
        System.out.println("trb");
        System.out.println(trbRes);
        System.out.println("llrb");
        System.out.println(llrbRes);
    }
    
    private static class DummyNodeInfo implements NodeInfo { public void recompute() { } }
    private static DummyNodeInfo dummy = new DummyNodeInfo();
    private static Balancer<Integer, Integer, DummyNodeInfo> dummyBalancer = new Balancer<Integer,Integer,DummyNodeInfo>() {
        public BSTMap<Integer,Integer,DummyNodeInfo>.Node putFixup(BSTMap<Integer,Integer,DummyNodeInfo>.Node fix) { return fix; }
        public BSTMap<Integer,Integer,DummyNodeInfo>.Node removeFixup(BSTMap<Integer,Integer,DummyNodeInfo>.Node fix) { return fix; }
        public DummyNodeInfo newInfo(BSTMap<Integer,Integer,DummyNodeInfo>.Node node) {
            return dummy;
        }
        public DummyNodeInfo nullInfo(BSTMap<Integer, Integer, DummyNodeInfo>.Node node) {
            return dummy;
        }
        public void rootFixup(BSTMap<Integer, Integer, DummyNodeInfo>.Node fix) {}
   };
}
