package com.testing4everyone;

import org.jpos.iso.*;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;

public class MockIso8583Service implements ISORequestListener {

    public static void main(String[] args) throws ISOException {
        String hostName = "localhost";
        int portNumber = 5000;
        ISOPackager packager = new GenericPackager("CustomerConfig.xml");
        ServerChannel channel = new ASCIIChannel(hostName, portNumber, packager);

    }

    @Override
    public boolean process(ISOSource isoSource, ISOMsg isoMsg) {

        try{
            System.out.println("ISO8583 Incoming message on Host: ");
        }catch (Exception e){

        }
        return false;
    }
}
