package com.stella.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gerencia as entradas do teclado do jogador.
 * Detecta quando as teclas WASD são pressionadas ou soltas.
 */
public class KeyHandler implements KeyListener{
    // Estados de cada direção (pressionado = true, solto = false)
    public boolean leftPressed, rightPressed, upPressed, downPressed;
    
    /**
     * Detecta quando uma tecla é pressionada.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
    
        // Controles: A = esquerda, D = direita, W = cima, S = baixo
        if(code == KeyEvent.VK_A){
            this.leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            this.rightPressed = true;
        }
        if(code == KeyEvent.VK_S){
            this.downPressed = true;
        }
        if(code == KeyEvent.VK_W){
            this.upPressed = true;
        }
    }

    /**
     * Detecta quando uma tecla é solta.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_A){
            this.leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            this.rightPressed = false;
        }
        if(code == KeyEvent.VK_S){
            this.downPressed = false;
        }
        if(code == KeyEvent.VK_W){
            this.upPressed = false;
        }
    }

    /**
     * Não é usado, mas é obrigatório implementar (parte da interface KeyListener).
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
       
    }
}
