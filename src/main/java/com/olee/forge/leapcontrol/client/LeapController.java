package com.olee.forge.leapcontrol.client;

import org.apache.logging.log4j.LogManager;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.*;

public class LeapController extends Listener {

	public short xdir;
	public short zdir;
	
	private boolean active;
	private boolean stoppedActivity;
	
	private Controller controller;
	
	public LeapController() {
		controller = new Controller();
		controller.addListener(this);
	}

	public void onInit(Controller controller) {
		LogManager.getLogger().info("LeapMotion controller initialized");
	}

	public void onConnect(Controller controller) {
		LogManager.getLogger().info("LeapMotion controller connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        //controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        //controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}

	public void onDisconnect(Controller controller) {
		LogManager.getLogger().info("LeapMotion controller disconnected");
	}

	public void onExit(Controller controller) {
		LogManager.getLogger().info("LeapMotion controller exited");
	}

	public void onFrame(Controller controller) {
        Frame frame = controller.frame();

        boolean activity = false;
        xdir = 0;
        zdir = 0;

        for(Gesture gesture : frame.gestures()) {
        	if (gesture.type() == Type.TYPE_CIRCLE) {
                CircleGesture circle = new CircleGesture(gesture);

                double angleSide = circle.normal().angleTo(new Vector(0, 0, 1)) / Math.PI * 180;
                double angleFront = circle.normal().angleTo(new Vector(1, 0, 0)) / Math.PI * 180;

                activity = true;
        		if (angleSide < 60)
        			xdir -= 1;
        		if (angleSide > 120)
        			xdir += 1;
        		if (angleFront < 60)
        			zdir -= 1;
        		if (angleFront > 120)
        			zdir += 1;

//        		LogManager.getLogger().info("Angle z = " + Math.round(angleFront) + 
//        				" / x = " + Math.round(angleSide) + 
//        				" / zdir = " + zdir + " / xdir = " + xdir);
        	}
        }
        
        if (!activity && active) {
        	stoppedActivity = true;
        }
        active = activity;
        
        
//        LogManager.getLogger().info("Frame id: " + frame.id()
//                         + ", timestamp: " + frame.timestamp()
//                         + ", hands: " + frame.hands().count()
//                         + ", fingers: " + frame.fingers().count()
//                         + ", tools: " + frame.tools().count()
//                         + ", gestures " + frame.gestures().count());
//
//        //Get hands
//        for(Hand hand : frame.hands()) {
//            String handType = hand.isLeft() ? "Left hand" : "Right hand";
//            LogManager.getLogger().info("  " + handType + ", id: " + hand.id()
//                             + ", palm position: " + hand.palmPosition());
//
//            // Get the hand's normal vector and direction
//            Vector normal = hand.palmNormal();
//            Vector direction = hand.direction();
//
//            // Calculate the hand's pitch, roll, and yaw angles
//            LogManager.getLogger().info("  pitch: " + Math.toDegrees(direction.pitch()) + " degrees, "
//                             + "roll: " + Math.toDegrees(normal.roll()) + " degrees, "
//                             + "yaw: " + Math.toDegrees(direction.yaw()) + " degrees");
//
//            // Get arm bone
//            Arm arm = hand.arm();
//            LogManager.getLogger().info("  Arm direction: " + arm.direction()
//                             + ", wrist position: " + arm.wristPosition()
//                             + ", elbow position: " + arm.elbowPosition());
//
//            // Get fingers
//            for (Finger finger : hand.fingers()) {
//                LogManager.getLogger().info("    " + finger.type() + ", id: " + finger.id()
//                                 + ", length: " + finger.length()
//                                 + "mm, width: " + finger.width() + "mm");
//
//                //Get Bones
//                for(Bone.Type boneType : Bone.Type.values()) {
//                    Bone bone = finger.bone(boneType);
//                    LogManager.getLogger().info("      " + bone.type()
//                                     + " bone, start: " + bone.prevJoint()
//                                     + ", end: " + bone.nextJoint()
//                                     + ", direction: " + bone.direction());
//                }
//            }
//        }
//
//        // Get tools
//        for(Tool tool : frame.tools()) {
//            LogManager.getLogger().info("  Tool id: " + tool.id()
//                             + ", position: " + tool.tipPosition()
//                             + ", direction: " + tool.direction());
//        }
//
//        GestureList gestures = frame.gestures();
//        for (int i = 0; i < gestures.count(); i++) {
//            Gesture gesture = gestures.get(i);
//
//            switch (gesture.type()) {
//                case TYPE_CIRCLE:
//                    CircleGesture circle = new CircleGesture(gesture);
//
//                    // Calculate clock direction using the angle between circle normal and pointable
//                    String clockwiseness;
//                    if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2) {
//                        // Clockwise if angle is less than 90 degrees
//                        clockwiseness = "clockwise";
//                    } else {
//                        clockwiseness = "counterclockwise";
//                    }
//
//                    // Calculate angle swept since last frame
//                    double sweptAngle = 0;
//                    if (circle.state() != State.STATE_START) {
//                        CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
//                        sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
//                    }
//
//                    LogManager.getLogger().info("  Circle id: " + circle.id()
//                               + ", " + circle.state()
//                               + ", progress: " + circle.progress()
//                               + ", radius: " + circle.radius()
//                               + ", angle: " + Math.toDegrees(sweptAngle)
//                               + ", " + clockwiseness);
//                    break;
//                case TYPE_SWIPE:
//                    SwipeGesture swipe = new SwipeGesture(gesture);
//                    LogManager.getLogger().info("  Swipe id: " + swipe.id()
//                               + ", " + swipe.state()
//                               + ", position: " + swipe.position()
//                               + ", direction: " + swipe.direction()
//                               + ", speed: " + swipe.speed());
//                    break;
//                case TYPE_SCREEN_TAP:
//                    ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
//                    LogManager.getLogger().info("  Screen Tap id: " + screenTap.id()
//                               + ", " + screenTap.state()
//                               + ", position: " + screenTap.position()
//                               + ", direction: " + screenTap.direction());
//                    break;
//                case TYPE_KEY_TAP:
//                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
//                    LogManager.getLogger().info("  Key Tap id: " + keyTap.id()
//                               + ", " + keyTap.state()
//                               + ", position: " + keyTap.position()
//                               + ", direction: " + keyTap.direction());
//                    break;
//                default:
//                    LogManager.getLogger().info("Unknown gesture type.");
//                    break;
//            }
//        }
	}

	public boolean isStoppedActivity() {
		return stoppedActivity;
	}

	public void resetStoppedActivity() {
		stoppedActivity = false;
	}

	public boolean isActive() {
		return active;
	}
	
}
