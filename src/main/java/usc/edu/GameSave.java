package usc.edu;

import java.io.*;
import java.util.ArrayList;


public class GameSave {

    private static final String ARCHIVO = "savegame.dat";

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
        ArrayList<EnemigoGuardado> enemigos = new ArrayList<>();
    }

    private static class TorreGuardada implements Serializable {
        private static final long serialVersionUID = 1L;
        int tipo;   
        int gridX;
        int gridY;
        int hp;

        TorreGuardada(int tipo, int gridX, int gridY, int hp) {
            this.tipo  = tipo;
            this.gridX = gridX;
            this.gridY = gridY;
            this.hp    =hp;
        }
    }
    private static class EnemigoGuardado implements Serializable {
        private static final long serialVersionUID = 1L;
        int tipo;
        int hp;
        double x;
        double y;
        int pathIndex;
        EnemigoGuardado(int tipo, int hp, double x, double y, int pathIndex) {
            this.tipo = tipo;
            this.hp = hp;
            this.x = x;
            this.y = y;
            this.pathIndex = pathIndex;
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
            e.torres.add(new TorreGuardada(tipo, t.gridX, t.gridY, t.hp));
        }
        for (Enemy enemy : gp.enemies) {
    int tipo;
    if (enemy instanceof NormalEnemy) tipo = 1;
    else if (enemy instanceof FastEnemy) tipo = 2;
    else if (enemy instanceof TankEnemy) tipo = 3;
    else if (enemy instanceof Witch) tipo = 4;
    else if (enemy instanceof NIGROMANTE) tipo = 5;
    else continue;

    e.enemigos.add(new EnemigoGuardado(tipo, enemy.hp, enemy.x, enemy.y, enemy.pathIndex));
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

            gp.startTime             = System.currentTimeMillis() - (long) e.elapsedSeconds * 1000L;
            gp.pausedAccumulatedTime = 0;

            gp.towers.clear();
            for (TorreGuardada tg : e.torres) {
                Tower t = crearTorre(tg.tipo, tg.gridX, tg.gridY, gp);
                if (t != null) {
                        t.hp = tg.hp;
                    gp.towers.add(t);
                }
            }
            gp.enemies.clear();

for(EnemigoGuardado eg:e.enemigos){

    Enemy enemy=crearEnemigo(eg.tipo,gp);

    if(enemy!=null){

        enemy.hp=eg.hp;
        enemy.x=eg.x;
        enemy.y=eg.y;
        enemy.pathIndex=eg.pathIndex;

        gp.enemies.add(enemy);
    }
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
    private static Enemy crearEnemigo(int tipo, GamePanel gp) {
    switch (tipo) {
        case 1: return new NormalEnemy(0, 4 * gp.TILE_SIZE, gp.normalenemyImg);
        case 2: return new FastEnemy(0, 4 * gp.TILE_SIZE, gp.fastEnemyImg);
        case 3: return new TankEnemy(0, 4 * gp.TILE_SIZE, gp.tankEnemyImg);
        case 4: return new Witch(0, 4 * gp.TILE_SIZE, gp.witchImg);
        case 5: return new NIGROMANTE(0, 4 * gp.TILE_SIZE, gp.NIGROMANTEImg);
        default: return null;
    }
}
}
