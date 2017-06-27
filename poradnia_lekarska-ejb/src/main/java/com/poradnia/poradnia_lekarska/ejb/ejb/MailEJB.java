/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poradnia.poradnia_lekarska.ejb.ejb;

import com.poradnia.poradnia_lekarska.ejb.model.Patient;
import com.poradnia.poradnia_lekarska.ejb.model.Visit;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

/**
 *
 * @author Łukasz
 */
@Singleton
public class MailEJB {

    private static Logger logger = Logger.getLogger(MailEJB.class.getName());
    
    @EJB
    private PatientDao dao;
            
    @Resource(name = "java/mail")
    private Session session;
    
    SimpleDateFormat ft = new SimpleDateFormat("E dd.MM.yyyy");
    SimpleDateFormat tt = new SimpleDateFormat("hh:mm");
    
    @Schedule(hour = "16",minute="30")
    private void sendMailToPatient() throws AddressException, MessagingException{
        logger.info("Sending mails to patients.");
        List<Visit> visits = dao.getCloseVisits();
        System.out.println(visits.toString());
        
        for(Visit visit : visits){
            if(visit.getIdPatient().getMail()!=null){
                MimeMessage mail = new MimeMessage(session);
                mail.setFrom(new InternetAddress("poradnia.lekarska@gmail.com"));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(visit.getIdPatient().getMail()));
                mail.setSubject("Przypomnienie o wizycie.");
                mail.setText("Dzień dobry,\nPrzypominamy o wizycie, którą ma pan/pani "
                        + ft.format(visit.getDate()) + " o godzinie " + tt.format(visit.getTime())
                        + " w pokoju nr " + visit.getRoom() + " w naszej poradni lekarskiej.");
                Transport.send(mail);
            }
        }
        
    }
}
