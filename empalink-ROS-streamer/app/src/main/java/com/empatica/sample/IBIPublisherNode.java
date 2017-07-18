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

public class IBIPublisherNode extends AbstractNodeMain implements NodeMain {
    static float Data=0;
    static double mainTime = 0;
    private org.ros.message.Time time = new org.ros.message.Time();
    private static final String TAG = IBIPublisherNode.class.getSimpleName();

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("SimplePublisher/IBILoopNode");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<std_msgs.Float64> publisher = connectedNode.newPublisher(GraphName.of("Empalink/IBI"), std_msgs.Float64._TYPE);
        final Publisher<std_msgs.Header> publisher2 = connectedNode.newPublisher(GraphName.of("Empalink/IBI/TimeStamp"), std_msgs.Header._TYPE);

        final CancellableLoop loop = new CancellableLoop() {

            @Override

            protected void loop() throws InterruptedException {
                std_msgs.Header msg = publisher2.newMessage();
                time = new org.ros.message.Time(mainTime);
                msg.setStamp(time);
                publisher2.publish(msg);
                std_msgs.Float64 flt = publisher.newMessage();
                flt.setData(Data);
                publisher.publish(flt);
                // go to sleep for one second
                Thread.sleep(1000);

            }

        };

        connectedNode.executeCancellableLoop(loop);

    }



}