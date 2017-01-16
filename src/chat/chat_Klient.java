
package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket; 
import java.math.BigInteger; 
import java.util.logging.Level;
import java.util.logging.Logger;


public class chat_Klient extends javax.swing.JFrame {

    static Socket s;
    
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    
    public chat_Klient() {
        initComponents();
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

        msg_send.setText("Wyślij");
        msg_send.setActionCommand("Wyślij");
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(msg_send, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                    .addComponent(msg_text))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_sendActionPerformed
        try{
            //odczytanie
            
            Message mess =  (Message) ois.readObject();
            BigInteger n = mess.n;
            BigInteger e = mess.e;

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
      
        }catch(IOException | ClassNotFoundException ex){
                System.out.println("w przycisku: "+ex.getMessage());
                ex.printStackTrace();
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
            java.util.logging.Logger.getLogger(chat_Klient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chat_Klient().setVisible(true);
            }
        });
        
        
        try{
            
            s = new Socket("127.0.0.1", 8080); //localne ip + port
            
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());

            //odczytanie
            
            Message mess = (Message) ois.readObject();
            BigInteger d = mess.d;
            BigInteger n = mess.n;
            BigInteger enc = mess.enc;
            System.out.println("Po otrzymaniu d: " + d);
            
            if(enc != null){
             //deszyfrowanie
             BigInteger dec = enc.modPow(d, n);
             String wejscie = new String(dec.toByteArray());

             msg_area.setText(msg_area.getText().trim()+"\n Serwer: "+wejscie); 
            }
        }catch( IOException ex){
            System.out.println("w main: "+ex);
            ex.printStackTrace();
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
