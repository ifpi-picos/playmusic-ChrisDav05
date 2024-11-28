import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import  javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import dominio.Album;
import dominio.Artista;
import dominio.Musica;

public class App{
    public static void main(String[] args) throws Exception{
        Musica musica1 = new Musica();
        musica1.setNome("Californication");
        musica1.setDuracao(120);
        musica1.setGenero("Rock");
        musica1.setArquivoAudio("assets\\Red-Hot-Chili-Peppers-Californication.wav");

        Musica musica2 = new Musica();
        musica2.setNome("Otherside");
        musica2.setDuracao(120);
        musica2.setGenero("Rock");
        musica2.setArquivoAudio("assets\\Red-Hot-Chili-Peppers-Otherside.wav");

        Album album1 = new Album();
        album1.setNome("Primeiro album");
        album1.setAno(2000);
        album1.addMusica(musica1);
        album1.addMusica(musica2);

        Artista redHot = new Artista();
        redHot.setNome("Red Hot Chili Peppers");
        redHot.addAlbum(album1);

        System.out.println("Abrindo o PlayMusic.");
        redHot.getAlbuns().get(0).getMusicas().get(0).getArquivoAudio();

        AudioPlayer player = new AudioPlayer();
        int[] index = { 0 }; 
        Musica[] playlist = redHot.getAlbuns().get(0).getMusicas().toArray(new Musica [0]);

        JButton playStopButton = new JButton("Play");
        playStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player.isPlaying) {
                    player.loadAudio(playlist[index[0]].getArquivoAudio());
                    player.playAudio();
                    playStopButton.setText("Stop");
                    System.out.println("Música reproduzida: " + playlist[index[0]].getNome());
                } else {
                    player.stopAudio();
                    playStopButton.setText("Play");
                    System.out.println("Stop.");
                }
            }
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if (index[0] < playlist.length - 1) {
                index[0]++;
                player.stopAudio();
                player.loadAudio(playlist[index[0]].getArquivoAudio());
                player.playAudio();
                playStopButton.setText("Stop");
                System.out.println("Reproduzindo a próxima música: " + playlist[index[0]].getNome());
            } else {
                System.out.println("Não tem mais músicas a serem reproduzidas.");
            }
        });

        JButton beforeButton = new JButton("Before");
        beforeButton.addActionListener(e -> {
            if (index[0] > 0) {
                index[0]--;
                player.stopAudio();
                player.loadAudio(playlist[index[0]].getArquivoAudio());
                player.playAudio();
                playStopButton.setText("Stop");
                System.out.println("Tocando música anterior: " + playlist[index[0]].getNome());
            } else {
                System.out.println("Você já está na primeira música.");
            }
        });

        ImageIcon Icon = new ImageIcon("assets\\music.png");
        JOptionPane.showOptionDialog(
                null,
                null,
                "PlayMusic",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                Icon,
                new Object[] { playStopButton, nextButton, beforeButton }, playStopButton);

        if (player.audioClip != null) {
            player.audioClip.close();
        }
    }
}