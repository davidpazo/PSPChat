package chatpsp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**** @author DAVID */

public class Chat {
    
    private int puerto = 9090;
    private SSLServerSocket ssv;
    private SSLSocket ss;
    private PrintWriter pw;
    private BufferedReader br;

     public static void main(String[] args) {
     try {
            //Le pasamos el fichero generado con la clave de keytool
            KeyStore ks = KeyStore.getInstance("JKS");
            //ks.load(new FileInputStream("/datos/local/dpazolopez/NetBeansProjects/Chat/src/chatpsp/servKeyStore"), "chatpsp".toCharArray());
            ks.load(new FileInputStream("E:\\Netbeans Projects\\Chat2\\src\\chatpsp\\servKeyStore"), "chatpsp".toCharArray());
            //Pasamos la clave para generar el fichero con el algoritmo.
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, "chatpsp".toCharArray());

            //Le da fiabilidad al certificado
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);

            //Creamos ssl context tengo un mismo contexto de ssl en cliente y servidor
            System.out.println("Generando SSL");
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            //Creo el socket server con el contexto ssl
            System.out.println("Socket de servidor creado");
            IServer is = new IServer(sc);
            ICliente ic = new ICliente(sc);
         
            System.out.println("Socket cliente aceptado en el servidor...");
            
        } catch (IOException | CertificateException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException ex) {
            System.out.println(ex);
        }
    }
    
}