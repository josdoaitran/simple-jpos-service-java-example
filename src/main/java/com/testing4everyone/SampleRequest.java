package com.testing4everyone;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;

import java.io.IOException;

public class SampleRequest {
    public static void main(String[] args) throws IOException, ISOException {
        // Create ISO Mesage
        ISOPackager packager = new GenericPackager("CustomConfig.xml");

        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);

        isoMsg.setMTI("0200");
        isoMsg.set(3, "201234");
        isoMsg.set(4, "10000");
        isoMsg.set(7, "110722180");
        isoMsg.set(11, "123456");
        isoMsg.set(32, "100005");
        isoMsg.set(44, "A5DFGR");
        isoMsg.set(105, "ABCDEFGHIJ 1234567890");

        String hostName = "127.0.0.1";
        int portNumber = 5000;

        ASCIIChannel channel = new ASCIIChannel(hostName, portNumber, packager);
        logISOMsg(isoMsg);
        System.out.println(ISOUtil.hexdump(isoMsg.pack()));

        channel.connect();
        channel.send(isoMsg);

        ISOMsg response = channel.receive();
        System.out.println("********** Response***********");
        logISOMsg(response);

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
