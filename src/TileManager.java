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
    public TileManager() {
        tile = new Tile[71];
        getTileImage();
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
    }

    public void getTileImage(){
        for (int i = 0; i < tile.length; i++) {
            tile[i] = new Tile();
            String path = String.format("./res/tile/tile%03d.png", i);
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
    public void LoadMap(){
        try {
            InputStream is = getClass().getResourceAsStream("./res/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row =0;

            while(col<gp.maxWorldCol && row<gp.maxWorldRow){
                String line = br.readLine();
                while(col<gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col=0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x =0;
        int y=0;
        
        for()
    }
    
}
