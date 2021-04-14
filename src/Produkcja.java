//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Produkcja extends JFrame implements MouseMotionListener {
    // wartości liczbowe scrollbarów kolejno wydajność i rpm wentylatorów:
    double sb1,sb2;

    JLabel etykieta1,etykieta2,etykieta3,etykieta4;
    JLabel wyprodukowano = new JLabel();
    JProgressBar progres = new JProgressBar();
    JProgressBar progres1 = new JProgressBar();
    JProgressBar progres2 = new JProgressBar();
    JScrollBar scrollBar = new JScrollBar(Adjustable.VERTICAL,50,10,0,110);
    JScrollBar scrollBar2 = new JScrollBar(Adjustable.VERTICAL,50,10,0,2510);
    JButton button1 = new JButton();
    Timer timer = new Timer();
    int czasnieaktywnosci=0;
    double temp = 25;
    int ilosc_materialu = 0;
    Random rand = new Random();

    Produkcja(){

        JLabel background;
        setSize(924,764);
        sb1=0;
        sb2=0;

        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon img= new ImageIcon("images/bg1.jpg");
        background= new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,1544,766);
        add(background);
        setVisible(true);

        etykieta1=new JLabel("<html>Wydajność</br> (Moc silnika) </html>");
        etykieta2=new JLabel("<html>Prędkość obrotów </br>  wentylatora [rpm]</html>");
        etykieta3=new JLabel("Temperatura");
        etykieta4=new JLabel();
        etykieta1.setBounds(100,0,100,50);
        etykieta2.setBounds(230,0,150,50);
        etykieta3.setBounds(400,0,150,50);
        etykieta4.setBounds(500,300,200,90);
        etykieta1.setForeground(new Color(244,111,77));
        etykieta2.setForeground(new Color(244,111,77));
        etykieta3.setForeground(new Color(244,111,77));
        etykieta4.setForeground(new Color(244, 77, 77));
        etykieta4.setFont(new Font("Calibri",Font.BOLD,24));
        background.add(etykieta1);
        background.add(etykieta2);
        background.add(etykieta3);
        background.add(etykieta4);

        wyprodukowano.setBounds(700,0,150,80);
        wyprodukowano.setForeground(new Color(77, 244, 191));
        background.add(wyprodukowano);

        button1.setBounds(500,450,300,50);
        button1.setText("Ponów działanie");
        button1.setFont(new Font("Calibri",Font.BOLD,18));
        button1.setVisible(false);
        background.add(button1);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollBar.setEnabled(true);
                scrollBar2.setEnabled(true);
            }
        });


        progres.setSize(60,150);
        progres.setLocation(100,80);
        progres.setMaximum(100);
        progres.setForeground(Color.getHSBColor(76,117,230));
        progres.setStringPainted(true);
        progres.setOrientation(SwingConstants.VERTICAL);

        progres1.setSize(60,150);
        progres1.setLocation(250,80);
        progres1.setMaximum(2500);
        progres1.setForeground(Color.getHSBColor(76,117,230));
        progres1.setStringPainted(true);
        progres1.setOrientation(SwingConstants.VERTICAL);


        progres2.setSize(60,150);
        progres2.setLocation(400,80);
        progres2.setMaximum(100);
        progres2.setForeground(Color.getHSBColor(76,117,230));
        progres2.setStringPainted(true);
        progres2.setOrientation(SwingConstants.VERTICAL);


        scrollBar.setSize(40,150);
        scrollBar.setLocation(110,250);

        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                progres.setValue(Math.abs(100-scrollBar.getValue()));
                sb1=progres.getValue();
                czasnieaktywnosci=0;

            }
        });
        scrollBar2.setSize(40,150);
        scrollBar2.setLocation(257,250);

        scrollBar2.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                progres1.setValue(Math.abs(2500-scrollBar2.getValue()));
                sb2=progres1.getValue();
                czasnieaktywnosci=0;

            }
        });

        timer.schedule(timerTask,0,1000);

        background.add(scrollBar);
        background.add(scrollBar2);
        background.add(progres);
        background.add(progres1);
        background.add(progres2);
        addMouseMotionListener(this);

    }




    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public  void mouseMoved(MouseEvent e){
        czasnieaktywnosci=0;

    }


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            String s,s1;
           temp =temp + sprawdzenie();
           if(temp<25){
               temp=25;
           }
           if(temp>90){
               temp=90;
               sb1=1;
               progres.setValue(0);
               scrollBar.setVisibleAmount(0);
               JOptionPane.showMessageDialog(null,"Temperatura została przekroczona, wydajność produkcji spadła do 0");

           }

            s1= String.valueOf((int)sb2);
           progres1.setString(s1);
           progres2.setValue((int)temp);
           s= String.valueOf((int)temp);
           progres2.setString(s+"C");
           czasnieaktywnosci++;
            if(czasnieaktywnosci>15){

                etykieta4.setText("<html>Brak aktywnośći!!!<br/>Za chwile nastąpi wylogowanie</html>");
                if(czasnieaktywnosci>20){
                    czasnieaktywnosci=0;
                    new OknoLogowania();
                    dispose();
                    timer.cancel();
                }
             }
            if(czasnieaktywnosci<15 && scrollBar.isEnabled())
                etykieta4.setText(null);

            //Ilość wyprodukowanego materiału
            ilosc_materialu = (int) (ilosc_materialu+(sb1/100) *1000);
            wyprodukowano.setText("<html>Wyprodukowano<br/>"+ilosc_materialu+"kg "+ "<br/>Materiału</html>");

            //Prawdopodobieństwo Awarii
            int r = rand.nextInt((int) sb2+1);
            if(r>2201 || (r<1200 && r>1160)|| (r<1600 && r>1520)){
                dzwieki();
                etykieta4.setText("<html>AWARIA!!!!!<br/>Należy wezwać obsługe</html>");
                progres.setValue(0);
                progres1.setValue(0);
                sb1=0;
                sb2=0;
                scrollBar.setEnabled(false);
                scrollBar2.setEnabled(false);
                button1.setVisible(true);
            }



        }
    };//TimerTask

    double  sprawdzenie() {
        double i = 0, j = 0;
        i=sb1/10; //scrollbar wydajności
        j=sb2/250; //scrollbar rpm wentylatora
        return(i-j);
    }



    public void dzwieki(){
        InputStream alarm = null;
        try {
            alarm = new FileInputStream("sounds/alarm.wav");
            AudioStream a1 = new AudioStream(alarm);
            AudioPlayer.player.start(a1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
