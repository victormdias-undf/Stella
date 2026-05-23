import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[71];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        LoadMap();
    }

    public void getTileImage(){
        for (int i = 0; i < tile.length; i++) {
            tile[i] = new Tile();
            if(i==67){
                tile[i].collision=true;
            }
            String path = String.format("/res/tile/tile%03d.png", i);
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is != null) {
                    tile[i].image = ImageIO.read(is);
                } else {
                    System.err.println("Resource not found: " + path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void LoadMap() {

    try (InputStream is = getClass().getResourceAsStream("/res/map.txt")) {

        if (is == null) {
            System.err.println("Map file not found");
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int row = 0;

        while (row < gp.maxWorldRow) {

            String line = br.readLine();

            if (line == null) {
                break;
            }

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] numbers = line.trim().split("\\s+");

            for (int col = 0; col < gp.maxWorldCol; col++) {

                int num = Integer.parseInt(numbers[col]);

                mapTileNum[col][row] = num;
            }

            row++;
        }

        br.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void Draw(Graphics2D g2){

        for(int i = 0; i<gp.maxWorldRow; i++){
            for(int j=0; j<gp.maxWorldCol; j++){
                int WorldX = j*gp.tileSz;
                int WorldY = i*gp.tileSz;

                int ScreenX = WorldX - gp.player.worldX + gp.player.screenX;
                int ScreenY = WorldY - gp.player.worldY + gp.player.screenY;
                int tilenum = mapTileNum[j][i];
                g2.drawImage(tile[tilenum].image, ScreenX, ScreenY, gp.tileSz, gp.tileSz, null);
            }
        }
    }
    
}
