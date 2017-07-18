package com.empatica.sample;
/**
 * Created by paulg on 7/13/2017.
 */
import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.rosjava_geometry.Vector3;

import sensor_msgs.BatteryState;

public class BatteryPublisherNode extends AbstractNodeMain implements NodeMain {
    public static float Data= 0;
    public static double mainTime;
    private org.ros.message.Time time = new org.ros.message.Time();
    private static final String TAG = BatteryPublisherNode.class.getSimpleName();

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("SimplePublisher/BatteryLoopNode");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<sensor_msgs.BatteryState> publisher = connectedNode.newPublisher(GraphName.of("Empalink/battery"), sensor_msgs.BatteryState._TYPE);

        final CancellableLoop loop = new CancellableLoop() {

            @Override

            protected void loop() throws InterruptedException {

                BatteryState msg = publisher.newMessage();
                msg.setCharge(Data);
                publisher.publish(msg);
                time = new org.ros.message.Time(mainTime);
                msg.getHeader().setStamp(time);
                publisher.publish(msg);
                // go to sleep for 4:40 min
                Thread.sleep(140000);

            }

        };

        connectedNode.executeCancellableLoop(loop);

    }



}