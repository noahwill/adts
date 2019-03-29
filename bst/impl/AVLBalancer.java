package impl;

public class AVLBalancer<K extends Comparable<K>,V> implements Balancer<K,V,AVLInfo<K,V>> {

    public BSTMap<K, V, AVLInfo<K, V>>.Node putFixup(BSTMap<K, V, AVLInfo<K, V>>.Node fix) {
        fix.getInfo().recompute();
      
        // Right violated
        if (fix.getInfo().getBalance() < -1) {
        	// Right - Left Violated
        	if (fix.getLeft().getInfo().getBalance() > 1) {
        		fix.getLeft().rotateRight();
        	}
        	
        	fix.rotateLeft();
        }
        
        // Left violated
        if (fix.getInfo().getBalance() > 1) {
        	// Left - Right Violated
        	if (fix.getRight().getInfo().getBalance() < -1) {
        		fix.getRight().rotateRight();
        	}
        	
        	fix.rotateRight();
    	}
        
        
        return fix;
    }

    public BSTMap<K, V, AVLInfo<K, V>>.Node removeFixup(BSTMap<K, V, AVLInfo<K, V>>.Node fix) {
        return putFixup(fix);
    }

    public AVLInfo<K, V> newInfo(BSTMap<K, V, AVLInfo<K, V>>.Node node) {
        return new AVLInfo<K,V>(0, 0, node);
    }
    public AVLInfo<K, V> nullInfo(BSTMap<K, V, AVLInfo<K, V>>.Node node) {
        return newInfo(node);
    }

    public void rootFixup(BSTMap<K, V, AVLInfo<K, V>>.Node fix) { }

}
