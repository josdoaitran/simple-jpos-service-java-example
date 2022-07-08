package com.testing4everyone;

import org.jpos.iso.*;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.XMLPackager;

import java.io.IOException;

public class MockISO8583Service implements ISORequestListener {

    public static void main(String[] args) throws ISOException {
        String hostName = "localhost";
        int portNumber = 5001;
//        ISOPackager packager = new GenericPackager("CustomConfig.xml");
        XMLPackager packager = new XMLPackager();
        ServerChannel channel = new ASCIIChannel(hostName, portNumber, packager);
        ISOServer server = new ISOServer(portNumber, channel, null);
        server.addISORequestListener(new MockISO8583Service());
        System.out.println("Let's Start !!!!! ISO8583 Service");
        new Thread(server).start();
        System.out.println("ISO8583 Service Started successfully");
    }


    public boolean process(ISOSource isoSource, ISOMsg isoMsg) {

        try{
            System.out.println("ISO8583 Incoming message on Host: ["
            + ((BaseChannel) isoSource).getSocket().getInetAddress().getHostAddress() + "]");

            receiveMessage(isoSource, isoMsg);
            logISOMsg(isoMsg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void receiveMessage(ISOSource isoSource, ISOMsg isoMsg) throws ISOException, IOException {
        System.out.println("ISO8583 Service will receive message .....");
        ISOMsg reply = (ISOMsg) isoMsg.clone();
        reply.setMTI("2100");
        System.out.println("ISO8583 Tesst000000000");
        reply.set(32, "00000000001");
        System.out.println("ISO8583 Service will reply by received message .....");
        isoSource.send(reply);
    }

    private static void logISOMsg(ISOMsg msg) {
        System.out.println("----ISO MESSAGE to Pack-----");
        try {
            System.out.println("  MTI : " + msg.getMTI());
            for (int i = 1; i <= msg.getMaxField(); i++) {
                if (msg.hasField(i)) {
                    System.out.println("    Field-" + i + " : " + msg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("--------------------");
        }
    }
}
