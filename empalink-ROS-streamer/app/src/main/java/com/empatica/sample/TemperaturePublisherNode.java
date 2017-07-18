package com.empatica.sample;
/**
 * Created by paulg on 7/13/2017.
 */
import org.apache.commons.httpclient.Header;
import org.apache.commons.net.ntp.TimeStamp;
import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.rosjava_geometry.Vector3;

import sensor_msgs.Temperature;
import std_msgs.Time;

public class TemperaturePublisherNode extends AbstractNodeMain implements NodeMain {
    public static float Data = 0;
    public static double mainTime;
    private org.ros.message.Time time = new org.ros.message.Time();
    private static final String TAG = TemperaturePublisherNode.class.getSimpleName();
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("SimplePublisher/TemperatureLoopNode");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<sensor_msgs.Temperature> publisher = connectedNode.newPublisher(GraphName.of("Empalink/temperature"), sensor_msgs.Temperature._TYPE);
        final CancellableLoop loop = new CancellableLoop() {

            @Override
            protected void loop() throws InterruptedException {
                Temperature msg = publisher.newMessage();
                msg.setTemperature(Data);
                time = new org.ros.message.Time(mainTime);
                msg.getHeader().setStamp(time);
                publisher.publish(msg);
                // go to sleep for one second
                Thread.sleep(1000);
            }

        };

        connectedNode.executeCancellableLoop(loop);

    }



}