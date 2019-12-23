/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

/**
 *
 * @author Chris
 */
  public class TurntableConnector {
        ConnectionInterface port;
        Boolean input;
        
        public TurntableConnector(ConnectionInterface port, Boolean input) {
            this.port = port;
            this.input = input;
        }
    }