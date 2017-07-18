package com.empatica.sample;

/**
 * Created by paulg on 7/13/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import org.ros.address.InetAddressFactory;

import org.ros.android.RosActivity;

import org.ros.node.NodeConfiguration;

import org.ros.node.NodeMain;

import org.ros.node.NodeMainExecutor;



public class initROS extends RosActivity {



    public initROS() {
        super("RosAndroid", "RosAndroid");

    }



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }



    @Override

    public void init(NodeMainExecutor nodeMainExecutor) {
        //NodeMain node = new AccelerationPublisherNode();
        NodeMain node1 = new AccelerationPublisherNode();
        NodeMain node2 = new BatteryPublisherNode();
        NodeMain node3 = new BVPPublisherNode();
        NodeMain node4 = new IBIPublisherNode();
        NodeMain node5 = new TemperaturePublisherNode();
        NodeMain node6 = new EDAPublisherNode();

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress());

        nodeConfiguration.setMasterUri(getMasterUri());

        nodeMainExecutor.execute(node1, nodeConfiguration);
        nodeMainExecutor.execute(node2, nodeConfiguration);
        nodeMainExecutor.execute(node3, nodeConfiguration);
        nodeMainExecutor.execute(node4, nodeConfiguration);
        nodeMainExecutor.execute(node5, nodeConfiguration);
        nodeMainExecutor.execute(node6, nodeConfiguration);
        Intent myIntent = new Intent(initROS.this, MainActivity.class);
        myIntent.putExtra("key", 33); //Optional parameters
        initROS.this.startActivity(myIntent);
    }

}