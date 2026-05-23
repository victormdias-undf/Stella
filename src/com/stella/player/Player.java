package com.stella.player;

import com.stella.core.GamePanel;
import com.stella.entities.Entity;
import com.stella.entities.superObject;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Representa o jogador controlado pelo usuário.
 * Gerencia movimento, colisão e detecção de inimigos próximos.
 */
public class Player extends Entity {
    // Referências para o painel do jogo e entrada do teclado
    GamePanel gp;
    KeyHandler key;
    
    // Posição do jogador na tela (fixa, câmera segue ele)
    public int screenX;
    public int screenY;
    
    // Texto de status atual (ex: "Barra de medo: 50%")
    String situation;

    public Player(GamePanel gp, KeyHandler key){
        this.gp = gp;
        
        // Coloca o jogador no centro da tela
        screenX = gp.screenWidth/2-(gp.tileSz/2);
        screenY = gp.screenHeight/2-(gp.tileSz/2);
        
        // A posição no mundo começa onde a câmera está
        worldX = screenX;
        worldY = screenY;
        
        // Define a área de colisão (pequena, não ocupa todo o tile)
        solidArea = new Rectangle(gp.tileSz/4, gp.tileSz/2, gp.tileSz/2, gp.tileSz/2);
        
        this.key = key;
    }

    /**
     * Atualiza o estado do jogador a cada frame.
     */
    public void update(){
        // Checa se bate em alguma parede
        gp.cChecker.checkTile(this);
        // Checa proximidade com inimigos
        checkFear();
    }

    /**
     * Move o jogador baseado nas teclas pressionadas.
     * Só se move se não colidiu com nada.
     */
    public void andar(){
        // Se não colidiu, pode se mover
        if(collisonOn == false){
            if(key.leftPressed){
                worldX -= 3;
            }
            if(key.rightPressed){
                worldX += 3;
            }
            if(key.upPressed){
                worldY -= 3;
            }
            if(key.downPressed){
                worldY += 3;
            }
        }
        
        // Atualiza para qual direção o jogador está virado
        if(key.leftPressed){
            direction = "left";
        }
        if(key.rightPressed){
            direction = "right";
        }
        if(key.upPressed){
            direction = "top";
        }
        if(key.downPressed){
            direction = "bottom";
        }
    }

    /**
     * Verifica proximidade com inimigos e aumenta o nível de medo.
     * Quanto mais perto do inimigo, maior o medo.
     */
    public void checkFear(){
        // Calcula a menor distância euclidiana entre o jogador e qualquer inimigo
        double minDist = Double.MAX_VALUE;
        double playerCenterX = worldX + gp.tileSz / 2.0;
        double playerCenterY = worldY + gp.tileSz / 2.0;

        for (superObject Obj1 : gp.obj) {
            if (Obj1 == null) continue;
            if (!Obj1.enemy) continue;

            double objCenterX = Obj1.WorldX + Obj1.width / 2.0;
            double objCenterY = Obj1.WorldY + Obj1.height / 2.0;

            double dx = objCenterX - playerCenterX;
            double dy = objCenterY - playerCenterY;

            double dist = Math.hypot(dx, dy); // distância euclidiana
            if (dist < minDist) minDist = dist;
        }

        // Atualiza a situação com base na menor distância encontrada
        if (minDist == Double.MAX_VALUE) {
            situation = null; // nenhum inimigo presente
            return;
        }

        if (minDist < 200) {
            situation = "Barra de medo: 100%";
        } else if (minDist < 300) {
            situation = "Barra de medo: 75%";
        } else if (minDist < 400) {
            situation = "Barra de medo: 50%";
        } else if (minDist < 500) {
            situation = "Barra de medo: 20%";
        } else {
            situation = null; // fora do alcance
        }
    }
    

    /**
     * Desenha o jogador na tela.
     */
    public void Draw(Graphics2D g2){
        // Desenha um quadrado rosa representando o jogador
        g2.setColor(Color.PINK);
        g2.fillRect(screenX, screenY, gp.tileSz, gp.tileSz);
        
        // Mostra o status atual (nível de medo)
        if(situation != null){
            g2.setColor(Color.WHITE);
            g2.drawString(situation, 100, 100);
        }
    }
}
