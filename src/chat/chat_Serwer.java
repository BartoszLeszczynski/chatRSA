
package chat;

import java.net.ServerSocket;
import java.net.Socket; 
import java.math.BigInteger;  
import java.security.SecureRandom;
import java.util.Random;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

public class chat_Serwer extends javax.swing.JFrame {
    public static int BIT_LENGTH = 1024;
    
    static ServerSocket ss;
    static Socket s;
    
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    
    public chat_Serwer() throws IOException {
        initComponents();
        
          
    }
    
    public static void calculateAndSend() throws IOException{
        // Generuje losowe liczby pierwsze
          Random rand = new SecureRandom();
          BigInteger p = new BigInteger(BIT_LENGTH / 2, 100, rand);
          BigInteger q = new BigInteger(BIT_LENGTH / 2, 100, rand);

            // Obliczenia składników
          BigInteger n = p.multiply(q);
          BigInteger phi = p.subtract(BigInteger.ONE)
                  .multiply(q.subtract(BigInteger.ONE));

            // Generuje klucze
          BigInteger e;
          do e = new BigInteger(phi.bitLength(), rand);
          while (e.compareTo(BigInteger.ONE) <= 0
                  || e.compareTo(phi) >= 0
                  || !e.gcd(phi).equals(BigInteger.ONE));
          BigInteger d = e.modInverse(phi);
          
          ss = new ServerSocket(8080); //ustala port
          s = ss.accept(); 
          oos = new ObjectOutputStream(s.getOutputStream());
          
        System.out.println("Przed wysłaniem ");
           
            //wysłanie
            Message mes = new Message();
            mes.d = d;
            mes.n = n;
            mes.e = e;
            oos.writeObject(mes);
            oos.flush();
            System.out.println("Po wysłaniu d: " + d);
    }
  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msg_text = new javax.swing.JTextField();
        msg_send = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        msg_text.setText("jTextField1");
        msg_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_textActionPerformed(evt);
            }
        });

        msg_send.setText("Wyślij");
        msg_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_sendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(msg_send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(msg_text)
                    .addComponent(msg_send, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_textActionPerformed

    }//GEN-LAST:event_msg_textActionPerformed

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_sendActionPerformed
        try{
            
            
            //odczytanie
            
            Message mess = (Message) ois.readObject();
            BigInteger d = mess.d;
            BigInteger n = mess.n;
            BigInteger e = mess.e;
            System.out.println("d: "+d);
            System.out.println("n: "+n);
            System.out.println("e: "+e);
            
            String msgout = "";
            msgout = msg_text.getText().trim();
            
            // konwersja String do BigInteger
            String txt = msgout;
            byte[] msBytes = txt.getBytes();
            BigInteger msg = new BigInteger(msBytes);
            System.out.println(msg);
            
            // Wiadomość szyfrowanie
            BigInteger enc = msg.modPow(e, n);
            
            // wysłanie
            
            Message mes = new Message();
            mes.enc = enc;
            oos.writeObject(mes);
            oos.flush();

            }catch(IOException ex){
                    System.out.println("w przycisku: "+ex.getMessage());
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(chat_Serwer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_msg_sendActionPerformed

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chat_Serwer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new chat_Serwer().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(chat_Serwer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
          
      
      
        
        try{
            
            calculateAndSend();
            ois = new ObjectInputStream(s.getInputStream());
            
            //odebranie
            Message mess = (Message) ois.readObject();
            BigInteger d = mess.d;
            BigInteger n = mess.n;
            BigInteger enc = mess.enc;
            if(enc != null){
             //deszyfrowanie
             BigInteger dec = enc.modPow(d, n);
             String wejscie = new String(dec.toByteArray());

             msg_area.setText(msg_area.getText().trim()+"\n Serwer: "+wejscie); //wyświetla wiadomość od serwera
            }
             

        }catch( IOException z){
            System.out.println("w main: "+z);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(chat_Serwer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea msg_area;
    private javax.swing.JButton msg_send;
    private javax.swing.JTextField msg_text;
    // End of variables declaration//GEN-END:variables
}
