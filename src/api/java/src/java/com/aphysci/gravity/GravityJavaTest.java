
package com.aphysci.gravity;

import com.aphysci.gravity.protobuf.GravityDataProduct;
import com.aphysci.gravity.protobuf.GravityDataProduct.GravityDataProductPB;
import com.aphysci.gravity.swig.*;


public class GravityJavaTest {

    public static void main(String[] argv) throws InterruptedException {
        System.out.println("in main");

        GravityNode node = new GravityNode();
        GravityReturnCode ret = node.init();
        assert(ret == GravityReturnCode.SUCCESS);
        
        ret = node.registerDataProduct("JavaGDP", 5678, "tcp");
        assert(ret == GravityReturnCode.SUCCESS);
        
        Subscriber s = new Subscriber();
        ret = node.subscribe("JavaGDP", s, "");
  
        GravityDataProductPB.Builder gdp = GravityDataProductPB.newBuilder();
        gdp.setSoftwareVersion("version 1");
        gdp.setDataProductID("JavaGDP");
        
        ret = node.publish(gdp.build());
        
        ret = node.unregisterDataProduct("JavaGDP");
        assert(ret == GravityReturnCode.SUCCESS);

        ret = node.unregisterDataProduct("JavaGDP");
        assert(ret == GravityReturnCode.REGISTRATION_CONFLICT);
        
//        Thread.sleep(10000);
    }
    
    private static class Subscriber implements GravitySubscriber {

		@Override
		public void subscriptionFilled(GravityDataProduct dataProduct) {
			System.out.println("made it to java callback");
			
		}
    	
    }
}
