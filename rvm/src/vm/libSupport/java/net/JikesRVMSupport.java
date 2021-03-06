/*
 * (C) Copyright IBM Corp 2002
 */
//$Id: JikesRVMSupport.java,v 1.6 2004/12/17 18:34:57 augart-oss Exp $
package java.net;
import com.ibm.JikesRVM.VM_SizeConstants;

/**
 * Library support interface of Jikes RVM
 *
 * @author Julian Dolby
 */
public class JikesRVMSupport implements VM_SizeConstants {

  private static byte[] toArrayForm(int address) {
    byte[] addr = new byte[4];
    addr[0] = (byte)((address>>(3*BITS_IN_BYTE)) & 0xff);
    addr[1] = (byte)((address>>(2*BITS_IN_BYTE)) & 0xff);
    addr[2] = (byte)((address>>BITS_IN_BYTE) & 0xff);
    addr[3] = (byte)(address & 0xff);
    return addr;
  }

  public static InetAddress createInetAddress(int address) {
    return createInetAddress(address, null);
  }
    
  public static InetAddress createInetAddress(int address, String hostname) {
    //-#if RVM_WITH_CLASSPATH_0_10 || RVM_WITH_CLASSPATH_0_11
    return new InetAddress(toArrayForm(address), hostname, null);
    //-#else
    return new InetAddress(toArrayForm(address), hostname);
    //-#endif
  }
    
  public static int getFamily(InetAddress inetaddress) {
    return inetaddress.family;
  }
    
  public static void setHostName(InetAddress inetaddress, String hostname) {
    inetaddress.hostName = hostname;
  }
}
