package com.empatica.sample;
/**
 * Created by paulg on 7/13/2017.
 */
import android.util.Log;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.rosjava_geometry.Vector3;

import geometry_msgs.Accel;
import geometry_msgs.Twist;
import sensor_msgs.Imu;

public class AccelerationPublisherNode extends AbstractNodeMain implements NodeMain {
    public static int X;
    public static int Y;
    public static int Z;
    public static double mainTime;
    private org.ros.message.Time time = new org.ros.message.Time();
    private static final String TAG = AccelerationPublisherNode.class.getSimpleName();
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("SimplePublisher/AccelerationLoopNode");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        //final Publisher<Twist> publisher = connectedNode.newPublisher(GraphName.of("test"), geometry_msgs.Twist._TYPE);
        final Publisher<sensor_msgs.Imu> publisher = connectedNode.newPublisher("Empalink/acceleration", sensor_msgs.Imu._TYPE);
            X=0;
            Y=0;
            Z=0;
        final CancellableLoop loop = new CancellableLoop() {

            @Override

            protected void loop() throws InterruptedException {
                Imu msg = publisher.newMessage();
                geometry_msgs.Vector3 linear = msg.getLinearAcceleration();
                linear.setX(X);
                linear.setY(Y);
                linear.setZ(Z);
                time = new org.ros.message.Time(mainTime);
                msg.getHeader().setStamp(time);
                publisher.publish(msg);
                // go to sleep for 1/10 second
                Thread.sleep(100);

            }

        };

        connectedNode.executeCancellableLoop(loop);

    }



}