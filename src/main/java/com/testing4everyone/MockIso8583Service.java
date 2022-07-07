package com.testing4everyone;

import org.jpos.iso.*;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;

import java.io.IOException;

public class MockIso8583Service implements ISORequestListener {

    public static void main(String[] args) throws ISOException {
        String hostName = "localhost";
        int portNumber = 5000;
        ISOPackager packager = new GenericPackager("CustomerConfig.xml");
        ServerChannel channel = new ASCIIChannel(hostName, portNumber, packager);
        ISOServer server = new ISOServer(portNumber, channel, null);
        server.addISORequestListener(new MockIso8583Service());
        System.out.println("Let's Start !!!!! ISO8583 Service");
        new Thread(server).start();
        System.out.println("ISO8583 Service Started successfully");
    }

    @Override
    public boolean process(ISOSource isoSource, ISOMsg isoMsg) {

        try{
            System.out.println("ISO8583 Incoming message on Host: ["
            + ((BaseChannel) isoSource).getSocket().getInetAddress().getHostAddress() + "]");
            receiveMessage(isoSource, isoMsg);

        }catch (Exception e){

        }
        return false;
    }

    private void receiveMessage(ISOSource isoSource, ISOMsg isoMsg) throws ISOException, IOException {
        System.out.println("ISO8583 Service will receive message .....");
        ISOMsg reply = (ISOMsg)isoMsg.clone();
        reply.setMTI("0210");
        reply.set(39, "00");
        System.out.println("ISO8583 Service will reply by received message .....");
        isoSource.send(reply);
    }
}
