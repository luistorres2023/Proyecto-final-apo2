package usc.edu;

import java.io.*;
import java.util.ArrayList;


public class GameSave {

    private static final String ARCHIVO = "savegame.dat";

    // ─── Objeto que se serializa ──────────────────────────────────────────
    private static class Estado implements Serializable {
        private static final long serialVersionUID = 2L;

        int    wave;
        int    maxWave;
        int    money;
        double lives;
        int    score;
        int    enemiesSpawned;
        int    enemiesPerWave;
        int    elapsedSeconds;

        ArrayList<TorreGuardada> torres = new ArrayList<>();
    }

    private static class TorreGuardada implements Serializable {
        private static final long serialVersionUID = 1L;
        int tipo;   // 1=Basic  2=SlowDown  3=Magic  4=Sniper
        int gridX;
        int gridY;

        TorreGuardada(int tipo, int gridX, int gridY) {
            this.tipo  = tipo;
            this.gridX = gridX;
            this.gridY = gridY;
        }
    }

   
    public static void guardar(GamePanel gp) {

        Estado e = new Estado();
        e.wave           = gp.wave;
        e.maxWave        = gp.maxWave;
        e.money          = gp.money;
        e.lives          = gp.lives;
        e.score          = gp.score;
        e.enemiesSpawned = gp.enemiesSpawned;
        e.enemiesPerWave = gp.enemiesPerWave;
        e.elapsedSeconds = gp.elapsedSeconds;

        for (Tower t : gp.towers) {
            int tipo;
            if      (t instanceof BasicTower)    tipo = 1;
            else if (t instanceof SlowDownTower) tipo = 2;
            else if (t instanceof MagicTower)    tipo = 3;
            else if (t instanceof SniperTower)   tipo = 4;
            else continue;
            e.torres.add(new TorreGuardada(tipo, t.gridX, t.gridY));
        }


        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(ARCHIVO)))) {
            oos.writeObject(e);
            System.out.println("[GameSave] Guardado: oleada=" + e.wave
                    + " vidas=" + e.lives + " torres=" + e.torres.size());
        } catch (IOException ex) {
            System.err.println("[GameSave] Error al guardar: " + ex.getMessage());
        }
    }

    
    public static boolean cargar(GamePanel gp) {

        if (!existe()) return false;

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(ARCHIVO)))) {

            Estado e = (Estado) ois.readObject();

            gp.wave           = e.wave;
            gp.maxWave        = e.maxWave;
            gp.money          = e.money;
            gp.lives          = e.lives;
            gp.score          = e.score;
            gp.enemiesSpawned = e.enemiesSpawned;
            gp.enemiesPerWave = e.enemiesPerWave;

            // El reloj continúa desde donde se quedó
            gp.startTime             = System.currentTimeMillis() - (long) e.elapsedSeconds * 1000L;
            gp.pausedAccumulatedTime = 0;

            gp.towers.clear();
            for (TorreGuardada tg : e.torres) {
                Tower t = crearTorre(tg.tipo, tg.gridX, tg.gridY, gp);
                if (t != null) gp.towers.add(t);
            }

            System.out.println("[GameSave] Cargado: oleada=" + gp.wave
                    + " vidas=" + gp.lives + " torres=" + gp.towers.size());
            return true;

        } catch (Exception ex) {
            System.err.println("[GameSave] Archivo corrupto, eliminando: " + ex.getMessage());
            borrar();
            return false;
        }
    }

    
    public static int getMaxWaveGuardado() {

        if (!existe()) return 0;

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(ARCHIVO)))) {

            Estado e = (Estado) ois.readObject();
            return e.maxWave;

        } catch (Exception ex) {
            return 0;
        }
    }

    
    public static boolean existe() {
        return new File(ARCHIVO).exists();
    }

    public static void borrar() {
        if (new File(ARCHIVO).delete())
            System.out.println("[GameSave] Guardado eliminado.");
    }

    
    private static Tower crearTorre(int tipo, int gridX, int gridY, GamePanel gp) {
        switch (tipo) {
            case 1: return new BasicTower(gridX, gridY, gp.tower1Img);
            case 2: return new SlowDownTower(gridX, gridY, gp.tower4Img);
            case 3: return new MagicTower(gridX, gridY, gp.tower3Img);
            case 4: return new SniperTower(gridX, gridY, gp.tower2Img);
            default: return null;
        }
    }
}
