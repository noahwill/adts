package impl;

public class AVLBalancer<K extends Comparable<K>,V> implements Balancer<K,V,AVLInfo<K,V>> {

    public BSTMap<K, V, AVLInfo<K, V>>.Node putFixup(BSTMap<K, V, AVLInfo<K, V>>.Node fix) {
        fix.getInfo().recompute();
      
        // Right violated
        if (fix.getInfo().getBalance() == -2) 
        {
        	// Right - Left Violated
        	if (fix.getRight().getInfo().getBalance() > 0) fix.rightRotateRight();
        	return fix.rotateLeft();
        }
        
        // Left violated
        else if (fix.getInfo().getBalance() == 2) 
        {
        	// Left - Right Violated
        	if (fix.getLeft().getInfo().getBalance() < 0) fix.leftRotateLeft();
        	return fix.rotateRight();
    	}
        
        // No violation
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
