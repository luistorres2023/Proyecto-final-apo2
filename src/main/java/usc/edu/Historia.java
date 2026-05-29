package usc.edu;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class Historia extends JFrame {

    public Historia() {

        setTitle("Historia");
        setSize(1200,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel fondo = new JPanel() {

            Image bg = new ImageIcon(
                    getClass().getResource("/assets/ui/historia_bg.png"))
                    .getImage();

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                g.drawImage(bg,0,0,getWidth(),getHeight(),this);
            }
        };

        fondo.setLayout(new BorderLayout());

        JTextPane historia = new JTextPane();

        historia.setText(
                "LAST STAND TOWERS\n\n"

                + "Un pequeño pueblo que vivía en paz y prosperidad, dedicado a la agricultura y la\n"
                + "ganadería. Tenían una vida tranquila y relajada, pero de repente fueron atacados\n"
                + "por seres mitológicos, los cuales empezaron a saquear y a destruir su pueblo.\n\n"

                + "En su desesperación, se vieron obligados a contratar mercenarios para\n"
                + "defenderse, pero lo que no sabían era que dichos mercenarios estaban aliados con\n"
                + "los seres mitológicos. El pueblo fue saqueado y masacrado; los mercenarios\n"
                + "tomaron como esclavos a los habitantes fuertes y se olvidaron de los débiles.\n\n"

                + "Los sobrevivientes, llamados Luis, Steven, Santiago y Nicholas, se encontraban\n"
                + "en la miseria y, cuando pensaban que todo estaba perdido, encontraron un pueblo\n"
                + "próspero y rico. Fueron recibidos, y las personas del pueblo les enseñaron sus\n"
                + "creencias y tradiciones.\n\n"

                + "En ese pueblo existía la Guardia Real, encargada de protegerlo. Esto fue lo que le\n"
                + "faltó al pueblo de Luis, Steven, Santiago y Nicholas. Se maravillaron tanto que\n"
                + "decidieron inscribirse en la Guardia Real. Trabajaron duro hasta llegar al rango\n"
                + "más alto, llamado 'Tower Defense'. Este rango se encarga de manipular torres\n"
                + "para defender el pueblo.\n\n"

                + "Luego de muchos años de trabajo duro, Luis, Steven, Santiago y Nicholas se\n"
                + "percataron de que el pueblo estaba en peligro. Los seres mitológicos que\n"
                + "destruyeron su pueblo natal estaban de regreso. Se infiltraron en el pueblo y\n"
                + "causaban disturbios; por suerte, pudieron eliminarlos a tiempo.\n\n"

                + "Luis, Steven, Santiago y Nicholas ya sabían lo que seguía, así que se prepararon\n"
                + "para defender su pueblo. Los seres mitológicos, fascinados por la riqueza del\n"
                + "lugar, decidieron atacar con el objetivo de robar el oro, diamantes, amatistas y\n"
                + "demás joyas preciosas del pueblo.\n\n"

                + "Pero lo que no sabían era que Luis, Steven, Santiago y Nicholas estaban\n"
                + "dispuestos a protegerlo con su vida, para que su nuevo hogar no terminara como\n"
                + "su pueblo natal."
        );

        historia.setEditable(false);
        historia.setOpaque(false);

        StyledDocument doc = historia.getStyledDocument();

        SimpleAttributeSet center = new SimpleAttributeSet();

        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        historia.setForeground(new Color(20,10,0));

        historia.setFont(MedievalFont.getFont(30f));

        historia.setMargin(new Insets(80,80,80,80));

        JScrollPane scroll = new JScrollPane(historia);

        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        scroll.setBorder(null);

        scroll.getVerticalScrollBar().setUnitIncrement(16);

        fondo.add(scroll,BorderLayout.CENTER);

        setContentPane(fondo);

        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new Historia();
        });
    }
}